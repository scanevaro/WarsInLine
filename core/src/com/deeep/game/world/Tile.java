package com.deeep.game.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.game.pathfinding.Node;
import com.deeep.game.util.Assets;

/**
 * Created by Andreas on 12/14/2014.
 */
public class Tile {
    public boolean isSolid;
    public float x, y, renderX, renderY;
    public int size;
    public Node node;
    public Grid parentGrid;

    public Tile(float x, float y, int tileSize, boolean isSolid, Grid parentGrid) {
        this.x = x;
        this.y = y;
        this.renderX = x * tileSize;
        this.renderY = y * tileSize;
        this.size = tileSize;
        this.isSolid = isSolid;
        this.node = new Node(this);
        this.parentGrid = parentGrid;
    }

    public void draw(SpriteBatch batch) {
        if (isSolid) batch.draw(Assets.getAssets().solidTile, renderX, renderY);
        else batch.draw(Assets.getAssets().nonSolidTile, renderX, renderY);
        batch.draw(Assets.getAssets().tileOverlay, renderX, renderY);
        if (node.secondaryNode) batch.draw(Assets.getAssets().secondaryNode, renderX, renderY);
        if (node.startNode) batch.draw(Assets.getAssets().startNode, renderX, renderY);
        if (node.initialNode) batch.draw(Assets.getAssets().initialNode, renderX, renderY);
        if (node.finalNode) batch.draw(Assets.getAssets().finalNode, renderX, renderY);
        if (node.h > 0) {
            Assets.getAssets().getBitmapFont().draw(batch, node.h + "", renderX, renderY);
        }
    }

    public void update(float delta) {
        node.update();
    }

    public void clearNode() {
        node = new Node(this);
    }

}
