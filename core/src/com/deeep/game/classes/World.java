package com.deeep.game.classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deeep.game.HumanInput;
import com.deeep.game.entities.Tank;

/**
 * Created by scanevaro on 13/12/2014.
 */
public class World extends Actor {

    private Tank tank;

    public World() {
        addListener(clickListener);

        tank = new Tank(50, 50);
    }

    private ClickListener clickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            HumanInput.getHumanInput().setPosition(x, y);
            System.out.println(x + "_ " + y);
        }
    };

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = new Color(getColor().r, getColor().g,
                getColor().b, getColor().a * parentAlpha);

        batch.setColor(color);
        batch.enableBlending();
        tank.draw((SpriteBatch) batch);
    }

    @Override
    public void act(float delta) {
        tank.update(delta);
    }
}