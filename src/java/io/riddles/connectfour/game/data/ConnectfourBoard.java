package io.riddles.connectfour.game.data;

/**
 * ${PACKAGE_NAME}
 *
 * This file is a part of Connectfour
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */

public class ConnectfourBoard {
    protected int[][] board;

    protected int width = 7;
    protected int height = 6;
    private Coordinate lastMove = null;



    public ConnectfourBoard(int w, int h) {
        this.width = w;
        this.height = h;
        this.board = new int[w][h];
        clearBoard();
    }

    public void clearBoard() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = 0;
            }
        }
    }


    @Override
    /**
     * Creates comma separated String with player ids for the microboards.
     * @param :
     * @return : String with player names for every cell, or 'empty' when cell is empty.
     */
    public String toString() {
        String r = "";
        int counter = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (counter > 0) {
                    r += ",";
                }
                r += board[x][y];
                counter++;
            }
        }
        return r;
    }


    /**
     * Checks whether the field is full
     * @param :
     * @return : Returns true when field is full, otherwise returns false.
     */
    public boolean boardIsFull() {
        for (int y = 0; y < this.height; y++)
            for (int x = 0; x < this.width; x++)
                if (board[x][y] == 0)
                    return false; // At least one cell is not filled
        // All cells are filled
        return true;
    }


    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }


    public int getFieldAt(Coordinate c) {
        return board[c.getX()][c.getY()];
    }
    public void setFieldAt(Coordinate c, int v) {

        board[c.getX()][c.getY()] = v;
        System.out.println("setFieldAt " + c);
    }


    public void dump() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                System.out.print(board[x][y]);
            }
            System.out.println();
        }
    }

}

