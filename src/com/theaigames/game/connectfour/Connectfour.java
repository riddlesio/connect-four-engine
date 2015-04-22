package com.theaigames.game.connectfour;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import com.theaigames.connections.Filewriter;
import com.theaigames.connections.JSONWriter;
import com.theaigames.engine.io.IOPlayer;
import com.theaigames.game.AbstractGame;
import com.theaigames.game.AbstractPlayer;
import com.theaigames.game.connectfour.Field;

public class Connectfour extends AbstractGame {
	
	private final int TIMEBANK_MAX = 10000;
	private final int TIME_PER_MOVE = 500;
	private final int FIELD_COLUMNS = 7;
	private final int FIELD_ROWS = 6;	
	private List<Player> players;
	private Field mField;

	@Override
	public void setupGame(ArrayList<IOPlayer> ioPlayers) throws Exception {		
		System.out.println("Setting up game...");
		
		// create all the players and everything they need
		this.players = new ArrayList<Player>();
		
		// create the playing field
		this.mField = new Field(FIELD_COLUMNS, FIELD_ROWS);
		
		for(int i=0; i<ioPlayers.size(); i++) {
			// create the player
			String playerName = String.format("player%d", i+1);
			Player player = new Player(playerName, ioPlayers.get(i), TIMEBANK_MAX, TIME_PER_MOVE);
			System.out.println("Adding player " + playerName);
			this.players.add(player);
			
			// send the settings
			sendSettings(player);
		}
		
		// create the processor
		super.processor = new Processor(this.players, this.mField);
	}

	@Override
	public void sendSettings(Player player) {
		player.sendSetting("timebank", TIMEBANK_MAX);
		player.sendSetting("time_per_move", TIME_PER_MOVE);
		player.sendSetting("field_columns", FIELD_COLUMNS);
		player.sendSetting("field_rows", FIELD_ROWS);
	}

	@Override
	protected void runEngine() throws Exception {
		super.engine.setLogic(this);
		super.engine.start();
	}
	
	public static void main(String args[]) throws Exception {
		Connectfour game = new Connectfour();
		
		game.setupEngine(args);
		game.runEngine();
	}
	
	/**
	 * Does everything that is needed to store the output of a game
	 */
	@Override
	public void saveGame() {
		
		AbstractPlayer winner = this.processor.getWinner();
		int score = this.processor.getRoundNumber() - 1;

		String gamePath = "game" + this.gameIdString;

		if(winner != null) {
			System.out.println("winner: " + winner.getName());
		} else {
			System.out.println("winner: draw");
		}
		
		System.out.println("Saving the game...");
		Filewriter f = new Filewriter();
		for(IOPlayer ioPlayer : this.engine.getPlayers()) {
			try {
				f.write (gamePath + "_bot" + ioPlayer.getIdString() + "Errors", ioPlayer.getStderr());
				f.write (gamePath + "_bot" + ioPlayer.getIdString() + "Dump", ioPlayer.getDump());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		JSONWriter j = new JSONWriter();
		Hashtable<String,String> settings = new Hashtable<String, String>();
		settings.put("TIMEBANK_MAX", String.valueOf(TIMEBANK_MAX));
		settings.put("TIME_PER_MOVE", String.valueOf(TIME_PER_MOVE));
		settings.put("FIELD_COLUMNS", String.valueOf(FIELD_COLUMNS));
		settings.put("FIELD_ROWS", String.valueOf(FIELD_ROWS));
		settings.put("WINNER", winner.getName());
		
		try {
			j.write(settings, this.processor.getMoves());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
