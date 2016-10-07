package io.riddles.connectfour.engine;

import io.riddles.connectfour.game.ConnectfourSerializer;
import io.riddles.connectfour.game.board.Board;
import io.riddles.connectfour.game.player.ConnectfourPlayer;
import io.riddles.connectfour.game.processor.ConnectfourProcessor;
import io.riddles.connectfour.game.state.ConnectfourState;
import io.riddles.javainterface.engine.AbstractEngine;
import io.riddles.javainterface.exception.TerminalException;

/**
 * ConnectfourEngine:
 * - Creates a Processor, the Players and an initial State
 * - Parses the setup input
 * - Sends settings to the players
 * - Runs a game
 * - Returns the played game at the end of the game
 *
 * Created by joost on 6/27/16.
 */
public class ConnectfourEngine extends AbstractEngine<ConnectfourProcessor, ConnectfourPlayer, ConnectfourState> {

    public ConnectfourEngine() throws TerminalException {

        super(new String[0]);
        setDefaults();
    }

    public ConnectfourEngine(String args[]) throws TerminalException {
        super(args);
        setDefaults();
    }

    public ConnectfourEngine(String wrapperFile, String[] botFiles) throws TerminalException {
        super(wrapperFile, botFiles);
        setDefaults();
    }


    private void setDefaults() {
        configuration.put("maxRounds", 100);
        configuration.put("fieldWidth", 7);
        configuration.put("fieldHeight", 6);
    }

    /* createPlayer creates and initialises a Player for the game.
     * returns: a Player
     */
    @Override
    protected ConnectfourPlayer createPlayer(int id) {
        ConnectfourPlayer p = new ConnectfourPlayer(id);
        return p;
    }

    /* createProcessor creates and initialises a Processor for the game.
     * returns: a Processor
     */
    @Override
    protected ConnectfourProcessor createProcessor() {
        /* We're going for one-based indexes for playerId's so we can use 0's for empty fields.
         * This makes sure existing bots will still work with Connectfour, when used with the new wrapper.
         */
        for (ConnectfourPlayer player : this.players) {
            player.setId(player.getId() + 1);
        }
        return new ConnectfourProcessor(this.players);

    }

    /* sendGameSettings sends the game settings to a Player
     * returns:
     */
    @Override
    protected void sendGameSettings(ConnectfourPlayer player) {
    }

    /* getPlayedGame creates a serializer and serialises the game
     * returns: String with the serialised game.
     */
    @Override
    protected String getPlayedGame(ConnectfourState state) {
        ConnectfourSerializer serializer = new ConnectfourSerializer();
        return serializer.traverseToString(this.processor, state);
    }

    /* getInitialState creates an initial state to start the game with.
     * returns: ConnectfourState
     */
    @Override
    protected ConnectfourState getInitialState() {
        ConnectfourState s = new ConnectfourState();
        s.setBoard(new Board(configuration.getInt("fieldWidth"), configuration.getInt("fieldHeight")));
        return s;
    }
}
