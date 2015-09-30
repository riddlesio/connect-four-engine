package com.theaigames.connectfour;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.theaigames.connectfour.Field;
import com.theaigames.engine.io.IOPlayer;
import com.theaigames.game.AbstractGame;
import com.theaigames.game.player.AbstractPlayer;

public class Connectfour extends AbstractGame {
	
	private final int TIMEBANK_MAX = 10000;
	private final int TIME_PER_MOVE = 500;
	private final int FIELD_COLUMNS = 7;
	private final int FIELD_ROWS = 6;	
	private List<Player> players;
	private Field mField;

	@Override
	public void setupGame(ArrayList<IOPlayer> ioPlayers) throws Exception {			
		// create all the players and everything they need
		this.players = new ArrayList<Player>();
		
		// create the playing field
		this.mField = new Field(FIELD_COLUMNS, FIELD_ROWS);
		
		for(int i=0; i<ioPlayers.size(); i++) {
			// create the player
			String playerName = ""+(i+1);
			Player player = new Player(playerName, ioPlayers.get(i), TIMEBANK_MAX, TIME_PER_MOVE, i+1);
			this.players.add(player);

		}
		for(Player player : this.players) {
			sendSettings(player);
		}
		
		// create the processor
		super.processor = new Processor(this.players, this.mField);
	}

	public void sendSettings(Player player) {
		player.sendSetting("timebank", TIMEBANK_MAX);
		player.sendSetting("time_per_move", TIME_PER_MOVE);
		player.sendSetting("field_columns", FIELD_COLUMNS);
		player.sendSetting("field_rows", FIELD_ROWS);
		player.sendSetting("bot_id", player.getId());
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

	@Override
	public void sendSettings(AbstractPlayer player) {
		// TODO Auto-generated method stub
		
	}
}
