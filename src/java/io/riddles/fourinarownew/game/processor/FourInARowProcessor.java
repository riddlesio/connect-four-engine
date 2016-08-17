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

package io.riddles.fourinarownew.game.processor;

import java.util.ArrayList;

import io.riddles.fourinarownew.game.player.FourInARowPlayer;
import io.riddles.javainterface.game.processor.AbstractProcessor;
import io.riddles.fourinarownew.game.move.*;
import io.riddles.fourinarownew.game.state.FourInARowState;

/**
 * This file is a part of FourInARow
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * FourInARowProcessor shall process a State and return the result.
 *
 * @author joost
 */
public class FourInARowProcessor extends AbstractProcessor<FourInARowPlayer, FourInARowState> {

    private int roundNumber;
    private boolean gameOver;
    private FourInARowPlayer winner;


    /* Constructor */
    public FourInARowProcessor(ArrayList<FourInARowPlayer> players) {
        super(players);
        this.gameOver = false;
    }

    /* preGamePhase may be used to set up the Processor before starting the game loop.
    * */
    @Override
    public void preGamePhase() {

    }

    /* playRound is called every cycle of SimpleGameLoop. It should:
    *   - Take a State
    *   - Ask players for response
    *   - Parse response into a move
    *   - Handle move logic
    *   - Create a new State and return it.
    *   returns: FourInARowState
    * */
    @Override
    public FourInARowState playRound(int roundNumber, FourInARowState state) {
        this.roundNumber = roundNumber;

        ArrayList<FourInARowMove> moves = new ArrayList();

        int moveNumber = (roundNumber-1)*this.players.size()+1;

        FourInARowState nextState = state;

        for (FourInARowPlayer player : this.players) {
            if (!hasGameEnded(nextState)) {
                nextState = new FourInARowState(nextState, moves, roundNumber);

                LOGGER.info(String.format("Playing round %d, move %d", roundNumber, moveNumber));

                String response = player.requestMove(ActionType.MOVE.toString());

                // parse the response
                FourInARowMoveDeserializer deserializer = new FourInARowMoveDeserializer(player);
                FourInARowMove move = deserializer.traverse(response);

                FourInARowLogic l = new FourInARowLogic();

                try {
                    nextState = l.transformBoard(nextState, move, this.players);
                } catch (Exception e) {
                    LOGGER.info(e.toString());
                }

                //if (move.getException() != null) System.out.println(move.getException());

                // create the next state
                moves.add(move);

                // stop game if bot returns nothing
                if (response == null) {
                    this.gameOver = true;
                }

                nextState.setMoveNumber(moveNumber);
                int nextPlayer = getNextPlayerId(player);
                nextState.setFieldPresentationString(nextState.getBoard().toPresentationString(nextPlayer, false));
                nextState.setPossibleMovesPresentationString(nextState.getBoard().toPresentationString(nextPlayer, true));

                //nextState.getBoard().dump();
                //nextState.getBoard().dumpMacroboard();
                checkWinner(nextState);
                moveNumber++;
            }

        }


        return nextState;
    }

    private int getNextPlayerId(FourInARowPlayer p) {
        return (p.getId() == 1) ? 2 : 1;
    }

    /* hasGameEnded should check all conditions on which a game should end
    *  returns: boolean
    * */
    @Override
    public boolean hasGameEnded(FourInARowState state) {
        boolean returnVal = false;
        if (roundNumber > 30) returnVal = true;
        checkWinner(state);
        if (this.winner != null) returnVal = true;
        return returnVal;
    }

    /* getWinner should check if there is a winner.
    *  returns: if there is a winner, the winning Player, otherwise return null.
    *  */
    @Override
    public FourInARowPlayer getWinner() {
        return this.winner;
    }

    public void checkWinner(FourInARowState s) {
        this.winner = null;
        s.getBoard().updateMacroboard();
        int winner = s.getBoard().getMacroboardWinner();
        if (winner != 0) {
            for (FourInARowPlayer player : this.players) {
                if (player.getId() == winner) {
                    this.winner = player;
                }
            }
        }
    }

    /* getScore should return the game score if applicable.
    *  returns: double Score
    *  */
    @Override
    public double getScore() {
        return 0;
    }

}
