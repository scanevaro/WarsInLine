package com.deeep.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.game.Core;
import com.deeep.game.HumanInput;
import com.deeep.game.classes.World;
import com.deeep.game.entities.Tank;

/**
 * Created by scanevaro on 13/12/2014.
 */
public class GameScreen implements Screen {
    private Core game;
    private SpriteBatch batch;
    private Stage stage;
    private World world;
    private Tank tank;
    private float x, y;


    public GameScreen(Core game) {
        this.game = game;
        tank = new Tank(50, 50);
    }

    @Override
    public void show() {
        this.batch = game.batch;
       // stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT), batch);
        world = new World(game);

       // stage.addActor(world);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw(batch);
       // stage.act();
       // stage.draw();
    }

    public void update(float deltaT) {
        tank.update(deltaT);
    }

    public void draw(SpriteBatch spriteBatch) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y--;
        }
        //System.out.println(tank);
       // System.out.println(HumanInput.getHumanInput());
       // batch.setProjectionMatrix(stage.getCamera().projection);
        batch.begin();
        tank.draw(spriteBatch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        /*stage.getViewport().update(width, height);*/
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
    }
}