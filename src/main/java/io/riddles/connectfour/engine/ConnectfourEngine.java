package io.riddles.connectfour.engine;

import io.riddles.connectfour.game.ConnectfourSerializer;
import io.riddles.connectfour.game.board.Board;
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
            ConnectfourPlayerState goPlayerState = new ConnectfourPlayerState(player.getId());
            playerStates.add(goPlayerState);
        }
        ConnectfourState s = new ConnectfourState(null, playerStates, 0);
        s.setBoard(new Board(configuration.getInt("fieldWidth"), configuration.getInt("fieldHeight")));
        return s;
    }
}
