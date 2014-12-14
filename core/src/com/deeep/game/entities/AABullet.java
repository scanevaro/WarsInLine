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
        maxSpeed = 400 + force;
        speed = 400 + force;
        Pixmap pixmap = new Pixmap(3, 1 , Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        Color color = new Color(Color.RED);
        pixmap.setColor(color);
        pixmap.drawPixel(2, 0);
        color.a-= 0.2f;
        pixmap.setColor(color);
        pixmap.drawPixel(1,0);
        color.a-= 0.3f;
        pixmap.setColor(color);
        pixmap.drawPixel(0,0);
        Texture texture = new Texture(pixmap);
        sprite = new Sprite(texture);
    }
}
