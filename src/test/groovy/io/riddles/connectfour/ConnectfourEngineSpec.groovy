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

package io.riddles.connectfour

import io.riddles.connectfour.engine.ConnectfourEngine
import io.riddles.connectfour.game.player.ConnectfourPlayer
import io.riddles.connectfour.game.processor.ConnectfourProcessor
import io.riddles.connectfour.game.state.ConnectfourState
import io.riddles.javainterface.game.player.PlayerProvider
import io.riddles.javainterface.game.state.AbstractState
import io.riddles.javainterface.io.FileIOHandler
import spock.lang.Ignore
import spock.lang.Specification

/**
 *
 * [description]
 *
 * @author joost
 */

class ConnectfourEngineSpec extends Specification {

    public static class TestEngine extends ConnectfourEngine {

        TestEngine(PlayerProvider<ConnectfourEngine> playerProvider, String wrapperInput) {
            super(playerProvider, null);
            this.ioHandler = new FileIOHandler(wrapperInput);
        }
    }

    //@Ignore
    def "test a standard game"() {
        println("test a standard game")

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./src/test/resources/wrapper_input.txt"
        botInputs[0] = "./src/test/resources/bot1_input.txt"
        botInputs[1] = "./src/test/resources/bot2_input.txt"

        PlayerProvider<ConnectfourPlayer> playerProvider = new PlayerProvider<>();
        ConnectfourPlayer player1 = new ConnectfourPlayer(0);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        ConnectfourPlayer player2 = new ConnectfourPlayer(1);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState state = engine.willRun()
        state = engine.run(state);
        /* Fast forward to final state */
        while (state.hasNextState()) state = state.getNextState();

        state.getBoard().dumpBoard();
        ConnectfourProcessor processor = engine.getProcessor();

        expect:
        state instanceof ConnectfourState;
        state.getBoard().toString() == ".,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,1,1,1,1,1,1,1,1,.,.,.,.,.,.,.,.,.,.,.,1,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,1,.,0,0,0,0,0,0,0,.,.,.,.,.,.,.,.,.,.,1,.,0,0,0,0,0,0,0,.,.,.,.,.,.,.,.,.,.,1,.,0,0,0,0,0,0,0,.,.,.,.,.,.,.,.,.,0,1,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,1,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,1,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,1,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.";
        processor.getWinnerId(state) == null;

    }
}