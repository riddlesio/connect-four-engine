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

package io.riddles.fourinarownew.game.move;

import io.riddles.fourinarownew.game.data.Coordinate;
import io.riddles.fourinarownew.game.data.MoveType;
import io.riddles.fourinarownew.game.player.FourInARowPlayer;
import io.riddles.javainterface.exception.InvalidInputException;
import io.riddles.javainterface.serialize.Deserializer;

/**
 * ${PACKAGE_NAME}
 *
 * This file is a part of FourInARow
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */

public class FourInARowMoveDeserializer implements Deserializer<FourInARowMove> {

    private FourInARowPlayer player;

    public FourInARowMoveDeserializer(FourInARowPlayer player) {
        this.player = player;
    }

    @Override
    public FourInARowMove traverse(String string) {
        try {
            return visitMove(string);
        } catch (InvalidInputException ex) {
            return new FourInARowMove(this.player, ex);
        } catch (Exception ex) {
            return new FourInARowMove(
                this.player, new InvalidInputException("Failed to parse move"));
        }
    }

    private FourInARowMove visitMove(String input) throws InvalidInputException {
        String[] split = input.split(" ");
        if (split.length != 3) {
            throw new InvalidInputException("Syntax error.");
        }
        MoveType type = visitAssessment(split[0]);
        int column = Integer.parseInt(split[1]);
        int row = Integer.parseInt(split[2]);
        Coordinate c = new Coordinate(column, row);
        return new FourInARowMove(this.player, c);
    }

    public MoveType visitAssessment(String input) throws InvalidInputException {
        switch (input) {
            case "place_move":
                return MoveType.PLACEMOVE;
            default:
                throw new InvalidInputException("Move isn't valid");
        }
    }
}
