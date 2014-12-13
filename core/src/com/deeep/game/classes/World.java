package com.deeep.game.classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deeep.game.Core;

/**
 * Created by scanevaro on 13/12/2014.
 */
public class World extends Actor {

    private OrthographicCamera camera;

    public World(Core game) {
        this.camera = game.camera;

        addListener(clickListener);
    }

    private ClickListener clickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {

        }
    };

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = new Color(getColor().r, getColor().g,
                getColor().b, getColor().a * parentAlpha);

        batch.setColor(color);
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        render();
    }

    private void render() {

    }
}