// Copyright 2015 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//	
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package com.theaigames.connectfour;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.theaigames.engine.io.IOPlayer;
import com.theaigames.game.moves.AbstractMove;
import com.theaigames.game.player.AbstractPlayer;
import com.theaigames.game.GameHandler;

public class Processor implements GameHandler {
	
	private int mRoundNumber = 0;
	private List<Player> mPlayers;
	private List<Move> mMoves;
	private List<Disc> mWinningDiscs;
	private List<MoveResult> mMoveResults;
	private Field mField;
	
	private final int MAX_TRIES = 3;

	public Processor(List<Player> players, Field field) {
		mPlayers = players;
		mField = field;
		mMoves = new ArrayList<Move>();
		mMoveResults = new ArrayList<MoveResult>();
	}

	@Override
	public void playRound(int roundNumber) {
		mRoundNumber = roundNumber;
		for (Player player : mPlayers) {
			if (getWinner() == null) {
				String response = player.requestMove("move");
				
				Move move = new Move(player);
				MoveResult moveResult = new MoveResult(player, mField);
				if (parseResponse(response, player)) {
					move.setColumn(mField.getLastColumn());
					mMoves.add(move);
					moveResult = new MoveResult(player, mField);
					moveResult.setColumn(mField.getLastColumn());
					mMoveResults.add(moveResult);
				} else {
					move = new Move(player);
					moveResult = new MoveResult(player, mField);
					move.setColumn(mField.getLastColumn());
					move.setIllegalMove(mField.getLastError());
					mMoves.add(move);
					moveResult = new MoveResult(player, mField);
					moveResult.setColumn(mField.getLastColumn());
					mMoveResults.add(moveResult);
					if (parseResponse(response, player)) {
						move = new Move(player);
						moveResult = new MoveResult(player, mField);
						move.setColumn(mField.getLastColumn());
						mMoves.add(move);
						moveResult = new MoveResult(player, mField);
						moveResult.setColumn(mField.getLastColumn());
						mMoveResults.add(moveResult);
					} else {
						move = new Move(player);
						moveResult = new MoveResult(player, mField);
						move.setColumn(mField.getLastColumn());
						move.setIllegalMove(mField.getLastError());
						mMoves.add(move);
						moveResult = new MoveResult(player, mField);
						moveResult.setColumn(mField.getLastColumn());
						mMoveResults.add(moveResult);
						if (parseResponse(response, player)) {
							move = new Move(player);
							moveResult = new MoveResult(player, mField);
							move.setColumn(mField.getLastColumn());
							mMoves.add(move);
							moveResult = new MoveResult(player, mField);
							moveResult.setColumn(mField.getLastColumn());
							mMoveResults.add(moveResult);
						}
					}
				}
				
				player.sendUpdate("field", player, mField.toString());
			}
		}
	}
	
	/**
	 * Parses player response and inserts disc in field
	 * @param args : command line arguments passed on running of application
	 * @return : true if valid move, otherwise false
	 */
	private Boolean parseResponse(String r, Player player) {
		String[] parts = r.split(" ");

        if (parts[0].equals("place_disc")) {
        	int column = Integer.parseInt(parts[1]);
        	if (mField.addDisc(column, player.getId())) {
        		return true;
        	}
        }
        return false;
	}

	@Override
	public int getRoundNumber() {
		return this.mRoundNumber;
	}

	@Override
	public AbstractPlayer getWinner() {
		int winner = mField.getWinner();
		if (winner != 0) {
			mField.dumpBoard();
			for (Player player : mPlayers) {
				if (player.getId() == winner) {
					return player;
				}
			}

		}
		return null;
	}

	@Override
	public String getPlayedGame() {
		JSONObject output = new JSONObject();
		AbstractPlayer winner = getWinner();
		
		/* Create array of winning discs */
		Disc winDisc = getField().getWinDisc();
		String winType = getField().getWinType();
		JSONObject winDiscsJSON = new JSONObject();
		JSONObject winDiscJSON = new JSONObject();
		int winColumn = winDisc.getColumn();
		int winRow = winDisc.getRow();
		try {
			if (winType.equals("horizontal")) {
				winDiscJSON.put("column", winColumn); winDiscJSON.put("row", winRow);
				winDiscsJSON.put("windisc0", winDiscJSON); winDiscJSON = new JSONObject();
				winDiscJSON.put("column", winColumn+1); winDiscJSON.put("row", winRow);
				winDiscsJSON.put("windisc1", winDiscJSON); winDiscJSON = new JSONObject();				
				winDiscJSON.put("column", winColumn+2); winDiscJSON.put("row", winRow);
				winDiscsJSON.put("windisc2", winDiscJSON); winDiscJSON = new JSONObject();
				winDiscJSON.put("column", winColumn+3); winDiscJSON.put("row", winRow);
				winDiscsJSON.put("windisc3", winDiscJSON);
			} else if (winType.equals("vertical")) {
				winDiscJSON.put("column", winColumn); winDiscJSON.put("row", winRow);
				winDiscsJSON.put("windisc0", winDiscJSON); winDiscJSON = new JSONObject();
				winDiscJSON.put("column", winColumn); winDiscJSON.put("row", winRow+1);
				winDiscsJSON.put("windisc1", winDiscJSON); winDiscJSON = new JSONObject();				
				winDiscJSON.put("column", winColumn); winDiscJSON.put("row", winRow+2);
				winDiscsJSON.put("windisc2", winDiscJSON); winDiscJSON = new JSONObject();
				winDiscJSON.put("column", winColumn); winDiscJSON.put("row", winRow+3);
				winDiscsJSON.put("windisc3", winDiscJSON);
			} else if (winType.equals("diagonal")) {
				winDiscJSON.put("column", winColumn); winDiscJSON.put("row", winRow);
				winDiscsJSON.put("windisc0", winDiscJSON); winDiscJSON = new JSONObject();
				winDiscJSON.put("column", winColumn-1); winDiscJSON.put("row", winRow+1);
				winDiscsJSON.put("windisc1", winDiscJSON); winDiscJSON = new JSONObject();				
				winDiscJSON.put("column", winColumn-2); winDiscJSON.put("row", winRow+2);
				winDiscsJSON.put("windisc2", winDiscJSON); winDiscJSON = new JSONObject();
				winDiscJSON.put("column", winColumn-3); winDiscJSON.put("row", winRow+3);
				winDiscsJSON.put("windisc3", winDiscJSON);
			} else if (winType.equals("antidiagonal")) {
				winDiscJSON.put("column", winColumn); winDiscJSON.put("row", winRow);
				winDiscsJSON.put("windisc0", winDiscJSON); winDiscJSON = new JSONObject();
				winDiscJSON.put("column", winColumn+1); winDiscJSON.put("row", winRow+1);
				winDiscsJSON.put("windisc1", winDiscJSON); winDiscJSON = new JSONObject();				
				winDiscJSON.put("column", winColumn+2); winDiscJSON.put("row", winRow+2);
				winDiscsJSON.put("windisc2", winDiscJSON); winDiscJSON = new JSONObject();
				winDiscJSON.put("column", winColumn+3); winDiscJSON.put("row", winRow+3);
				winDiscsJSON.put("windisc3", winDiscJSON);
			}
			
			Hashtable<String,String> settings = new Hashtable<String, String>();
			
			JSONArray playerNames = new JSONArray();
			for(Player player : this.mPlayers) {
				playerNames.put(player.getName());
			}
			
			output.put("settings", new JSONObject()
			.put("field", new JSONObject()
					.put("width", String.valueOf(getField().getNrColumns()))
					.put("height", String.valueOf(getField().getNrRows())))
			.put("players", new JSONObject()
					.put("count", this.mPlayers.size()) // could maybe be removed
					.put("names", playerNames))
					.put("winnerplayer", winner.getName())
			);		
			output.put("windiscs", winDiscsJSON);
			
			JSONArray states = new JSONArray();
			JSONObject state = new JSONObject();
			int counter = 0;
			for (MoveResult move : mMoveResults) {
				state = new JSONObject();
				state.put("field", move.toString());
				state.put("move", counter);
				state.put("column", move.getColumn());
				states.put(state);
				counter++;
			}
			output.put("states", states);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}
	

	/**
	 * Returns a List of Moves played in this game
	 * @param args : 
	 * @return : List with Move objects
	 */
	public List<Move> getMoves() {
		return mMoves;
	}
	
	public Field getField() {
		return mField;
	}

	@Override
	public boolean isGameOver() {
		return (getWinner() != null);
	}
}
