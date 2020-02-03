package com.maze.algorithm;

import com.maze.model.Maze;
import com.maze.model.TreeNode;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * An algorithm class that calculates the solution path based on Depth first search algorithm
 */
public class DFS {

    private static final int BOTTOM_MOVE = 0;
    private static final int RIGHT_MOVE = 1;
    private static final int TOP_MOVE = 2;
    private static final int LEFT_MOVE = 3;

    //an empty Maze Stack that will holds mazes to visit
    public Stack<TreeNode> mazeStack = new Stack<>();

    //an empty String list that will holds all the visited mazes code
    public List<String> visitedMazes = new ArrayList<>();

    //a flag that indicates if the algorithm found a solution yet or not
    private boolean isSolved = false;

    //A Tree that will hold the final node
    private TreeNode finalTreeNode;


    /**
     * A method called to explore (Visit) a tree node
     */
    public void explore(TreeNode treeNode) {

        //print this world (for debugging)
        treeNode.getMaze().printWorld();

        //check is this world is complete
        if (treeNode.getMaze().isComplete()) {

            //set the isSolved flag to true
            isSolved = true;

            //set the final tree node to this one
            finalTreeNode = treeNode;

            return;

        }

        //check if the bottom move is valid and not visited
        Maze bottomMaze = new Maze(treeNode.getMaze().copyWorld(), treeNode.getMaze().getCost() + 1);
        if (bottomMaze.move(BOTTOM_MOVE) && !bottomMaze.isVisited(visitedMazes)) {

            //create a new tree node with this maze
            TreeNode bottomMoveTree = new TreeNode(bottomMaze, treeNode);

            //add this new tree node to the leaf list
            mazeStack.push(bottomMoveTree);

            //visit this world
            visitedMazes.add(bottomMaze.getCode());
        }

        //check if the right move is valid and not visited
        Maze rightMaze = new Maze(treeNode.getMaze().copyWorld(), treeNode.getMaze().getCost() + 1);
        if (rightMaze.move(RIGHT_MOVE) && !rightMaze.isVisited(visitedMazes)) {

            //create a new tree node with this maze
            TreeNode rightMoveTree = new TreeNode(rightMaze, treeNode);

            //add this new tree node to the leaf list
            mazeStack.push(rightMoveTree);

            //visit this world
            visitedMazes.add(rightMaze.getCode());
        }

        //check if the left move is valid and not visited
        Maze leftMaze = new Maze(treeNode.getMaze().copyWorld(), treeNode.getMaze().getCost() + 1);
        if (leftMaze.move(LEFT_MOVE)) {

            //create a new tree node with this maze
            TreeNode leftMoveTree = new TreeNode(leftMaze, treeNode);

            //add this new tree node to the leaf list
            mazeStack.push(leftMoveTree);

            //visit this world
            visitedMazes.add(leftMaze.getCode());
        }

        //check if the top move is valid and not visited
        Maze topMaze = new Maze(treeNode.getMaze().copyWorld(), treeNode.getMaze().getCost() + 1);
        if (topMaze.move(TOP_MOVE)) {

            //create a new tree node with this maze
            TreeNode topMoveTree = new TreeNode(topMaze, treeNode);

            //add this new tree node to the leaf list
            mazeStack.push(topMoveTree);

            //visit this world
            visitedMazes.add(topMaze.getCode());

        }

    }

    /**
     * Method called to find a solution for a level using Depth First Search algorithm
     */
    public int runDepthFirstSearch(String level){

        //create a new Maze
        Maze rootMaze = new Maze();

        //read the level1 maze
        rootMaze.readLevel(level);

        //create a new tree and add it to the queue
        TreeNode root = new TreeNode(rootMaze);
        mazeStack.add(root);

        //add this maze to the visited list
        visitedMazes.add(rootMaze.getCode()) ;

        //loop till this level is solved
        while (!isSolved)
            //get maze from the queue and explore
            explore(Objects.requireNonNull(mazeStack.pop()));

        //initialize an int that will hold the solution steps number
        int steps = 0;

        //print the final maze (for debugging)
        finalTreeNode.getMaze().printWorld();

        //create a stack of Maze that will holds the solution path
        Stack<Maze> mazeSolutionPath = new Stack<>();

        //add the final maze to the path stack
        mazeSolutionPath.add(finalTreeNode.getMaze());

        //loop till the tree node has no parent (root node)
        while (finalTreeNode.getParent() != null) {

            //increment the steps number
            steps++;

            //get the parent tree
            finalTreeNode = finalTreeNode.getParent();

            //add this maze to the path stack
            mazeSolutionPath.add(finalTreeNode.getMaze());
        }

        System.out.println("It took " + steps + " step to solve it");

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
