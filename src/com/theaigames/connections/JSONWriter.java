package com.theaigames.connections;

//Copyright 2015 theaigames.com (developers@theaigames.com)

//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at

//  http://www.apache.org/licenses/LICENSE-2.0

//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
//
//For the full copyright and license information, please view the LICENSE
//file that was distributed with this source code.

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import org.json.JSONObject; 

import com.theaigames.game.AbstractPlayer;
import com.theaigames.game.connectfour.Move;

/**
* JSONWriter class
* 
* Dumps game into JSON output
* 
* @author Joost de Meij <joostdemeij@starapple.nl>
*/
public final class JSONWriter {
	
	public String errors = "";
	public String dumps = "";
	
	public void write(JSONObject dataJSON, List<Move> moves) throws Exception {
		JSONObject json = new JSONObject();

		
		/* Convert moves to JSONObject */
		JSONObject movesJSON = new JSONObject();
		int counter = 0;
		for (Move move : moves) {
			JSONObject moveJSON = new JSONObject();
			moveJSON.put("player", move.getPlayer().getName());
			moveJSON.put("column", move.getColumn());
			moveJSON.put("illegalmove", move.getIllegalMove());
			movesJSON.put("move" + counter,  moveJSON);
			counter++;
		}
		json.put("data", dataJSON);
		json.put("moves",  movesJSON);
		
		System.out.println(json.toString());
		
		
		
		
		
	}
}