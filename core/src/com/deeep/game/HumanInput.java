package com.deeep.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.game.entities.Entity;

/**
 * Created by Elmar on 14-12-2014.
 */
public class HumanInput extends Entity {
    private static HumanInput humanInput;
    private float length;
    private float x2, y2;
    private float length2;
    private float angle2;

    public static HumanInput getHumanInput() {
        if (humanInput == null) {
            humanInput = new HumanInput();
        }
        return humanInput;
    }

    public void update(float deltaT) {
        x = Gdx.input.getX();
        y = Gdx.graphics.getHeight() - Gdx.input.getY();
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        //draw pugs
    }
}
