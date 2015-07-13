package com.theaigames.connectfour;

import com.theaigames.game.AbstractMove;
import com.theaigames.game.AbstractPlayer;

public class MoveResult extends Move {
	private Field mField;

	public MoveResult(AbstractPlayer player, Field field) {
		super(player);
		mField = field;
	}
	

}
