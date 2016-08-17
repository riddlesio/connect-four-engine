package io.riddles.fourinarownew.game.processor;

import io.riddles.fourinarownew.game.player.FourInARowPlayer;
import io.riddles.javainterface.exception.InvalidInputException;
import io.riddles.fourinarownew.game.data.Coordinate;
import io.riddles.fourinarownew.game.data.FourInARowBoard;
import io.riddles.fourinarownew.game.move.FourInARowMove;
import io.riddles.fourinarownew.game.state.FourInARowState;

import java.util.ArrayList;

/**
 * Created by joost on 3-7-16.
 */
public class FourInARowLogic {


    public FourInARowLogic() {
    }

    public FourInARowState transformBoard(FourInARowState state, FourInARowMove move, ArrayList<FourInARowPlayer> players) throws InvalidInputException {

        if (move.getException() == null) {
            transformMoveLocation(state, move, players);
        } else {
        }
        return state;
    }

    private void transformMoveLocation(FourInARowState state, FourInARowMove move, ArrayList<FourInARowPlayer> players) {
        FourInARowPlayer p = move.getPlayer();
        FourInARowBoard board = state.getBoard();

        int pId = p.getId();
        FourInARowBoard b = state.getBoard();
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