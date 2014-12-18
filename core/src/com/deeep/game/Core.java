package com.deeep.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.game.screens.GameScreen;
import com.deeep.game.util.Assets;
import com.deeep.game.util.Settings;

/**
 * This class is the entry point to the game
 */
public class Core extends AbstractGame {

    public static float VIRTUAL_WIDTH = 960;
    public static float VIRTUAL_HEIGHT = 511;

    public SpriteBatch batch;
    public OrthographicCamera camera;

    /**
     * Called when the {@link com.badlogic.gdx.Application} is first created.
     */
    @Override
    public void create() {
        Settings.load();
        Assets.getAssets().load();
        batch = new SpriteBatch();
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);

        setScreen(new GameScreen(this));
    }

    /**
     * This will get rid of all the assets to prevent a memory leak
     */
    @Override
    public void dispose() {
        super.dispose();
        Assets.getAssets().dispose();
    }

    /**
     * Render & Update
     */
    @Override
    public void render(float deltaTime) {
        HumanInput.getHumanInput().update(deltaTime);
        Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}