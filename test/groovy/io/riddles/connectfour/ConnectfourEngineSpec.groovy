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
    def "test a standard game with winner 0"() {

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input.txt"
        botInputs[1] = "./test/resources/bot2_input.txt"

        PlayerProvider<ConnectfourPlayer> playerProvider = new PlayerProvider<>();
        ConnectfourPlayer player1 = new ConnectfourPlayer(0);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        ConnectfourPlayer player2 = new ConnectfourPlayer(1);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState initialState = engine.willRun()
        AbstractState finalState = engine.run(initialState);
        engine.didRun(initialState, finalState);
        ConnectfourProcessor processor = engine.getProcessor();

        expect:
        finalState instanceof ConnectfourState;
        finalState.getBoard().toString() == ".,0,.,.,.,.,.,.,1,.,.,.,.,.,.,0,.,.,.,.,.,.,1,.,.,.,.,.,1,1,0,.,.,.,.,0,1,0,0,.,.,.";
        processor.getWinnerId(finalState) == 0;

    }

    //@Ignore
    def "test a standard game with winner 3"() {

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input.txt"
        botInputs[1] = "./test/resources/bot2_input.txt"

        PlayerProvider<ConnectfourPlayer> playerProvider = new PlayerProvider<>();
        ConnectfourPlayer player1 = new ConnectfourPlayer(3);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        ConnectfourPlayer player2 = new ConnectfourPlayer(6);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState initialState = engine.willRun()
        AbstractState finalState = engine.run(initialState);
        engine.didRun(initialState, finalState);
        ConnectfourProcessor processor = engine.getProcessor();

        expect:
        finalState instanceof ConnectfourState;
        finalState.getBoard().toString() == ".,3,.,.,.,.,.,.,6,.,.,.,.,.,.,3,.,.,.,.,.,.,6,.,.,.,.,.,6,6,3,.,.,.,.,3,6,3,3,.,.,.";
        processor.getWinnerId(finalState) == 3;

    }

    //@Ignore
    def "cdia p1 win"() {

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_cdia_p1win.txt"
        botInputs[1] = "./test/resources/bot2_input_cdia_p1win.txt"

        PlayerProvider<ConnectfourPlayer> playerProvider = new PlayerProvider<>();
        ConnectfourPlayer player1 = new ConnectfourPlayer(3);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        ConnectfourPlayer player2 = new ConnectfourPlayer(6);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState initialState = engine.willRun()
        AbstractState finalState = engine.run(initialState);
        engine.didRun(initialState, finalState);
        ConnectfourProcessor processor = engine.getProcessor();

        expect:
        finalState instanceof ConnectfourState;
        finalState.getBoard().toString() == ".,.,.,.,.,.,.,.,.,.,.,.,.,.,3,.,.,.,.,.,.,3,3,.,.,.,.,.,6,6,3,.,.,.,.,3,6,6,3,6,.,.";
        processor.getWinnerId(finalState) == 3;
    }

    //@Ignore
    def "cdia p2 win"() {

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_cdia_p2win.txt"
        botInputs[1] = "./test/resources/bot2_input_cdia_p2win.txt"

        PlayerProvider<ConnectfourPlayer> playerProvider = new PlayerProvider<>();
        ConnectfourPlayer player1 = new ConnectfourPlayer(3);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        ConnectfourPlayer player2 = new ConnectfourPlayer(6);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState initialState = engine.willRun()
        AbstractState finalState = engine.run(initialState);
        engine.didRun(initialState, finalState);
        ConnectfourProcessor processor = engine.getProcessor();

        expect:
        finalState instanceof ConnectfourState;
        finalState.getBoard().toString() == ".,.,.,.,.,.,.,.,.,.,.,.,.,.,6,.,.,.,.,.,.,6,6,.,.,.,.,.,3,3,6,.,3,.,.,6,3,3,6,3,.,.";
        processor.getWinnerId(finalState) == 6;
    }

    //@Ignore
    def "hor p1 win"() {

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_hor_p1win.txt"
        botInputs[1] = "./test/resources/bot2_input_hor_p1win.txt"

        PlayerProvider<ConnectfourPlayer> playerProvider = new PlayerProvider<>();
        ConnectfourPlayer player1 = new ConnectfourPlayer(1);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        ConnectfourPlayer player2 = new ConnectfourPlayer(2);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState initialState = engine.willRun()
        AbstractState finalState = engine.run(initialState);
        engine.didRun(initialState, finalState);
        ConnectfourProcessor processor = engine.getProcessor();

        expect:
        finalState instanceof ConnectfourState;
        finalState.getBoard().toString() == ".,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,2,.,.,1,1,1,1,2,2";
        processor.getWinnerId(finalState) == 1;
    }

    //@Ignore
    def "hor p2 win"() {

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_hor_p2win.txt"
        botInputs[1] = "./test/resources/bot2_input_hor_p2win.txt"

        PlayerProvider<ConnectfourPlayer> playerProvider = new PlayerProvider<>();
        ConnectfourPlayer player1 = new ConnectfourPlayer(0);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        ConnectfourPlayer player2 = new ConnectfourPlayer(1);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState initialState = engine.willRun()
        AbstractState finalState = engine.run(initialState);
        engine.didRun(initialState, finalState);
        ConnectfourProcessor processor = engine.getProcessor();

        expect:
        finalState instanceof ConnectfourState;
        finalState.getBoard().toString() == ".,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,0,.,.,.,0,.,.,1,1,1,1,0,0";
        processor.getWinnerId(finalState) == 1;
    }

    //@Ignore
    def "draw"() {
        println("draw")

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_boardfull.txt"
        botInputs[1] = "./test/resources/bot2_input_boardfull.txt"

        PlayerProvider<ConnectfourPlayer> playerProvider = new PlayerProvider<>();
        ConnectfourPlayer player1 = new ConnectfourPlayer(0);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        ConnectfourPlayer player2 = new ConnectfourPlayer(1);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState initialState = engine.willRun()
        AbstractState finalState = engine.run(initialState);
        engine.didRun(initialState, finalState);
        ConnectfourProcessor processor = engine.getProcessor();

        expect:
        finalState instanceof ConnectfourState;
        finalState.getBoard().toString() == "0,0,1,1,0,0,1,1,1,0,0,1,1,0,0,0,1,1,0,0,1,1,1,0,0,1,1,0,0,0,1,1,0,0,1,1,1,0,0,1,1,0";
        processor.getWinnerId(finalState) == null;
    }

    //@Ignore
    def "ver p1 win"() {

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_ver_p1win.txt"
        botInputs[1] = "./test/resources/bot2_input_ver_p1win.txt"

        PlayerProvider<ConnectfourPlayer> playerProvider = new PlayerProvider<>();
        ConnectfourPlayer player1 = new ConnectfourPlayer(1);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        ConnectfourPlayer player2 = new ConnectfourPlayer(2);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState initialState = engine.willRun()
        AbstractState finalState = engine.run(initialState);
        engine.didRun(initialState, finalState);
        ConnectfourProcessor processor = engine.getProcessor();

        expect:
        finalState instanceof ConnectfourState;
        finalState.getBoard().toString() == ".,2,.,.,.,.,.,.,1,1,.,.,.,.,.,1,1,.,.,.,.,.,2,1,1,.,2,.,2,1,1,1,.,2,2,2,1,2,1,.,2,2";
        processor.getWinnerId(finalState) == 1;
    }

    //@Ignore
    def "ver p2 win ids 0 and 1"() {

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_ver_p2win.txt"
        botInputs[1] = "./test/resources/bot2_input_ver_p2win.txt"

        PlayerProvider<ConnectfourPlayer> playerProvider = new PlayerProvider<>();
        ConnectfourPlayer player1 = new ConnectfourPlayer(0);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        ConnectfourPlayer player2 = new ConnectfourPlayer(1);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState initialState = engine.willRun()
        AbstractState finalState = engine.run(initialState);
        engine.didRun(initialState, finalState);
        ConnectfourProcessor processor = engine.getProcessor();

        expect:
        finalState instanceof ConnectfourState;
        finalState.getBoard().toString() == ".,1,.,.,.,.,.,.,0,.,.,.,.,.,.,0,.,.,.,1,0,.,1,0,0,.,1,1,1,0,0,0,.,1,0,1,0,1,0,.,1,1";
        processor.getWinnerId(finalState) == 1;
    }

    //@Ignore
    def "garbage and out of bounds player 1 wins"() {

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot_input_garbage.txt"
        botInputs[1] = "./test/resources/bot2_input_ver_p2win.txt"

        PlayerProvider<ConnectfourPlayer> playerProvider = new PlayerProvider<>();
        ConnectfourPlayer player1 = new ConnectfourPlayer(0);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        ConnectfourPlayer player2 = new ConnectfourPlayer(1);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState initialState = engine.willRun()
        AbstractState finalState = engine.run(initialState);
        engine.didRun(initialState, finalState);
        ConnectfourProcessor processor = engine.getProcessor();

        expect:
        finalState instanceof ConnectfourState;
        finalState.getBoard().toString() == ".,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,1,0,.,.,.";
        processor.getWinnerId(finalState) == 1;
    }

    //@Ignore
    def "garbage and out of bounds player 0 wins"() {

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot2_input_ver_p2win.txt"
        botInputs[1] = "./test/resources/bot_input_garbage.txt"

        PlayerProvider<ConnectfourPlayer> playerProvider = new PlayerProvider<>();
        ConnectfourPlayer player1 = new ConnectfourPlayer(0);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        ConnectfourPlayer player2 = new ConnectfourPlayer(1);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState initialState = engine.willRun()
        AbstractState finalState = engine.run(initialState);
        engine.didRun(initialState, finalState);
        ConnectfourProcessor processor = engine.getProcessor();

        expect:
        finalState instanceof ConnectfourState;
        finalState.getBoard().toString() == ".,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,0,1,.,0,.";
        processor.getWinnerId(finalState) == 0;
    }
}