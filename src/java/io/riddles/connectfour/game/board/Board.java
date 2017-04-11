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

package io.riddles.connectfour.game.board;

import java.awt.*;

/**
 * io.riddles.bookinggame.game.board.Board - Created on 29-6-16
 *
 * [description]
 *
 * @author Joost - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */

public class Board {
    protected String[][] field;
    protected int width = 20;
    protected int height = 11;
    public static final String EMPTY_FIELD = ".";


    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.field = new String[width][height];
        clear();
    }

    public Board(int width, int height, String fieldLayout) {
        this.width = width;
        this.height = height;
        this.field = new String[width][height];

        String[] split = fieldLayout.split(",");
        int x = 0;
        int y = 0;

        for (String value : split) {
            this.field[x][y] = value;
            if (++x == width) {
                x = 0;
                y++;
            }
        }
    }

    public void clear() {
        for (int y = 0; y < this.height; y++)
            for (int x = 0; x < this.width; x++)
                field[x][y] = EMPTY_FIELD;
    }

    protected Board(int width, int height, String[][] field) {
        this.width = width;
        this.height = height;
        this.field = field;
    }

    public String toString() {
        String s = "";
        int counter = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (counter > 0) s += ",";
                s += this.field[x][y];
                counter ++;
            }
        }
        return s;
    }

    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }


    public String getFieldAt(Point c) {
        return field[c.x][c.y];
    }

    public void setFieldAt(Point c, String s) {
        field[c.x][c.y] = s;
    }

    public void dump() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                System.out.print(field[x][y]);
            }
            System.out.println();
        }
    }


    public int getNrAvailableFields() {
        int availableFields = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if(field[x][y].equals(EMPTY_FIELD)) {
                    availableFields++;
                }
            }
        }
        return availableFields;
    }
}