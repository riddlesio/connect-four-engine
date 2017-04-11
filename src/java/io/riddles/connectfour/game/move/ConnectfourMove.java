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

package io.riddles.connectfour.game.move;

import io.riddles.javainterface.game.move.AbstractMove;

/**
 * io.riddles.connectfour.game.move.ConnectfourMove - Created on 6/27/16
 *
 * This file is a part of Connectfour
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Joost - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */


public class ConnectfourMove extends AbstractMove {

    private MoveType type;
    private int column;

    public ConnectfourMove(MoveType type, int column) {
        super();
        this.type = type;
        this.column = column;
    }

    public ConnectfourMove(Exception exception) {
        super(exception);
    }

    public MoveType getMoveType() {
        return this.type;
    }

    public int getColumn() { return this.column; }
}
