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

import io.riddles.connectfour.game.processor.ConnectfourProcessor;
import io.riddles.connectfour.game.state.ConnectfourState;
import io.riddles.connectfour.game.state.ConnectfourStateSerializer;
import org.json.JSONArray;
import org.json.JSONObject;

import io.riddles.javainterface.game.AbstractGameSerializer;

/**
 * ConnectfourSerializer takes a ConnectfourState and serialises it and all previous states into a JSON String.
 * Customize this to add all game specific data to the output.
 *
 * @author Joost - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class ConnectfourSerializer extends AbstractGameSerializer<ConnectfourProcessor, ConnectfourState> {

    public ConnectfourSerializer() {
        super();
    }

    @Override
    public String traverseToString(ConnectfourProcessor processor, ConnectfourState initialState) {
        JSONObject game = new JSONObject();

        game = addDefaultJSON(initialState, game, processor);

        // put all states
        ConnectfourStateSerializer serializer = new ConnectfourStateSerializer();
        JSONArray states = new JSONArray();

        states.put(serializer.traverseToJson(initialState));

        ConnectfourState state = initialState;
        while (state.hasNextState()) {
            state = (ConnectfourState) state.getNextState();
            states.put(serializer.traverseToJson(state));
        }

        game.put("states", states);

        return game.toString();
    }
}
