package com.luisali;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class SudokuFrame extends JFrame {

    public SudokuFrame() {
        this.add(new SudokuPanel());
        this.setTitle("Sudoku Solver");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
