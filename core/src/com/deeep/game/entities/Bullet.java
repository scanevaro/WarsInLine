package com.deeep.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Elmar on 14-12-2014.
 */
public abstract class Bullet extends MovingEntity {
    protected Sprite sprite;

    public Bullet(float x, float y, float angle) {
        super(x, y);
        this.angle = angle;
    }

    @Override
    public void updateSpecific(float deltaT) {
        float degrees = (float) Math.toDegrees(angle);
        sprite.setRotation(degrees);
        sprite.setPosition(x, y);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }
}
