package com.deeep.game.pathfinding;

import com.deeep.game.world.Tile;

/**
 * Created by Andreas on 12/18/2014.
 */
public class Node {

    public int f, g, h;
    public Tile parentTile;
    public boolean startNode = false;
    public boolean secondaryNode = false;

    public Node(Tile parentTile) {
        this.parentTile = parentTile;
    }

    public Node[] getSurroundingNodes() {
        startNode = true;
        Node[] nodes = new Node[8];
        nodes[0] = parentTile.parentGrid.tiles.get(((int) Math.floor(parentTile.x) - 1) + (int) Math.floor(parentTile.y - 1) * parentTile.parentGrid.WIDTH / 2).node;
        nodes[1] = parentTile.parentGrid.tiles.get(((int) Math.floor(parentTile.x)) + (int) Math.floor(parentTile.y - 1) * parentTile.parentGrid.WIDTH / 2).node;
        nodes[2] = parentTile.parentGrid.tiles.get(((int) Math.floor(parentTile.x) + 1) + (int) Math.floor(parentTile.y - 1) * parentTile.parentGrid.WIDTH / 2).node;
        nodes[3] = parentTile.parentGrid.tiles.get(((int) Math.floor(parentTile.x) - 1) + (int) Math.floor(parentTile.y) * parentTile.parentGrid.WIDTH / 2).node;
        nodes[4] = parentTile.parentGrid.tiles.get(((int) Math.floor(parentTile.x) + 1) + (int) Math.floor(parentTile.y) * parentTile.parentGrid.WIDTH / 2).node;
        nodes[5] = parentTile.parentGrid.tiles.get(((int) Math.floor(parentTile.x) - 1) + (int) Math.floor(parentTile.y + 1) * parentTile.parentGrid.WIDTH / 2).node;
        nodes[6] = parentTile.parentGrid.tiles.get(((int) Math.floor(parentTile.x)) + (int) Math.floor(parentTile.y + 1) * parentTile.parentGrid.WIDTH / 2).node;
        nodes[7] = parentTile.parentGrid.tiles.get(((int) Math.floor(parentTile.x) + 1) + (int) Math.floor(parentTile.y + 1) * parentTile.parentGrid.WIDTH / 2).node;
        for (Node n : nodes) {
            n.secondaryNode = true;
            System.out.println("Node found at: [" + n.parentTile.x + ", " + n.parentTile.y + "]");
        }
        return nodes;
    }

    public void update() {
        if (startNode) secondaryNode = false;
    }

}
