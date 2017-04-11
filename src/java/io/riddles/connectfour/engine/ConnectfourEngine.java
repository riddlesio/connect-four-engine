/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package io.riddles.connectfour.engine;

import io.riddles.connectfour.game.ConnectfourSerializer;
import io.riddles.connectfour.game.board.Board;
import io.riddles.connectfour.game.move.ConnectfourMove;
import io.riddles.connectfour.game.player.ConnectfourPlayer;
import io.riddles.connectfour.game.processor.ConnectfourProcessor;
import io.riddles.connectfour.game.state.ConnectfourPlayerState;
import io.riddles.connectfour.game.state.ConnectfourState;
import io.riddles.javainterface.configuration.Configuration;
import io.riddles.javainterface.engine.AbstractEngine;
import io.riddles.javainterface.engine.GameLoopInterface;
import io.riddles.javainterface.engine.TurnBasedGameLoop;
import io.riddles.javainterface.exception.TerminalException;
import io.riddles.javainterface.game.player.PlayerProvider;
import io.riddles.javainterface.io.IOHandler;

import java.util.ArrayList;

import static io.riddles.connectfour.game.move.MoveType.PLACEDISC;

/**
 * ConnectfourEngine:
 * - Creates a Processor, the Players and an initial State
 * - Parses the setup input
 * - Sends settings to the players
 * - Runs a game
 * - Returns the played game at the end of the game
 *
 * Created by joost on 6/27/16.
 */
public class ConnectfourEngine extends AbstractEngine<ConnectfourProcessor, ConnectfourPlayer, ConnectfourState> {

    public ConnectfourEngine(PlayerProvider<ConnectfourPlayer> playerProvider, IOHandler ioHandler) throws TerminalException {
        super(playerProvider, ioHandler);
    }

    /* createPlayer creates and initialises a Player for the game.
     * returns: a Player
     */
    @Override
    protected ConnectfourPlayer createPlayer(int id) {
        ConnectfourPlayer p = new ConnectfourPlayer(id);
        return p;
    }

    @Override
    protected Configuration getDefaultConfiguration() {
        Configuration cc = new Configuration();
        cc.put("maxRounds", 10);
        cc.put("fieldWidth", 7);
        cc.put("fieldHeight", 6);
        return cc;
    }

    /* createProcessor creates and initialises a Processor for the game.
     * returns: a Processor
     */
    @Override
    protected ConnectfourProcessor createProcessor() {
        return new ConnectfourProcessor(playerProvider);
    }

    @Override
    protected GameLoopInterface createGameLoop() {
        return new TurnBasedGameLoop();
    }

    @Override
    protected void sendSettingsToPlayer(ConnectfourPlayer player) {
        player.sendSetting("your_botid", player.getId());
        player.sendSetting("field_width", configuration.getInt("fieldWidth"));
        player.sendSetting("field_height", configuration.getInt("fieldHeight"));
        player.sendSetting("max_rounds", configuration.getInt("maxRounds"));
    }

    /* getPlayedGame creates a serializer and serialises the game
     * returns: String with the serialised game.
     */
    @Override
    protected String getPlayedGame(ConnectfourState initialState) {
        ConnectfourSerializer serializer = new ConnectfourSerializer();
        return serializer.traverseToString(this.processor, initialState);
    }

    /* getInitialState creates an initial state to start the game with.
     * returns: ConnectfourState
     */
    @Override
    protected ConnectfourState getInitialState() {
        ArrayList<ConnectfourPlayerState> playerStates = new ArrayList<>();

        for (ConnectfourPlayer player : this.playerProvider.getPlayers()) {
            ConnectfourPlayerState playerState = new ConnectfourPlayerState(player.getId());
            playerState.setMove(new ConnectfourMove(PLACEDISC, 0));
            playerStates.add(playerState);
        }
        ConnectfourState s = new ConnectfourState(null, playerStates, 0);
        s.setPlayerId(playerProvider.getPlayers().get(0).getId());
        s.setBoard(new Board(configuration.getInt("fieldWidth"), configuration.getInt("fieldHeight")));
        return s;
    }
}
