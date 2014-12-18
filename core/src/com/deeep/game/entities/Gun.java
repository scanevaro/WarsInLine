package com.deeep.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.game.HumanInput;
import com.deeep.game.util.Assets;

/**
 * Created by Elmar on 14-12-2014.
 */
public class Gun extends Entity {
    private static final int ANTI_AIR = 0;
    private int selectedGun = ANTI_AIR;
    private static final int ANTI_INFANTRY = 1;
    private static final int ANTI_TANK = 2;
    private Sprite sprites[];

    public Gun() {
        sprites = new Sprite[3];
        sprites[ANTI_AIR] = new Sprite(Assets.getAssets().getRegion("gun_aa"));
        sprites[ANTI_INFANTRY] = new Sprite(Assets.getAssets().getRegion("gun_ai"));
        sprites[ANTI_TANK] = new Sprite(Assets.getAssets().getRegion("gun_at"));
        for (Sprite sprite : sprites) {
            sprite.setOrigin(8, 16);
        }
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
            sprite.setScale(2);
        }
    }

    public void setSelectedGun(int selection) {
        this.selectedGun = selection;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        sprites[selectedGun].draw(spriteBatch);
    }
}
