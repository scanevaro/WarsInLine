package com.deeep.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Elmar on 14-12-2014.
 */
public class AABullet extends Bullet {
    public AABullet(float x, float y, float angle, float force) {
        super(x, y, angle);
        maxSpeed = 200 + force;
        speed = 200 + force;
        Pixmap pixmap = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        sprite = new Sprite(texture);
    }
}
