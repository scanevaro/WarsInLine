package com.deeep.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Elmar on 14-12-2014.
 */
public abstract class Entity {
    //The x and y of the image
    protected float x, y;
    //The position on the grid
    protected float gridX, gridY;
    //rotation of the image in radians
    protected double angle;
    private int entityId;
    private int entityType;
    private int playerId;

    public Entity() {
        reset();
    }

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void reset() {
        x = 0;
        y = 0;
        gridX = 0;
        gridY = 0;
        angle = 0;
    }

    public abstract void update(float deltaT);

    public abstract void draw(SpriteBatch spriteBatch);

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setPositionOnGrid(float gridX, float gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getGridX() {
        return gridX;
    }

    public float getGridY() {
        return gridY;
    }

    public double getAngle() {
        return angle;
    }

    public String toString() {
        return "[" + x + " - " + y + "](" + gridX + ", " + gridY + ")" + "Facing: " + angle;
    }

    public int getEntityId() {
        return entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public int getPlayerId() {
        return playerId;
    }
}
