package com.theaigames.connectfour;

import com.theaigames.game.moves.AbstractMove;
import com.theaigames.game.player.AbstractPlayer;

public class MoveResult extends Move {
	private String mBoard;

	public MoveResult(AbstractPlayer player, Field field) {
		super(player);
		mBoard = field.toString();
	}
	
	public String toString() {
		return mBoard;
	}
	

}
