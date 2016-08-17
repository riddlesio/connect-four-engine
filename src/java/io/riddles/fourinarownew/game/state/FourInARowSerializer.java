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

package io.riddles.fourinarownew.game.state;

import io.riddles.fourinarownew.game.move.FourInARowMove;
import org.json.JSONObject;
import io.riddles.javainterface.game.state.AbstractStateSerializer;

/**
 * FourInARowSerializer takes a FourInARowState and serialises it into a String.
 *
 * @author jim
 */
public class FourInARowSerializer extends AbstractStateSerializer<FourInARowState> {

    @Override
    public String traverseToString(FourInARowState state) {
        return visitState(state, false).toString();
    }

    @Override
    public JSONObject traverseToJson(FourInARowState state) throws NullPointerException {
        return visitState(state, false);
    }

    public JSONObject traverseToJson(FourInARowState state, Boolean showPossibleMoves) throws NullPointerException {
        return visitState(state, showPossibleMoves);
    }

    private JSONObject visitState(FourInARowState state, Boolean showPossibleMoves) throws NullPointerException {
        JSONObject stateJson = new JSONObject();
        stateJson.put("move", state.getMoveNumber());

        FourInARowMove move = state.getMoves().get(0);

        stateJson.put("movetype", move.getMoveType());
        stateJson.put("winner", ""); /* TODO: find a winner */

        if (showPossibleMoves) {
            stateJson.put("field", state.getPossibleMovesPresentationString());
        } else {
            stateJson.put("field", state.getFieldPresentationString());
        }
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
