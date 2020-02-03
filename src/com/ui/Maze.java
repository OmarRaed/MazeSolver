package com.ui;

import javax.swing.*;
import java.util.List;

public class Maze extends JFrame {

    private final int OFFSET = 30;

    public Maze(List<String> levels) {

        initUI(levels);
    }

    private void initUI(List<String> levels) {

        Board board = new Board(levels, this);
        add(board);

        setTitle("Maze");

        setSize(board.getBoardWidth() + OFFSET,
                board.getBoardHeight() + 2 * OFFSET);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
