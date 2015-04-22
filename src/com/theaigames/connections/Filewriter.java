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
import java.util.Properties;

import com.theaigames.game.AbstractPlayer;

/**
* Filewriter class
* 
* Dumps output to a file
* 
* @author Joost de Meij <joostdemeij@starapple.nl>
*/
public final class Filewriter {
	
	public String errors = "";
	public String dumps = "";
	
	public void write(String tag, String data) throws Exception {
		PrintWriter writer = new PrintWriter(tag + ".txt", "UTF-8");
		writer.println(data);
		writer.close();
	}
}