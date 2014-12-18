package com.deeep.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deeep.game.entities.Tank;
import com.deeep.game.pathfinding.Pathfinder;
import com.deeep.game.world.Grid;

/**
 * Created by scanevaro on 13/12/2014.
 */
public class World extends Actor {

    private Tank tank;
    private Grid grid;
    private Pathfinder pathFinder;
    private ClickListener clickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {

        }
    };

    public World() {
        addListener(clickListener);
        grid = new Grid();
        pathFinder = new Pathfinder();
        pathFinder.findPath(2, 2, 4, 4, grid);
        tank = new Tank(0, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = new Color(getColor().r, getColor().g,
                getColor().b, getColor().a * parentAlpha);

        batch.setColor(color);
        batch.enableBlending();
        //System.out.println(tank);
        // System.out.println(HumanInput.getHumanInput());
        grid.draw((SpriteBatch) batch);
        tank.draw((SpriteBatch) batch);
    }

    @Override
    public void act(float delta) {
        tank.update(delta);
    }
}