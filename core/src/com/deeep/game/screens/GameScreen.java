package com.deeep.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.game.Core;
import com.deeep.game.classes.WorldTest1;

/**
 * Created by scanevaro on 13/12/2014.
 */
public class GameScreen implements Screen {
    private Core game;
    private SpriteBatch batch;
    private Stage stage;
    private WorldTest1 world;

    public GameScreen(Core game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.batch = game.batch;
        stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT), batch);
        world = new WorldTest1();

        stage.addActor(world);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        world.resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        world.dispose();
        stage.dispose();
    }
}