package io.riddles.connectfour.game.processor;

import io.riddles.connectfour.game.player.ConnectfourPlayer;
import io.riddles.connectfour.game.state.ConnectfourState;
import io.riddles.javainterface.exception.InvalidInputException;
import io.riddles.connectfour.game.data.Coordinate;
import io.riddles.connectfour.game.data.ConnectfourBoard;
import io.riddles.connectfour.game.move.ConnectfourMove;

import java.util.ArrayList;

/**
 * Created by joost on 3-7-16.
 */
public class ConnectfourLogic {


    public ConnectfourLogic() {
    }

    public ConnectfourState transformBoard(ConnectfourState state, ConnectfourMove move, ArrayList<ConnectfourPlayer> players) throws InvalidInputException {

        if (move.getException() == null) {
            transformMoveLocation(state, move, players);
        } else {
        }
        return state;
    }


    private void transformMoveLocation(ConnectfourState state, ConnectfourMove move, ArrayList<ConnectfourPlayer> players) {
        ConnectfourPlayer p = move.getPlayer();
        ConnectfourBoard board = state.getBoard();

        int pId = p.getId();

        Coordinate c = move.getCoordinate();

        if (c.getX() < board.getWidth() && c.getX() >= 0) { /* Move within range */
            for (int y = board.getHeight()-1; y >= 0; y--) { // From bottom column up
                Coordinate tmpC = new Coordinate(c.getX(),y);
                if (board.getFieldAt(tmpC) == 0) {
                    board.setFieldAt(tmpC, pId);
                }
            }
            move.setException(new InvalidInputException("Column " + c.getX() + " is full"));
        } else {
            move.setException(new InvalidInputException("Move out of bounds. (" + c.getX() + ")"));
        }
    }
}