package com.luisali;

// Sudoku class, which takes a string that encodes the Sudoku and converts it into a 2D array containing the problem
// In this encoding system: 0 is an empty (to be filled) cell, 1-9 represent the numbers on the Sudoku
// The string is given as a continuous string with no spaces/blanks, left to right, top to bottom
// Example of an encoded Sudoku:
// 000095180009082470008104090096057200800469357705203068000008009080026540000031806
// Decoded Sudoku:

// 0 0 0 0 9 5 2 8 0
// 0 0 9 0 8 2 4 7 0
// 0 0 8 1 0 4 0 9 0
// 0 9 6 0 5 7 2 0 0
// 8 0 0 4 6 9 3 5 7
// 7 0 5 2 0 3 0 6 8
// 0 0 0 0 0 8 0 0 9
// 0 8 0 0 2 6 5 4 0
// 0 0 0 0 3 1 8 0 6


public class Sudoku {

    private static final int SIZE = 9;
    private final String encodedSudoku;
    private int[][] decodedSudoku;

    public Sudoku(String encodedSudoku) {
        this.encodedSudoku = encodedSudoku;
        this.decodedSudoku = this.decodeSudoku();
    }

    private int[][] decodeSudoku() {
        int[][] solved = new int[SIZE][SIZE];
        int index = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int y = 0; y < SIZE; y++) {
                solved[i][y] = Character.getNumericValue(encodedSudoku.charAt(index));
                index++;
            }
        }
        return solved;
    }

    public String getEncodedSudoku() {
        return encodedSudoku;
    }

    public int[][] getDecodedSudoku() {
        return decodedSudoku;
    }

    public void setDecodedSudoku(int[][] decodedSudoku) {
        this.decodedSudoku = decodedSudoku;
    }

}
