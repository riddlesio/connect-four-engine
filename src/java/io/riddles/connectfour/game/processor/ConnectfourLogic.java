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

import io.riddles.connectfour.game.board.Board;
import io.riddles.connectfour.game.player.ConnectfourPlayer;
import io.riddles.connectfour.game.state.ConnectfourPlayerState;
import io.riddles.connectfour.game.state.ConnectfourState;
import io.riddles.javainterface.exception.InvalidInputException;
import io.riddles.connectfour.game.move.ConnectfourMove;

import java.awt.*;


public class ConnectfourLogic {

    public void transform(ConnectfourState state, ConnectfourPlayerState playerState) throws InvalidInputException {
        ConnectfourMove move = playerState.getMove();

        if (move.getException() == null) {
            transformMove(state, move, playerState.getPlayerId());
        }
    }

    private void transformMove(ConnectfourState state, ConnectfourMove move, int pId) {
        Board board = state.getBoard();


        int c = move.getColumn();

        if (c < board.getWidth() && c  >= 0) { /* Move within range */
            for (int y = board.getHeight()-1; y >= 0; y--) { // From bottom column up
                Point tmpC = new Point(c,y);
                if (board.getFieldAt(tmpC).equals(board.EMPTY_FIELD)) {
                    board.setFieldAt(tmpC, String.valueOf(pId));
                    return;
                }
            }
            move.setException(new InvalidInputException("Column " + c + " is full"));
        } else {
            move.setException(new InvalidInputException("Move out of bounds. (" + c + ")"));
        }
    }

    public String getWinner(Board b, int inARow) {
        /* Check for horizontal wins */
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                String c = b.getFieldAt(new Point(x,y));
                Boolean win = true;
                if (!c.equals(Board.EMPTY_FIELD)) {
                    for (int i = 0; i < inARow; i++) {
                        if (x + i < b.getWidth()) {
                            if (!c.equals(b.getFieldAt(new Point(x + i, y)))) {
                                win = false;
                            }
                        } else {
                            win = false;
                        }
                    }
                    if (win) {
                        return c;
                    }
                }
            }
        }

        /* Check for vertical wins */
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                String c = b.getFieldAt(new Point(x,y));
                Boolean win = true;
                if (!c.equals(Board.EMPTY_FIELD)) {
                    for (int i = 0; i < inARow; i++) {
                        if (y + i < b.getHeight()) {
                            if (!c.equals(b.getFieldAt(new Point(x, y + i)))) {
                                win = false;
                            }
                        } else {
                            win = false;
                        }
                    }
                    if (win) {
                        return c;
                    }
                }
            }
        }

        /* Check for diagonal wins */
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                String c = b.getFieldAt(new Point(x,y));
                Boolean win = true;
                if (!c.equals(Board.EMPTY_FIELD)) {
                    for (int i = 0; i < inARow; i++) {
                        if (x - i >= 0 && y + i < b.getHeight()) {
                            if (!c.equals(b.getFieldAt(new Point(x - i, y + i)))) {
                                win = false;
                            }
                        } else {
                            win = false;
                        }
                    }
                    if (win) {
                        return c;
                    }
                }
            }
        }
        /* Check for anti diagonal wins */
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                String c = b.getFieldAt(new Point(x,y));
                Boolean win = true;
                if (!c.equals(Board.EMPTY_FIELD)) {
                    for (int i = 0; i < inARow; i++) {
                        if (x + i < b.getWidth() && y + i < b.getHeight()) {
                            if (!c.equals(b.getFieldAt(new Point(x + i, y + i)))) {
                                win = false;
                            }
                        } else {
                            win = false;
                        }
                    }
                    if (win) {
                        return c;
                    }
                }
            }
        }
        return null;
    }
}