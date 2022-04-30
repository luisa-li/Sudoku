package com.luisali;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SudokuPanel extends JPanel {

    private static final int DIMENSION = 9;
    private static final int UNIT = 30;
    private final JLabel[][] labelGrid = new JLabel[DIMENSION][DIMENSION];

    private final Sudoku sudoku = new Sudoku("530070000600195000098000060800060003400803001700020006060000280000419005000080079");


    public SudokuPanel() {

        // setting up the left side of the board that contaisn the actual problem
        setUpBoard();

        // solve it
        while (!solved()) {
            solveSudoku(this.sudoku.getDecodedSudoku());
            int solvedCells = 0;
            // test that counts how many solved cell we have: are we making progress?
            for (int i = 0; i < DIMENSION; i++) {
                for (int y = 0; y < DIMENSION; y++) {
                    if (this.sudoku.getDecodedSudoku()[i][y] <= 9 && this.sudoku.getDecodedSudoku()[i][y]!= 0) {
                        solvedCells++;
                    }
                }
            }
            System.out.println(solvedCells);
        }

    }

    private void setUpBoard() {

        GridLayout gridLayout = new GridLayout(9, 9);
        // using the grid gap to draw the lighter, individual cell lines
        gridLayout.setVgap(1);
        gridLayout.setHgap(1);
        this.setLayout(gridLayout);
        this.setPreferredSize(new Dimension(278, 278));

        for (int x = 0; x < DIMENSION; x++) {
            for (int y = 0; y < DIMENSION; y++) {

                // setting the graphical elements

                labelGrid[x][y] = new JLabel();
                labelGrid[x][y].setPreferredSize(new Dimension(UNIT, UNIT));
                labelGrid[x][y].setBorder(null);
                labelGrid[x][y].setFont(new Font("Arial", Font.PLAIN, 20));
                labelGrid[x][y].setHorizontalAlignment(SwingConstants.CENTER);
                labelGrid[x][y].setVerticalAlignment(SwingConstants.CENTER);

                // setting the numbers in the Sudoku board
                // if the number if 0, display it as being empty

                if (sudoku.getDecodedSudoku()[x][y] == 0) {
                    labelGrid[x][y].setText("");
                } else {
                    labelGrid[x][y].setText(String.valueOf(sudoku.getDecodedSudoku()[x][y]));
                    labelGrid[x][y].setForeground(new Color(163, 42, 42));
                }

                this.add(labelGrid[x][y]);

            }
        }

    }

    public void paintComponent(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;

        // thinner lines
        g2D.setStroke(new BasicStroke(1));
        g2D.setPaint(Color.lightGray);

        for (int i = 31; i < 278; i = i + 31) {
            g2D.drawLine(i, 0, i, 278);
            g2D.drawLine(0, i, 278, i);
        }

        // thicker lines
        g2D.setPaint(Color.GRAY);
        g2D.setStroke(new BasicStroke(2));
        g2D.drawLine(92, 0, 92, 278);
        g2D.drawLine(185, 0, 185, 278);
        g2D.drawLine(0, 92, 278, 92);
        g2D.drawLine(0, 184, 278, 184);

    }

    private void solveSudoku(int[][] decodedSudoku) {

        // progress keeps track of whether we have made progress in this new iteration of solveSudoku
        boolean progress = false;

        // step 0
        // passing in sudoku as a variable since this method is most likely going to be recursive.

        // step 1
        // loop through every element of the array, and for each cell find the possible numbers that could go there
        // to avoid making a 3D array, each cell that has multiple possibilities will be numbers of 2 or more digits. for example, if a cell can be either 9 or 1, the cell would contain the integer 91.
        // that way, if a cell is detected such that it has only one possibility, then that cell will automatically become that number.

        for (int i = 0; i < DIMENSION; i++) {
            for (int y = 0; y < DIMENSION; y++) {
                // returns all the possible values of a particular cell if it is empty (0), this applies to the first pass of the sudoku board ONLY
                // by the second call of solveSudoku(), all cells will have numbers in them.
                if (decodedSudoku[i][y] == 0) {
                    // find all the possible ints in that spot and write it in that cell of decodedSudoku
                    decodedSudoku[i][y] = returnPossibilities(i, y, decodedSudoku);
                } else if (this.sudoku.getDecodedSudoku()[i][y] > 9) {
                    // if the current cell has more than two digits, then find the possibilities again.
                    decodedSudoku[i][y] = returnPossibilities(i, y, decodedSudoku);
                }
                if (decodedSudoku[i][y] < 10 && decodedSudoku[i][y] != 0) {
                    // then we have made progress in finding new confirmed digits
                    progress = true;
                }
            }
        }

        if (!progress) {

            // if no progress was made with the iterative algorithm, then switch to a recursive algorithm

            // TODO Figure out how to make this happen

        }

        printSudoku();
        refreshGraphics();

    }

    private int returnPossibilities(int i, int y, int[][] decodedSudoku) {

        // m is the initial array of posibilities, as we check neighboring cells, elements will begin to be deleted from m
        ArrayList<Integer> possibilities = new ArrayList<>();
        for (int m = 1; m < 10; m++) {
            possibilities.add(m);
        }

        // check row and column to remove all the numbers that already appear in that row/col
        for (int a = 0; a < DIMENSION; a++) {
            // can't use .remove() because that would remove the element at that index instead of the actual element
            possibilities.remove(Integer.valueOf(decodedSudoku[i][a]));
            possibilities.remove(Integer.valueOf(decodedSudoku[a][y]));
        }

        // checking the 3x3 boxes and finding the x, y index of the 3x3 box the current coordinate belongs to.
        
        int[] u;
        if (i==0 || i==1 || i==2) {
            u = new int[] {0, 1, 2};
        } else if (i == 3 || i == 4 || i == 5) {
            u = new int[] {3, 4, 5};
        } else {
            u = new int[] {6, 7, 8};
        }

        int[] v;
        if (y==0 || y==1 || y==2) {
            v = new int[] {0, 1, 2};
        } else if (y == 3 || y == 4 || y == 5) {
            v = new int[] {3, 4, 5};
        } else {
            v = new int[] {6, 7, 8};
        }

        // iterates through the 9 cells of the 3x3 and removes any numbers it find from the possibilities

        for (int a : u) {
            for (int b : v) {
                possibilities.remove(Integer.valueOf(decodedSudoku[a][b]));
            }
        }

        // now possibilities should contain only the actual possible numbers, concatenate those numbers into a string, then convert that into an int, and return it.

        String returnString = "";
        for (int possibility : possibilities) {
            returnString = returnString.concat("" + possibility);
        }
        return Integer.parseInt(returnString);

    }

    public void refreshGraphics() {

        // refresh the graphics and show the numbers currently known for certain, i.e, if this.sudoku.getDecodedSudoku[i][y] has a length of 1.

        for (int i = 0; i < DIMENSION; i++) {
            for (int y = 0; y < DIMENSION; y++) {
                if ((this.sudoku.getDecodedSudoku()[i][y]) < 10) {
                    labelGrid[i][y].setText(String.valueOf(this.sudoku.getDecodedSudoku()[i][y]));
                }
            }
        }
    }

    public void printSudoku() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int y = 0; y < DIMENSION; y++) {
                System.out.print(this.sudoku.getDecodedSudoku()[i][y] + " ");
            }
            System.out.println();
        }
    }

    private boolean solved() {
        // a sudoku is solved if all spaces are not empty
        for (int x = 0; x < DIMENSION; x++) {
            for (int y = 0; y < DIMENSION; y++) {
                if (this.sudoku.getDecodedSudoku()[x][y] > 9 || this.sudoku.getDecodedSudoku()[x][y] == 0) {
                    // then there are still cells with double or more digits
                    return false;
                }
            }
        }
        return true;
    }

}

// TODO When you finish making an algorithm that works, look up what the actual algorithm for this is, and what the best solution algorithm was. It's probably not what I did.