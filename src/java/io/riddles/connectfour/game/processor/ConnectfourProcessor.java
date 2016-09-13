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

package io.riddles.connectfour.game.processor;

import java.util.ArrayList;

import io.riddles.connectfour.engine.ConnectfourEngine;
import io.riddles.connectfour.game.board.Board;
import io.riddles.connectfour.game.player.ConnectfourPlayer;
import io.riddles.javainterface.game.processor.AbstractProcessor;
import io.riddles.connectfour.game.move.*;
import io.riddles.connectfour.game.state.ConnectfourState;

/**
 * This file is a part of Connectfour
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * ConnectfourProcessor shall process a State and return the result.
 *
 * @author joost
 */
public class ConnectfourProcessor extends AbstractProcessor<ConnectfourPlayer, ConnectfourState> {

    private int roundNumber;
    private boolean gameOver;
    private ConnectfourPlayer winner;
    private ConnectfourLogic logic;


    /* Constructor */
    public ConnectfourProcessor(ArrayList<ConnectfourPlayer> players) {
        super(players);
        this.gameOver = false;
        this.logic = new ConnectfourLogic();
    }

    /* preGamePhase may be used to set up the Processor before starting the game loop.
    * */
    @Override
    public void preGamePhase() {

    }

    /**
     * Play one round of the game. It takes a LightridersState,
     * asks all living players for a response and delivers a new LightridersState.
     *
     * Return
     * the LightridersState that will be the state for the next round.
     * @param roundNumber The current round number
     * @param ConnectfourState The current state
     * @return The LightridersState that will be the start of the next round
     */
    @Override
    public ConnectfourState playRound(int roundNumber, ConnectfourState state) {
        LOGGER.info(String.format("Playing round %d", roundNumber));
        this.roundNumber = roundNumber;

        ConnectfourLogic logic = new ConnectfourLogic();
        ConnectfourState nextState = state;

        for (ConnectfourPlayer player : this.players) {
            if (!hasGameEnded(nextState)) {
                nextState = new ConnectfourState(nextState, new ArrayList<>(), roundNumber);
                Board nextBoard = nextState.getBoard();

                player.sendUpdate("field", player, nextBoard.toString());
                String response = player.requestMove(ActionType.MOVE.toString());

                // parse the response
                ConnectfourMoveDeserializer deserializer = new ConnectfourMoveDeserializer(player);
                ConnectfourMove move = deserializer.traverse(response);



                // create the next move
                nextState.getMoves().add(move);

                try {
                    logic.transform(nextState, move);
                } catch (Exception e) {
                    LOGGER.info(String.format("Unknown response: %s", response));
                }

                // stop game if bot returns nothing
                if (response == null) {
                    this.gameOver = true;
                }
                nextBoard.dump();
            }
        }

        return nextState;
    }

    private int getNextPlayerId(ConnectfourPlayer p) {
        return (p.getId() == 1) ? 2 : 1;
    }

    /**
     * The stopping condition for this game.
     * @param ConnectfourState the state to determine whether the game has ended.
     * @return True if the game is over, false otherwise
     */
    @Override
    public boolean hasGameEnded(ConnectfourState state) {
        boolean returnVal = false;
        if (this.roundNumber >= ConnectfourEngine.configuration.getInt("maxRounds")) returnVal = true;
        if (getWinner() != null) returnVal = true;
        return returnVal;
    }

    /**
     * Returns the winner of the game, if there is one.
     * @return null if there is no winner, a ConnectfourPlayer otherwise
     */
    @Override
    public ConnectfourPlayer getWinner() {
        return this.winner;
    }

    /**
     * GetScore isn't used in Lightriders.
     * @return always return 0.
     */
    @Override
    public double getScore() {
        return 0;
    }

}
