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

import io.riddles.connectfour.game.board.Board;
import io.riddles.connectfour.game.move.ConnectfourMove;
import io.riddles.javainterface.game.state.AbstractStateSerializer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * io.riddles.go.game.state.GoStateSerializer - Created on 6/27/16
 *
 * [description]
 *
 * @author Joost - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class ConnectfourStateSerializer extends AbstractStateSerializer<ConnectfourState> {

    @Override
    public String traverseToString(ConnectfourState state) {
        return visitState(state).toString();
    }

    @Override
    public JSONObject traverseToJson(ConnectfourState state) throws NullPointerException {
        return visitState(state);
    }

    private JSONObject visitState(ConnectfourState state) throws NullPointerException {
        JSONObject stateJSON = new JSONObject();
        Board board = state.getBoard();

        stateJSON.put("round", state.getRoundNumber() + 1);
        stateJSON.put("field", state.getBoard().toString());
        JSONArray playersJSON = new JSONArray();
        ArrayList<ConnectfourPlayerState> players = state.getPlayerStates();
        for (ConnectfourPlayerState p : players) {
            JSONObject playerObj = new JSONObject();
            playersJSON.put(playerObj);
        }
        stateJSON.put("players", playersJSON);
        ConnectfourMove move = state.getPlayerStateById(state.getPlayerId()).getMove();
        String moveString = "";
        String exceptionString = "";
        if (move != null) {
            moveString = move.toString();
            if (move.getException() != null)
                exceptionString = move.getException().toString();
        }
        stateJSON.put("action", moveString);
        stateJSON.put("illegalMove", exceptionString);
        stateJSON.put("player", state.getPlayerId());

        return stateJSON;
    }
}
