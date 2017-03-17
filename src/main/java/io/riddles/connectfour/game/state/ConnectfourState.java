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

package io.riddles.connectfour.game.state;

import io.riddles.connectfour.engine.ConnectfourEngine;
import io.riddles.connectfour.game.board.Board;
import io.riddles.javainterface.game.state.AbstractState;
import io.riddles.connectfour.game.move.ConnectfourMove;

import java.util.ArrayList;

/**
 * io.riddles.game.game.state.FourInARowStateState - Created on 2-6-16
 *
 * ConnectfourState extends AbstractState and is used to store game specific data per state.
 * It can be initialised to store a FourInARoweMove, or multiple FourInARowMoves in an ArrayList.
 *
 * @author joost
 */
public class ConnectfourState extends AbstractState<ConnectfourPlayerState> {

    private Board board;
    private int playerId;
    private String winnerString;

    public ConnectfourState(ConnectfourState previousState, ArrayList<ConnectfourPlayerState> playerStates, int roundNumber) {
        super(previousState, playerStates, roundNumber);
        if (previousState != null) {
            Board b = previousState.getBoard();
            this.board = new Board(b.getWidth(), b.getHeight(), b.toString());
            this.playerId = previousState.getPlayerId();
        } else
            this.board = new Board(ConnectfourEngine.configuration.getInt("fieldWidth"), ConnectfourEngine.configuration.getInt("fieldHeight"));
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board b) {
        this.board = b;
    }

    public int getPlayerId() { return this.playerId; }
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getWinnerString() { return this.winnerString; }
    public void setWinnerString(String winner) {
        this.winnerString = winner;
    }
}
