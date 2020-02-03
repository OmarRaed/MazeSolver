package com.maze.algorithm;

import com.maze.model.AstarTreeNode;
import com.maze.model.Maze;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * An algorithm class that calculates the solution path based on A* algorithm
 */
public class ASTAR {

    private static final int BOTTOM_MOVE = 0;
    private static final int RIGHT_MOVE = 1;
    private static final int TOP_MOVE = 2;
    private static final int LEFT_MOVE = 3;

    //an empty Maze list that will holds all the leaf mazes
    private List<AstarTreeNode> leafMazes = new ArrayList<>();

    //an empty String list that will holds all the visited mazes code
    private List<String> visitedMazes = new ArrayList<>();

    //a flag that indicates if the algorithm found a solution yet or not
    private boolean isSolved = false;

    //A Tree that will hold the final node
    private AstarTreeNode finalTree;

    /**
     * A method called to explore (Visit) a tree node
     */
    public void explore(AstarTreeNode tree) {

        //print this world (for debugging)
        tree.getMaze().printWorld();

        //check is this world is complete
        if (tree.getMaze().isComplete()) {

            //set the isSolved flag to true
            isSolved = true;

            //set the final tree node to this one
            finalTree = tree;

            return;

        }

        //check if the bottom move is valid and not visited
        Maze bottomMaze = new Maze(tree.getMaze().copyWorld(), tree.getMaze().getCost() + 1);
        if (bottomMaze.move(BOTTOM_MOVE) && !bottomMaze.isVisited(visitedMazes)) {

            //create a new tree node with this maze
            AstarTreeNode bottomMoveTree = new AstarTreeNode(bottomMaze, tree);

            //add this new tree node to the leaf list
            leafMazes.add(bottomMoveTree);

            //visit this world
            visitedMazes.add(bottomMaze.getCode());
        }

        //check if the right move is valid and not visited
        Maze rightMaze = new Maze(tree.getMaze().copyWorld(), tree.getMaze().getCost() + 1);
        if (rightMaze.move(RIGHT_MOVE) && !rightMaze.isVisited(visitedMazes)) {

            //create a new tree node with this maze
            AstarTreeNode rightMoveTree = new AstarTreeNode(rightMaze, tree);

            //add this new tree node to the leaf list
            leafMazes.add(rightMoveTree);

            //visit this world
            visitedMazes.add(rightMaze.getCode());
        }

        //check if the left move is valid and not visited
        Maze leftMaze = new Maze(tree.getMaze().copyWorld(), tree.getMaze().getCost() + 1);
        if (leftMaze.move(LEFT_MOVE)) {

            //create a new tree node with this maze
            AstarTreeNode leftMoveTree = new AstarTreeNode(leftMaze, tree);

            //add this new tree node to the leaf list
            leafMazes.add(leftMoveTree);

            //visit this world
            visitedMazes.add(leftMaze.getCode());
        }

        //check if the top move is valid and not visited
        Maze topMaze = new Maze(tree.getMaze().copyWorld(), tree.getMaze().getCost() + 1);
        if (topMaze.move(TOP_MOVE)) {

            //create a new tree node with this maze
            AstarTreeNode topMoveTree = new AstarTreeNode(topMaze, tree);

            //add this new tree node to the leaf list
            leafMazes.add(topMoveTree);

            //visit this world
            visitedMazes.add(topMaze.getCode());

        }

    }

    /**
     * Method called to find a solution for a level using A* algorithm
     */
    public int runAStar(String level) {

        //create a new Maze
        Maze rootMaze = new Maze();

        //read the level1 maze
        rootMaze.readLevel(level);

        //create a new tree and add it to the leaf list
        AstarTreeNode root = new AstarTreeNode(rootMaze);
        leafMazes.add(root);

        //add this maze to the visited list
        visitedMazes.add(rootMaze.getCode()) ;

        //loop till this level is solved
        while (!isSolved) {

            //sort the leaf list by evaluation function (heurstic and cost)
            Collections.sort(leafMazes);

            //get the maze at 0 index (least evaluation function)
            AstarTreeNode tempMaze = leafMazes.get(0);

            //remove this maze
            leafMazes.remove(0);

            //explore this maze
            explore(tempMaze);
        }

        //initialize an int that will hold the solution steps number
        int steps = 0;

        //print the final maze (for debugging)
        finalTree.getMaze().printWorld();

        //create a stack of Maze that will holds the solution path
        Stack<Maze> mazeSolutionPath = new Stack<>();

        //add the final maze to the path stack
        mazeSolutionPath.add(finalTree.getMaze());

        //loop till the tree node has no parent (root node)
        while (finalTree.getParent() != null) {

            //increment the steps number
            steps++;

            //get the parent tree
            finalTree = finalTree.getParent();

            //add this maze to the path stack
            mazeSolutionPath.add(finalTree.getMaze());
        }

        //start the UI visualization
        EventQueue.invokeLater(() -> {

            List<String> levels = new ArrayList<>();

            while (!mazeSolutionPath.isEmpty()) {
                String s = "";
                Maze w = mazeSolutionPath.pop();
                w.printWorld();
                char[][] temp = w.getLvl();
                for (int i = 0; i < temp.length; i++) {
                    for (int j = 0; j < temp[i].length; j++) {
                        s += temp[i][j];
                    }
                    s += '\n';
                }
                levels.add(s);
            }

            com.ui.Maze game = new com.ui.Maze(levels);
            game.setVisible(true);
        });

        return steps;

    }

}
