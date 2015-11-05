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

package com.theaigames.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.theaigames.engine.Engine;
import com.theaigames.engine.Logic;
import com.theaigames.engine.io.IOPlayer;
import com.theaigames.game.player.AbstractPlayer;

import com.theaigames.connections.Amazon;
import com.theaigames.connections.Database;

/**
 * abstract class AbstractGame
 * 
 * DO NOT EDIT THIS FILE
 * 
 * Extend this class with your main method. In the main method, create an
 * instance of your Logic and run setupEngine() and runEngine()
 * 
 * @author Jim van Eeden <jim@starapple.nl>
 */

public abstract class AbstractGame implements Logic {

	private String gameIdString;
	
	public Engine engine;
	public GameHandler processor;
	
	public int maxRounds;
	
	public boolean DEV_MODE = !false; // turn this on for local testing
	public String TEST_BOT; // command for the test bot in DEV_MODE
	public int NUM_TEST_BOTS; // number of bots for this game
	
	public AbstractGame() {
		maxRounds = -1; // set this later if there is a maximum amount of rounds for this game
	}

	/**
	 * Partially sets up the engine
	 * @param args : command line arguments passed on running of application
	 * @throws IOException
	 * @throws RuntimeException
	 */
	public void setupEngine(String args[]) throws IOException, RuntimeException {
		
		List<String> botDirs = new ArrayList<String>();
		List<String> botIds = new ArrayList<String>();
		
		this.gameIdString = args[0];
		
		// get the bot id's and location of bot program
//		for(int i=1; i<args.length; i++) {
//			switch(i % 2) {
//			case 0:
//				botIds.add(args[i]);
//				break;
//			case 1:
//				botDirs.add(args[i]);
//				break;
//			}
//		}
		
		// check is the starting arguments are passed correctly
//		if(botIds.isEmpty() || botDirs.isEmpty() || botIds.size() != botDirs.size())
//			throw new RuntimeException("Missing some arguments");
		
		// create engine
		this.engine = new Engine();
		
		// add the players
//		for(int i=0; i<botIds.size(); i++) {
//			this.engine.addPlayer("/opt/aigames/scripts/run_bot.sh aiplayer1 " + botDirs.get(i), botIds.get(i));
//		}
		this.engine.addPlayer("java -cp /home/joost/workspace/MyBot2/bin/ MyBot", "player1");
		this.engine.addPlayer("java -cp /home/joost/workspace/MyBot2/bin/ MyBot", "player2");
	}
	
	/**
	 * Implement this class. Set logic in the engine and start it to run the game
	 */
	protected abstract void runEngine() throws Exception;
	
	/**
	 * @return : True when the game is over
	 */
	@Override
	public boolean isGameOver()
	{
		if (this.processor.isGameOver() 
				|| (this.maxRounds >= 0 && this.processor.getRoundNumber() > this.maxRounds) ) {
        	return true;
        }
        return false;
	}
	
	/**
	 * Play one round of the game
	 * @param roundNumber : round number
	 */
	@Override
    public void playRound(int roundNumber) 
	{
		for(IOPlayer ioPlayer : this.engine.getPlayers())
			ioPlayer.addToDump(String.format("Round %d", roundNumber));
		
		this.processor.playRound(roundNumber);
	}
	
	/**
	 * close the bot processes, save, exit program
	 */
	@Override
	public void finish() throws Exception
	{
		// stop the bots
		for(IOPlayer ioPlayer : this.engine.getPlayers())
			ioPlayer.finish();
		Thread.sleep(100);
		
		if(DEV_MODE) { // print the game file when in DEV_MODE
			String playedGame = this.processor.getPlayedGame();
			System.out.println(playedGame);
		} else { // save the game to database
			try {
				this.saveGame();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Done.");
		
        System.exit(0);
	}
	
	/**
	 * Does everything that is needed to store the output of a game
	 */
	public void saveGame() {
		
		AbstractPlayer winner = this.processor.getWinner();
		ObjectId winnerId = null;
		int score = this.processor.getRoundNumber() - 1;
		BasicDBObject errors = new BasicDBObject();
		BasicDBObject dumps = new BasicDBObject();
		String gamePath = "games/" + this.gameIdString;

		if(winner != null) {
			System.out.println("winner: " + winner.getName());
			winnerId = new ObjectId(winner.getBot().getIdString());
		} else {
			System.out.println("winner: draw");
		}
		
		System.out.println("Saving the game...");
		
		// save the visualization file to amazon
		Amazon.connectToAmazon();
		String savedFilePath = Amazon.saveToAmazon(this.processor.getPlayedGame(), gamePath + "/visualization");
		
		// save errors and dumps to amazon and create object for database
		int botNr = 0;
		for(IOPlayer ioPlayer : this.engine.getPlayers()) {
			botNr++;
			errors.append(ioPlayer.getIdString(), Amazon.saveToAmazon(ioPlayer.getStderr(), String.format("%s/bot%dErrors", gamePath, botNr)));
			dumps.append(ioPlayer.getIdString(), Amazon.saveToAmazon(ioPlayer.getDump(), String.format("%s/bot%dDump", gamePath, botNr)));
		}
		
		// store everything in the database
		Database.connectToDatabase();
		Database.storeGameInDatabase(this.gameIdString, winnerId, score, savedFilePath, errors, dumps);
	}
}
