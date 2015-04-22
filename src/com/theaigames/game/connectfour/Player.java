package com.theaigames.game.connectfour;

import com.theaigames.engine.io.IOPlayer;
import com.theaigames.game.AbstractPlayer;
import com.theaigames.game.connectfour.Field;

public class Player extends AbstractPlayer {
	public Player(String name, IOPlayer bot, long maxTimeBank, long timePerMove) {
		super(name, bot, maxTimeBank, timePerMove);
	}
}
