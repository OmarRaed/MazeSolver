package com.maze.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents an Astar Tree Node
 */
public class AstarTreeNode implements Comparable<AstarTreeNode> {

    private Maze maze;
    private AstarTreeNode parent;
    private List<AstarTreeNode> children;

    public AstarTreeNode(Maze maze) {
        this.maze = maze;
        this.children = new ArrayList<>();
    }

    public AstarTreeNode(Maze maze, AstarTreeNode parent) {
        this.maze = maze;
        this.parent = parent;
        parent.addChild(this);
        this.children = new ArrayList<>();
    }

    public void addChild(AstarTreeNode child) {
        this.children.add(child);
    }

    public Maze getMaze() {
        return maze;
    }

    public AstarTreeNode getParent() {
        return parent;
    }

    public List<AstarTreeNode> getChildren() {
        return children;
    }

    @Override
    public int compareTo(AstarTreeNode o) {
        return (maze.getEvaluation()).compareTo(o.getMaze().getEvaluation());
    }
}
