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

import io.riddles.javainterface.game.state.AbstractState;
import io.riddles.connectfour.game.data.ConnectfourBoard;
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
public class ConnectfourState extends AbstractState<ConnectfourMove> {

    private ConnectfourBoard board;
    private String errorMessage;
    private String mPossibleMovesString, mFieldPresentationString;
    private int moveNumber;


    public ConnectfourState() {
        super();
    }

    public ConnectfourState(ConnectfourState previousState, ConnectfourMove move, int roundNumber, String possibleMovesString, String fieldPresentationString) {
        super(previousState, move, roundNumber);
        this.mPossibleMovesString = possibleMovesString;
        this.mFieldPresentationString = fieldPresentationString;
        this.board = previousState.getBoard();
    }

    public ConnectfourState(ConnectfourState previousState, ArrayList<ConnectfourMove> moves, int roundNumber) {
        super(previousState, moves, roundNumber);
        this.board = previousState.getBoard();
    }

    public ConnectfourBoard getBoard() {
        return this.board;
    }

    public void setBoard(ConnectfourBoard b) {
        this.board = b;
    }

    public String getPossibleMovesPresentationString() {
        return mPossibleMovesString;
    }
    public String getFieldPresentationString() {
        return mFieldPresentationString;
    }

    public void setPossibleMovesPresentationString(String s) {
        this.mPossibleMovesString = s;
    }
    public void setFieldPresentationString(String s) { this.mFieldPresentationString = s; }

    public void setMoveNumber(int n) { this.moveNumber = n; }
    public int getMoveNumber() { return this.moveNumber; }
}