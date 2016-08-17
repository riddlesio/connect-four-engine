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
        ConnectfourBoard b = state.getBoard();
        Coordinate c = move.getCoordinate();
        b.updateMacroboard();

        if (c.getX() < b.getWidth() && c.getY() < b.getHeight() && c.getX() >= 0 && c.getY() >= 0) { /* Move within range */
            if (b.isInActiveMicroboard(c.getX(), c.getY())) { /* Move in active microboard */
                if (b.getFieldAt(c) == 0) { /*Field is available */
                    b.setFieldAt(c, pId);
                    b.setLastMove(c);
                    b.updateMacroboard();
                    /* Success */
                } else {
                    move.setException(new InvalidInputException("Error: chosen position is already filled"));
                }
            } else {
                move.setException(new InvalidInputException("Error: move not in active macroboard"));
            }
        } else {
            move.setException(new InvalidInputException("Error: move out of bounds"));
        }
    }
}