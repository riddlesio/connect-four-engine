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

package io.riddles.connectfour.game.state;

import io.riddles.connectfour.game.move.ConnectfourMove;
import org.json.JSONObject;
import io.riddles.javainterface.game.state.AbstractStateSerializer;

/**
 * ConnectfourSerializer takes a ConnectfourState and serialises it into a String or JSONObject.
 *
 * @author jim
 */
public class ConnectfourSerializer extends AbstractStateSerializer<ConnectfourState> {

    @Override
    public String traverseToString(ConnectfourState state) {
        return visitState(state).toString();
    }

    @Override
    public JSONObject traverseToJson(ConnectfourState state) throws NullPointerException {
        return visitState(state);
    }

    private JSONObject visitState(ConnectfourState state) throws NullPointerException {
        JSONObject stateJson = new JSONObject();
        stateJson.put("move", state.getMoveNumber());

        ConnectfourMove move = state.getMoves().get(0);

        stateJson.put("movetype", move.getMoveType());
        stateJson.put("winner", ""); /* TODO: find a winner */

        stateJson.put("field", state.getBoard().toString());
        stateJson.put("move", state.getMoveNumber());

        if (move.getException() == null) {
            stateJson.put("exception", JSONObject.NULL);
            stateJson.put("illegalMove", "");
        } else {
            stateJson.put("exception", move.getException().getMessage());
            stateJson.put("illegalMove", move.getException().getMessage());
        }
        return stateJson;
    }
}
