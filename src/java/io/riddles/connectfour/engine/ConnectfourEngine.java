package io.riddles.connectfour.engine;

import io.riddles.connectfour.game.ConnectfourSerializer;
import io.riddles.connectfour.game.data.ConnectfourBoard;
import io.riddles.connectfour.game.player.ConnectfourPlayer;
import io.riddles.connectfour.game.processor.ConnectfourProcessor;
import io.riddles.connectfour.game.state.ConnectfourState;
import io.riddles.javainterface.engine.AbstractEngine;

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

    public ConnectfourEngine() {

        super();
    }

    public ConnectfourEngine(String wrapperFile, String[] botFiles) {

        super(wrapperFile, botFiles);
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
        s.setBoard(new ConnectfourBoard(9,9));
        return s;
    }


    /* parseSetupInput parses setup input received from the server.
     * returns:
     */
    @Override
    protected void parseSetupInput(String input) {
        String[] split = input.split(" ");
        String command = split[0];
        if (command.equals("bot_ids")) {
            String[] ids = split[1].split(",");
            for (int i = 0; i < ids.length; i++) {
                ConnectfourPlayer player = createPlayer(Integer.parseInt(ids[i]));

                if (this.botInputFiles != null)
                    player.setInputFile(this.botInputFiles[i]);
                this.players.add(player);
            }
        } else if (command.equals("bot_emails")) {
            String[] emails = split[1].split(",");
            for (int i = 0; i < emails.length; i++) {
                this.players.get(i).setEmail(emails[i]);
            }
        }
    }
}
