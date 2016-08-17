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
import io.riddles.javainterface.game.move.AbstractMove;

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


public class FourInARowMove extends AbstractMove<FourInARowPlayer> {

    private MoveType type;
    private Coordinate coordinate;

    public FourInARowMove(FourInARowPlayer player, Coordinate c) {
        super(player);
        coordinate = c;

    }

    public FourInARowMove(FourInARowPlayer player, InvalidInputException exception) {
        super(player, exception);
    }

    public MoveType getMoveType() {
        return this.type;
    }

    public Coordinate getCoordinate() { return this.coordinate; }



}
