package io.riddles.fourinarownew.engine;

import io.riddles.fourinarownew.game.FourInARowSerializer;
import io.riddles.fourinarownew.game.data.FourInARowBoard;
import io.riddles.fourinarownew.game.player.FourInARowPlayer;
import io.riddles.fourinarownew.game.processor.FourInARowProcessor;
import io.riddles.fourinarownew.game.state.FourInARowState;
import io.riddles.javainterface.engine.AbstractEngine;

/**
 * FourInARowEngine:
 * - Creates a Processor, the Players and an initial State
 * - Parses the setup input
 * - Sends settings to the players
 * - Runs a game
 * - Returns the played game at the end of the game
 *
 * Created by joost on 6/27/16.
 */
public class FourInARowEngine extends AbstractEngine<FourInARowProcessor, FourInARowPlayer, FourInARowState> {

    public FourInARowEngine() {

        super();
    }

    public FourInARowEngine(String wrapperFile, String[] botFiles) {

        super(wrapperFile, botFiles);
    }

    /* createPlayer creates and initialises a Player for the game.
     * returns: a Player
     */
    @Override
    protected FourInARowPlayer createPlayer(int id) {
        FourInARowPlayer p = new FourInARowPlayer(id);
        return p;
    }

    /* createProcessor creates and initialises a Processor for the game.
     * returns: a Processor
     */
    @Override
    protected FourInARowProcessor createProcessor() {
        return new FourInARowProcessor(this.players);
    }

    /* sendGameSettings sends the game settings to a Player
     * returns:
     */
    @Override
    protected void sendGameSettings(FourInARowPlayer player) {
    }

    /* getPlayedGame creates a serializer and serialises the game
     * returns: String with the serialised game.
     */
    @Override
    protected String getPlayedGame(FourInARowState state) {
        FourInARowSerializer serializer = new FourInARowSerializer();
        return serializer.traverseToString(this.processor, state);
    }

    /* getInitialState creates an initial state to start the game with.
     * returns: FourInARowState
     */
    @Override
    protected FourInARowState getInitialState() {
        FourInARowState s = new FourInARowState();
        s.setBoard(new FourInARowBoard(9,9));
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
                FourInARowPlayer player = createPlayer(Integer.parseInt(ids[i]));

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
