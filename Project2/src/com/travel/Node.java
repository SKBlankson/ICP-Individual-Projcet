package com.travel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.PriorityQueue;

/** A class used to model a node on the search tree */
public class Node {
    public Airport state;
    public Node parent;
    public int pathCost;
    public Route action; // safe delete this


    @Override
    public String toString() {
        return "Node{" +
                "state=" + state +
                ", parent=" + parent +
                ", pathCost=" + pathCost +
                ", action=" + action;

    }


    /** Constructor for the node class */
    public Node(Airport state, Node parent, int pathCost, Route action) {
        this.state = state;
        this.parent = parent;
        this.pathCost = pathCost;
        this.action = action;
    }


    public Airport getState() {
        return state;
    }

    public void setState(Airport state) {
        this.state = state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public Route getAction() {
        return action;
    }

    public void setAction(Route action) {
        this.action = action;
    }
}
