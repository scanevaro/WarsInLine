package com.deeep.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.game.HumanInput;
import com.deeep.game.classes.Assets;

import java.util.ArrayList;

/**
 * Created by Elmar on 14-12-2014.
 */
public class Gun extends Entity {
    private static final int ANTI_AIR = 0;
    private static final int ANTI_INFANTRY = 1;
    private static final int ANTI_TANK = 2;
    private float reloadTimes[];
    private int selectedGun = ANTI_AIR;
    private float reloadTimer = 0;
    private Sprite sprites[];
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private Tank tank;

    public Gun(Tank tank) {
        sprites = new Sprite[3];
        reloadTimes = new float[3];
        setType(ANTI_AIR, "gun_aa", 0.1f);
        setType(ANTI_INFANTRY, "gun_ai", 0.5f);
        setType(ANTI_TANK, "gun_at", 0.25f);
        this.tank = tank;
    }

    private void setType(int type, String name, float reloadTime) {
        sprites[type] = new Sprite(Assets.getAssets().getRegion(name));
        reloadTimes[type] = reloadTime;
        sprites[type].setOrigin(8, 16);
    }

    @Override
    public void update(float deltaT) {
        float mouseDeltaX = x - HumanInput.getHumanInput().getX();
        float mouseDeltaY = y - HumanInput.getHumanInput().getY();
        angle = Math.atan2(mouseDeltaY, mouseDeltaX);
        float degrees = (float) Math.toDegrees(angle) - 180;
        for (Sprite sprite : sprites) {
            sprite.setCenter(x, y);
            sprite.setRotation(degrees);
            sprite.setScale(1.5f);
        }
        if (Gdx.input.isTouched()) {
            if (reloadTimer >= reloadTimes[selectedGun]) {
                switch (selectedGun) {
                    default:
                        bullets.add(new AABullet(x, y, (float) (angle - Math.PI), tank.speed));
                }
                reloadTimer -= reloadTimes[selectedGun];
            }
        }
        for (Bullet bullet : bullets) {
            bullet.update(deltaT);
        }
        if(reloadTimer<reloadTimes[selectedGun])
        reloadTimer += deltaT;
    }

    public void setSelectedGun(int selection) {
        this.selectedGun = selection;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        sprites[selectedGun].draw(spriteBatch);
        for (Bullet bullet : bullets) {
            bullet.draw(spriteBatch);
        }
    }
}
