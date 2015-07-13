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
import java.util.List;
import java.util.Random;

import com.theaigames.engine.io.IOPlayer;
import com.theaigames.game.AbstractMove;
import com.theaigames.game.AbstractPlayer;
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
		int playerNr = 1;
		for (Player player : mPlayers) {
			if (getWinner() == null) {
				String response = player.requestMove("move");
				
				int counter = 0;
				Boolean moveAdded = false;
				Move move = new Move(player);
				if (parseResponse(response, player)) {
					move = new Move(player);
					move.setColumn(mField.getLastColumn());
					mMoves.add(move);
				} else {
					move = new Move(player);
					move.setColumn(mField.getLastColumn());
					move.setIllegalMove(mField.getLastError());
					mMoves.add(move);
					if (parseResponse(response, player)) {
						move = new Move(player);
						move.setColumn(mField.getLastColumn());
						mMoves.add(move);
					} else {
						move = new Move(player);
						move.setColumn(mField.getLastColumn());
						move.setIllegalMove(mField.getLastError());
						mMoves.add(move);
						if (parseResponse(response, player)) {
							move = new Move(player);
							move.setColumn(mField.getLastColumn());
							mMoves.add(move);
						}
					}
				}
				
				
				/*
				while (!parseResponse(response, player) && counter < MAX_TRIES) { // When move is invalid, keep asking for a new move until MAX_TRIES.
					Move move = new Move(player);
					move.setIllegalMove(mField.getLastError());
					move.setColumn(mField.getLastColumn());
					response = player.requestMove("move");
					System.out.println("Try " + counter + " Response for " + player.getName() + ": " + mField.getLastError());
					counter ++;
					if (counter >= MAX_TRIES) {
						move.setIllegalMove("Max tries exceeded.");
						System.out.println("Max tries exceeded.");

					}
					mMoves.add(move);
					MoveResult moveResult = new MoveResult(player, mField);
					moveResult.setIllegalMove(move.getIllegalMove());
					mMoveResults.add(moveResult);
					moveAdded = true;
				}
				if (!moveAdded) {
					Move move = new Move(player);
					move.setColumn(mField.getLastColumn());
					mMoves.add(move);
					MoveResult moveResult = new MoveResult(player, mField);
					moveResult.setIllegalMove(move.getIllegalMove());
					mMoveResults.add(moveResult);
				}
				*/
				player.sendUpdate("field", player, mField.toString());
				playerNr++;
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
        	if (mField.addDisc(column, player.getName())) {
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
		String winner = mField.getWinnerName();
		if (winner != null) {
			mField.dumpBoard();
			
			for (Player player : mPlayers) {
				if (player.getName().equals(winner)) {
					return player;
				}
			}

		}
		return null;
	}

	@Override
	public String getPlayedGame() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Returns a List of Moves played in this game
	 * @param args : 
	 * @return : List with Move objects
	 */
	@Override
	public List<Move> getMoves() {
		return mMoves;
	}
	
	@Override
	public Field getField() {
		return mField;
	}
}
