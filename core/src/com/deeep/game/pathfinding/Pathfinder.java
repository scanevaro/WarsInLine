package com.deeep.game.pathfinding;

import com.deeep.game.world.Grid;
import com.deeep.game.world.Tile;

import java.util.ArrayList;

/**
 * Created by Andreas on 12/18/2014.
 */
public class Pathfinder {

    public final int MAX_OUT = 5000;
    private Node startNode, endNode;
    private ArrayList<Node> closedNodes, path;

    public Pathfinder() {

    }

    public void findPath(int x, int y, int dx, int dy, Grid grid) {
        calculateHeuristicValues(grid, grid.getTile(x, y).node, grid.getTile(dx, dy).node);
    }

    private void calculateHeuristicValues(Grid grid, Node start, Node end) {
        for (Tile t : grid.tiles) {
            Node n = t.node;
            n.h = (int) Math.abs(n.parentTile.x - start.parentTile.x) + (int) Math.abs(n.parentTile.y - start.parentTile.y);
        }
    }

}
