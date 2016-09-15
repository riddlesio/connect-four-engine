package io.riddles.connectfour.game.processor;

import io.riddles.connectfour.game.board.Board;
import io.riddles.connectfour.game.player.ConnectfourPlayer;
import io.riddles.connectfour.game.state.ConnectfourState;
import io.riddles.javainterface.exception.InvalidInputException;
import io.riddles.connectfour.game.move.ConnectfourMove;

import java.awt.*;

/**
 * Created by joost on 3-7-16.
 */
public class ConnectfourLogic {


    public ConnectfourLogic() {
    }

    /**
     * Takes a ConnectfourState and transforms it with a ConnectfourMove.
     *
     * Return
     * Returns nothing, but transforms the given ConnectfourState.
     * @param ConnectfourState The initial state
     * @param ConnectfourPlayer The player involved
     * @param ConnectfourMove The move of the player
     * @return
     */
    public void transform(ConnectfourState state, ConnectfourMove move) throws InvalidInputException {

        if (move.getException() == null) {
            transformMove(state, move);
        }
    }


    private void transformMove(ConnectfourState state, ConnectfourMove move) {
        ConnectfourPlayer p = move.getPlayer();
        Board board = state.getBoard();

        int pId = p.getId();

        int c = move.getColumn();

        if (c < board.getWidth() && c  >= 0) { /* Move within range */
            for (int y = board.getHeight()-1; y >= 0; y--) { // From bottom column up
                Point tmpC = new Point(c,y);
                if (board.getFieldAt(tmpC).equals("0")) {
                    board.setFieldAt(tmpC, String.valueOf(pId));
                    return;
                }
            }
            move.setException(new InvalidInputException("Column " + c + " is full"));
        } else {
            move.setException(new InvalidInputException("Move out of bounds. (" + c + ")"));
        }
    }


    /**
     * Checks if there is a winner, if so, returns player id.
     * @param args :
     * @return : Returns player id if there is a winner, otherwise returns 0.
     */
    public int getWinner(Board b, int inARow) {
        /* Check for horizontal wins */
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                int n = Integer.parseInt(b.getFieldAt(new Point(x,y)));
                Boolean win = true;
                if (n != 0) {
                    for (int i = 0; i < inARow; i++) {
                        if (x + i < b.getWidth()) {
                            if (n != Integer.parseInt(b.getFieldAt(new Point(x + i, y)))) {
                                win = false;
                            }
                        } else {
                            win = false;
                        }
                    }
                    if (win) {
                        return n;
                    }
                }
            }
        }

        /* Check for vertical wins */
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                int n = Integer.parseInt(b.getFieldAt(new Point(x,y)));
                Boolean win = true;
                if (n != 0) {
                    for (int i = 0; i < inARow; i++) {
                        if (y + i < b.getHeight()) {
                            if (n != Integer.parseInt(b.getFieldAt(new Point(x, y + i)))) {
                                win = false;
                            }
                        } else {
                            win = false;
                        }
                    }
                    if (win) {
                        return n;
                    }
                }
            }
        }

        /* Check for diagonal wins */
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                int n = Integer.parseInt(b.getFieldAt(new Point(x,y)));
                Boolean win = true;
                if (n != 0) {
                    for (int i = 0; i < inARow; i++) {
                        if (x - i >= 0 && y + i < b.getHeight()) {
                            if (n != Integer.parseInt(b.getFieldAt(new Point(x - i, y + i)))) {
                                win = false;
                            }
                        } else {
                            win = false;
                        }
                    }
                    if (win) {
                        return n;
                    }
                }
            }
        }
        /* Check for anti diagonal wins */
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                int n = Integer.parseInt(b.getFieldAt(new Point(x,y)));
                Boolean win = true;
                if (n != 0) {
                    for (int i = 0; i < inARow; i++) {
                        if (x + i < b.getWidth() && y + i < b.getHeight()) {
                            if (n != Integer.parseInt(b.getFieldAt(new Point(x + i, y + i)))) {
                                win = false;
                            }
                        } else {
                            win = false;
                        }
                    }
                    if (win) {
                        return n;
                    }
                }
            }
        }
        return 0;
    }
}