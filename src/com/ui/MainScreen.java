package com.ui;

import com.maze.algorithm.ASTAR;
import com.maze.algorithm.BFS;
import com.maze.algorithm.DFS;
import com.maze.algorithm.Greedy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen {

    private JButton dfsButton;
    private JButton bfsButton;
    private JButton greedyButton;
    private JButton astarButton;
    private JPanel mainPanel;
    private JLabel resultSteps;

    private int steps ;

    private String level1
            = "%%%%%%%%%%\n" +
            "%.  % .  %\n" +
            "% %.% %% %\n" +
            "% %   .%.%\n" +
            "% .%P%   %\n" +
            "%.  .  . %\n" +
            "% %%%% % %\n" +
            "%. .   %.%\n" +
            "%%%%%%%%%%";

    public MainScreen() {
        dfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DFS dfs = new DFS() ;
                steps = dfs.runDepthFirstSearch(level1);
                resultSteps.setText("It took DFS " + steps + " steps");
            }
        });

        greedyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Greedy greedy = new Greedy();
                steps = greedy.runGreedy(level1);
                resultSteps.setText("It took Greedy " + steps + " steps");

            }
        });

        astarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ASTAR astar = new ASTAR();
                steps = astar.runAStar(level1);
                resultSteps.setText("It took A* " + steps + " steps");

            }
        });

        bfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BFS bfs = new BFS();
                steps = bfs.runBreadthFirstSearch(level1);
                resultSteps.setText("It took BFS " + steps + " steps");

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainScreen");
        frame.setContentPane(new MainScreen().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
