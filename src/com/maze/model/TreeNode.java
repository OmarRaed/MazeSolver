package com.maze.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents an Tree Node
 */
public class TreeNode implements Comparable<TreeNode> {

    private Maze maze;
    private TreeNode parent;
    private List<TreeNode> children;

    public TreeNode(Maze maze) {
        this.maze = maze;
        this.children = new ArrayList<>();
    }

    public TreeNode(Maze maze, TreeNode parent) {
        this.maze = maze;
        this.parent = parent;
        parent.addChild(this);
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        this.children.add(child);
    }

    public Maze getMaze() {
        return maze;
    }

    public TreeNode getParent() {
        return parent;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    @Override
    public int compareTo(TreeNode o) {
        return (maze.getHeurstic()).compareTo(o.getMaze().getHeurstic());
    }
}
