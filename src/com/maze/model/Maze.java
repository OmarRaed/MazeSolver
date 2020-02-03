package com.maze.model;

import java.util.List;

/**
 * A class that represents a Maze World
 */
public class Maze {

    //a private class that will hold the 2d array that represents the game map
    private char[][] lvl;

    //the Player co-ordinates
    private int x = 0;
    private int y = 0;

    //an int that will holds the heurstic value
    private Integer heurstic = 0;

    //an int that will holds the cost value
    private Integer cost = 0;

    public Integer getCost() {
        return cost;
    }

    public char[][] getLvl() {
        return lvl;
    }

    public Maze() {
    }

    public Maze(char[][] lvl, int cost) {
        this.lvl = lvl;
        this.cost = cost;

        //loop for all 2d array to find the player co-ordinates
        for (int i = 0; i < lvl.length; i++) {
            for (int j = 0; j < lvl[i].length; j++) {
                if (lvl[i][j] == 'P') {
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        //calculate heurstic
        getHeurstic();

    }

    /**
     * A method called to read the level from a String
     */
    public void readLevel(String level) {

        //splits the String by the new lines
        String[] parts = level.split("\n");
        lvl = new char[parts.length][parts[0].length()];

        //map the input to 2d array of char
        for (int index = 0; index < parts.length; index++) {

            lvl[index] = parts[index].toCharArray();

        }

        //loop for all 2d array to find the player co-ordinates
        for (int i = 0; i < lvl.length; i++) {
            for (int j = 0; j < lvl[i].length; j++) {
                if (lvl[i][j] == 'P') {
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        //calculate heurstic
        getHeurstic();

    }

    /**
     * A method called to calculate and return heurstic
     */
    public Integer getHeurstic() {

        int temp = 0;

        //loop for all 2d array to find the player co-ordinates
        for (int i = 0; i < lvl.length; i++) {
            for (int j = 0; j < lvl[i].length; j++) {
                if (lvl[i][j] == '.') {
                    temp += Math.abs(x - i);
                    temp += Math.abs(y - j);
                }
            }
        }

        heurstic = temp;

        return heurstic;

    }

    /**
     * A method called to calculate and return evaluation function (heurstic + cost)
     */
    public Integer getEvaluation() {

        int temp = 0;

        //loop for all 2d array to find the player co-ordinates
        for (int i = 0; i < lvl.length; i++) {
            for (int j = 0; j < lvl[i].length; j++) {
                if (lvl[i][j] == '.') {
                    temp += Math.abs(x - i);
                    temp += Math.abs(y - j);
                }
            }
        }

        heurstic = temp;

        return heurstic + cost;

    }

    /**
     * A method called copy the 2d array of char to a new world
     */
    public char[][] copyWorld() {

        //create a new char array with the same length
        char[][] level = new char[lvl.length][lvl[0].length];

        //copy the existing world into the new 2d array
        for (int i = 0; i < lvl.length; i++) {
            for (int j = 0; j < lvl[i].length; j++) {
                level[i][j] = lvl[i][j];
            }
        }

        //return the 2d array
        return level;

    }

    /**
     * A method called to move this level in a specific direction
     * and return true if it can move or false if it can't
     */
    public boolean move(int dir) {

        //bottom -> 0
        //right -> 1
        //top -> 2
        //left -> 3

        //moving values co-ordinates
        int i = 0;
        int j = 0;

        //if bottom move 1 in the y-axis
        if (dir == 0)
            i = 1;
        //if right move 1 in the x-axis
        else if (dir == 1)
            j = 1;
        //if top move -1 in the y-axis
        else if (dir == 2)
            i = -1;
        //if left move -1 in the x-axis
        else if (dir == 3)
            j = -1;

        //if the next area is not a wall
        if (lvl[x + i][y + j] != '%') {

            //set this area to empty
            lvl[x][y] = ' ';

            //set the next area to player 'P'
            lvl[x + i][y + j] = 'P';

            //calculate heurstic
            getHeurstic();

            //return true
            return true;

        }

        //if it can't move return false
        return false;
    }

    /**
     * A method called to get the CODE that describes this World state
     */
    public String getCode() {

        String currentCode = "";
        String paths = "";
        String player = "";

        for (int i = 0; i < lvl.length; i++) {
            for (int j = 0; j < lvl[i].length; j++) {
                if (lvl[i][j] == 'P') {
                    player += i;
                    player += j;
                } else if (lvl[i][j] == '.') {
                    paths += i;
                    paths += j;
                }
            }
        }

        currentCode += player;
        currentCode += paths;

        return currentCode;

    }

    /**
     * A method to detect if this world is already visited or not
     */
    public boolean isVisited(List<String> visitedCodes) {

        String currentCode = getCode();

        for (String s : visitedCodes) {

            if (s.equals(currentCode))
                return true;
        }

        return false;

    }

    /**
     * A method called to print this world (USED FOR DEBUGGING)
     */
    public void printWorld() {

        for (int i = 0; i < lvl.length; i++) {
            for (int j = 0; j < lvl[i].length; j++) {
                System.out.print(lvl[i][j]);
            }
            System.out.print('\n');
        }

    }

    /**
     * A method called to detect if this level is a complete level or not
     */
    public boolean isComplete() {

        for (int i = 0; i < lvl.length; i++)
            for (int j = 0; j < lvl[i].length; j++)
                if (lvl[i][j] == '.') {
                    return false;
                }

        return true;

    }

}
