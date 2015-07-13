package com.theaigames.connectfour;

import com.theaigames.connectfour.Field;
import com.theaigames.engine.io.IOPlayer;
import com.theaigames.game.AbstractPlayer;

public class Player extends AbstractPlayer {
	public Player(String name, IOPlayer bot, long maxTimeBank, long timePerMove) {
		super(name, bot, maxTimeBank, timePerMove);
	}
}
