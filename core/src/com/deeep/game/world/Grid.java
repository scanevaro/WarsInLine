package com.deeep.game.world;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Andreas on 12/14/2014.
 */
public class Grid {

    public final int TILE_SIZE = 64;
    public final int WIDTH = 20;
    public final int HEIGHT = 10;
    public ArrayList<Tile> tiles;
    private BitmapFont font;

    public Grid() {
        tiles = new ArrayList<Tile>();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Random r = new Random();
                tiles.add(new Tile(x, y, TILE_SIZE, r.nextFloat() < 0.1F, this));
            }
        }
    }

    public void draw(SpriteBatch batch) {
        Iterator i = tiles.iterator();
        while (i.hasNext()) {
            Tile t = (Tile) i.next();
            t.draw(batch);
        }
    }

    public void update(float delta) {
        Iterator i = tiles.iterator();
        while (i.hasNext()) {
            Tile t = (Tile) i.next();
            t.update(delta);
        }
    }

    public Tile getTile(int x, int y) {
        return tiles.get(x + y * HEIGHT);
    }

}
