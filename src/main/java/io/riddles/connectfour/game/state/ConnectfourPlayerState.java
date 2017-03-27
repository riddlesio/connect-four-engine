package io.riddles.connectfour.game.state;

import io.riddles.connectfour.game.move.ConnectfourMove;
import io.riddles.javainterface.game.state.AbstractPlayerState;

/**
 */
public class ConnectfourPlayerState extends AbstractPlayerState<ConnectfourMove> {


    public ConnectfourPlayerState(int playerId) {
        super(playerId);
    }


    public ConnectfourPlayerState clone() {
        ConnectfourPlayerState psClone = new ConnectfourPlayerState(this.playerId);
        return psClone;
    }

    public int getPlayerId() { return this.playerId; }
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

}
