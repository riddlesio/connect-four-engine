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

package io.riddles.connectfour.game.processor;

import java.util.ArrayList;

import io.riddles.connectfour.game.player.ConnectfourPlayer;
import io.riddles.connectfour.game.state.ConnectfourPlayerState;
import io.riddles.javainterface.engine.AbstractEngine;
import io.riddles.javainterface.game.player.PlayerProvider;
import io.riddles.connectfour.game.move.*;
import io.riddles.connectfour.game.state.ConnectfourState;
import io.riddles.javainterface.game.processor.PlayerResponseProcessor;
import io.riddles.javainterface.game.state.AbstractPlayerState;
import io.riddles.javainterface.io.PlayerResponse;

/**
 * This file is a part of Connectfour
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * ConnectfourProcessor shall process a State and return the result.
 *
 * @author joost
 */
public class ConnectfourProcessor extends PlayerResponseProcessor<ConnectfourState, ConnectfourPlayer> {
    private ConnectfourLogic logic;

    public ConnectfourProcessor(PlayerProvider<ConnectfourPlayer> playerProvider) {
        super(playerProvider);
        this.logic = new ConnectfourLogic();

    }

    /**
     * Play one round of the game. It takes a ConnectfourState
     * Return
     * the ConnectfourState that will be the state for the next round.
     * @param roundNumber The current round number
     * @param state The current state
     * @return The ConnectfourState that will be the start of the next round
     */
    @Override
    public ConnectfourState createNextStateFromResponse(ConnectfourState state, PlayerResponse input, int roundNumber) {

        /* Clone playerStates for next State */
        ArrayList<ConnectfourPlayerState> nextPlayerStates = clonePlayerStates(state.getPlayerStates());

        ConnectfourState nextState = new ConnectfourState(state, state.getPlayerStates(), roundNumber);
        nextState.setPlayerId(input.getPlayerId());

        ConnectfourPlayerState playerState = getActivePlayerState(nextPlayerStates, input.getPlayerId());

        ConnectfourMoveDeserializer deserializer = new ConnectfourMoveDeserializer();
        ConnectfourMove move = deserializer.traverse(input.getValue());
        playerState.setMove(move);

        try {
            logic.transform(nextState, playerState);
        } catch (Exception e) {
            //LOGGER.info(String.format("Unknown response: %s", input.getValue()));
        }

        nextState.setPlayerstates(nextPlayerStates);
        String winnerString = "none";
        if (getWinnerId(nextState) != null) {
            winnerString = String.valueOf(getWinnerId(nextState));
        }

        nextState.setWinnerString(winnerString);

        return nextState;
    }

    private ConnectfourPlayerState getActivePlayerState(ArrayList<ConnectfourPlayerState> playerStates, int id) {
        for (ConnectfourPlayerState playerState : playerStates) {
            if (playerState.getPlayerId() == id) { return playerState; }
        }
        return null;
    }

    private ArrayList<ConnectfourPlayerState> clonePlayerStates(ArrayList<ConnectfourPlayerState> playerStates) {
        ArrayList<ConnectfourPlayerState> nextPlayerStates = new ArrayList<>();
        for (ConnectfourPlayerState playerState : playerStates) {
            ConnectfourPlayerState nextPlayerState = playerState.clone();
            nextPlayerStates.add(nextPlayerState);
        }
        return nextPlayerStates;
    }

    /**
     * The stopping condition for this game.
     * @param state the ConnectfourState to determine whether the game has ended.
     * @return boolean True if the game is over, false otherwise
     */
    @Override
    public boolean hasGameEnded(ConnectfourState state) {
        if (state.getRoundNumber() > AbstractEngine.configuration.getInt("maxRounds")) return true;

        boolean returnVal = false;
        if (state.getBoard().getNrAvailableFields() == 0) returnVal = true;
        if (getWinnerId(state) != null) returnVal = true;

        return returnVal;
    }

    /* Returns winner playerId, or null if there's no winner. */
    @Override
    public Integer getWinnerId(ConnectfourState state) {
        String w = logic.getWinner(state.getBoard(), 4);
        if (w == null) return null;
        return Integer.parseInt(w);
    }

    @Override
    public void sendUpdates(ConnectfourState state, ConnectfourPlayer player) {
        player.sendUpdate("round", state.getRoundNumber());
        player.sendUpdate("field", state.getBoard().toString());
    }

    @Override
    public double getScore(ConnectfourState state) {
        return state.getRoundNumber();
    }

    @Override
    public Enum getActionType(ConnectfourState goState, AbstractPlayerState playerState) {
        return ActionType.MOVE;
    }
}
