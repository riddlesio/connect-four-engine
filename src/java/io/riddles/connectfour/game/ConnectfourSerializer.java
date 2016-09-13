/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package io.riddles.connectfour.game;

import io.riddles.connectfour.game.player.ConnectfourPlayer;
import io.riddles.connectfour.game.processor.ConnectfourProcessor;
import io.riddles.connectfour.game.state.ConnectfourState;
import io.riddles.javainterface.game.player.AbstractPlayer;
import org.json.JSONArray;
import org.json.JSONObject;

import io.riddles.javainterface.game.AbstractGameSerializer;

/**
 * ConnectfourSerializer takes a ConnectfourState and serialises it and all previous states into a JSON String.
 * Customize this to add all game specific data to the output.
 *
 * @author jim
 */
public class ConnectfourSerializer extends
        AbstractGameSerializer<ConnectfourProcessor, ConnectfourState> {

    @Override
    public String traverseToString(ConnectfourProcessor processor, ConnectfourState initialState) {
        JSONObject game = new JSONObject();


        // add all states
        JSONArray states = new JSONArray();
        ConnectfourState state = initialState;
        io.riddles.connectfour.game.state.ConnectfourSerializer serializer = new io.riddles.connectfour.game.state.ConnectfourSerializer();
        while (state.hasNextState()) {
            state = (ConnectfourState) state.getNextState();

            states.put(serializer.traverseToJson(state));
        }

        JSONObject matchdata = new JSONObject();
        matchdata.put("states", states);
        matchdata.put("settings", getSettingsJSON(game, processor));

        game.put("playerData", getPlayerDataJSON(processor));

        game.put("matchData", matchdata);

        // add score
        game.put("score", processor.getScore());

        return game.toString();
    }

    protected JSONObject getSettingsJSON(JSONObject game, ConnectfourProcessor processor) {
        JSONObject settings = new JSONObject();


        JSONObject field = new JSONObject();
        field.put("width", 9); /* TODO: this is idiotic */
        field.put("height", 9); /* TODO: this is idiotic */
        settings.put("field", field);

        settings.put("players", getPlayersJSON(processor));

        // add winner
        String winner = "null";
        if (processor.getWinner() != null) {
            winner = processor.getWinner().getId() + "";
        }
        settings.put("winnerplayer", winner);

        return settings;
    }

    protected JSONObject getPlayersJSON(ConnectfourProcessor processor) {

        JSONArray playerNames = new JSONArray();
        for (Object obj : processor.getPlayers()) {
            AbstractPlayer player = (AbstractPlayer) obj;
            playerNames.put(player.getName());
        }

        JSONObject players = new JSONObject();
        players.put("count", processor.getPlayers().size());
        players.put("names", playerNames);

        return players;
    }


    protected JSONArray getPlayerDataJSON(ConnectfourProcessor processor) {

        JSONArray playerData = new JSONArray();
        for (ConnectfourPlayer obj : processor.getPlayers()) {
            ConnectfourPlayer player = obj;
            JSONObject p = new JSONObject();

            p.put("name", player.getName());
            //p.put("emailHash", player.getEmail());
            playerData.put(p);
        }

        return playerData;
    }
}
