package com.luisali;

import javax.swing.*;
import java.awt.*;

public class SudokuPanel extends JPanel {

    private static final int DIMENSION = 9;
    private static final int UNIT = 30;
    private final JLabel[][] labelGrid = new JLabel[DIMENSION][DIMENSION];

    private final Sudoku sudoku = new Sudoku("000095180009082470008104090096057200800469357705203068000008009080026540000031806");


    public SudokuPanel() {

        System.out.println("WAAAA");

        // setting up the left side of the board that contaisn the actual problem
        setUpBoard();

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

}