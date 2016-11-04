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
import io.riddles.javainterface.exception.TerminalException
import io.riddles.javainterface.io.IOHandler
import io.riddles.connectfour.game.state.ConnectfourState
import spock.lang.Ignore
import spock.lang.Specification

/**
 *
 * [description]
 *
 * @author joost
 */

class ConnectfourEngineSpec extends Specification {

    class TestEngine extends ConnectfourEngine {
        protected ConnectfourState finalState = null

        TestEngine(IOHandler ioHandler) {
            super();
            this.ioHandler = ioHandler;
        }

        TestEngine(String wrapperFile, String[] botFiles) {
            super(wrapperFile, botFiles)

        }

        IOHandler getIOHandler() {
            return this.ioHandler;
        }

        @Override
        public void run() throws TerminalException, InterruptedException {
            LOGGER.info("Starting...");

            setup();

            if (this.processor == null) {
                throw new TerminalException("Processor has not been set");
            }

            LOGGER.info("Running pre-game phase...");

            this.processor.preGamePhase();


            LOGGER.info("Starting game loop...");

            ConnectfourState initialState = getInitialState();
            this.finalState = this.gameLoop.run(initialState, this.processor);

            String playedGame = getPlayedGame(initialState);
            this.platformHandler.finish(playedGame);
        }
    }

    @Ignore
    def "test if ConnectfourEngine is created"() {
        println("test if ConnectfourEngine is created")

        setup:
        String[] botInputs = new String[2]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input.txt"
        botInputs[1] = "./test/resources/bot2_input.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
    }

    @Ignore
    def "Test horizontal player 1 win"() {

        setup:
        String[] botInputs = new String[2]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_hor_p1win.txt"
        botInputs[1] = "./test/resources/bot2_input_hor_p1win.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
        engine.getProcessor().getWinner().getId() == 1;
    }

    @Ignore
    def "Test horizontal player 2 win"() {

        setup:
        String[] botInputs = new String[2]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_hor_p2win.txt"
        botInputs[1] = "./test/resources/bot2_input_hor_p2win.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
        engine.getProcessor().getWinner().getId() == 2;
    }

    @Ignore
    def "Test vertical player 1 win"() {

        setup:
        String[] botInputs = new String[2]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_ver_p1win.txt"
        botInputs[1] = "./test/resources/bot2_input_ver_p1win.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
        engine.getProcessor().getWinner().getId() == 1;
    }

    @Ignore
    def "Test vertical player 2 win"() {

        setup:
        String[] botInputs = new String[2]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_ver_p2win.txt"
        botInputs[1] = "./test/resources/bot2_input_ver_p2win.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
        engine.getProcessor().getWinner().getId() == 2;
    }

    @Ignore
    def "Test diagonal player 1 win"() {

        setup:
        String[] botInputs = new String[2]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_dia_p1win.txt"
        botInputs[1] = "./test/resources/bot2_input_dia_p1win.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
        engine.getProcessor().getWinner().getId() == 1;
    }

    @Ignore
    def "Test diagonal player 2 win"() {

        setup:
        String[] botInputs = new String[2]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_dia_p2win.txt"
        botInputs[1] = "./test/resources/bot2_input_dia_p2win.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
        engine.getProcessor().getWinner().getId() == 2;
    }

    @Ignore
    def "Test counter diagonal player 1 win"() {

        setup:
        String[] botInputs = new String[2]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_cdia_p1win.txt"
        botInputs[1] = "./test/resources/bot2_input_cdia_p1win.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
        engine.getProcessor().getWinner().getId() == 1;
    }

    @Ignore
    def "Test counter diagonal player 2 win"() {

        setup:
        String[] botInputs = new String[2]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_cdia_p2win.txt"
        botInputs[1] = "./test/resources/bot2_input_cdia_p2win.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
        engine.getProcessor().getWinner().getId() == 2;
    }

    @Ignore
    def "Test column overflow"() {

        setup:
        String[] botInputs = new String[2]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_overflow.txt"
        botInputs[1] = "./test/resources/bot2_input_overflow.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
    }

    @Ignore
    def "Test column out of bounds"() {

        setup:
        String[] botInputs = new String[2]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot_input_outofbounds.txt"
        botInputs[1] = "./test/resources/bot2_input_overflow.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
    }

    //@Ignore
    def "Test garbage input"() {

        setup:
        String[] botInputs = new String[3]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot_input_garbage.txt"
        botInputs[1] = "./test/resources/bot2_input_overflow.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
    }

    //@Ignore
    def "Test board full"() {

        setup:
        String[] botInputs = new String[3]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input_boardfull.txt"
        botInputs[1] = "./test/resources/bot2_input_boardfull.txt"

        def engine = new TestEngine(wrapperInput, botInputs)
        engine.run()

        expect:
        engine.finalState instanceof ConnectfourState;
        engine.getProcessor().getWinner() == null;
    }
}