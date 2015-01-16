package com.deeep.game.classes;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.collision.Ray;

/**
 * Created by scanevaro on 15/01/2015.
 */
public class WorldTest2InputAdapter extends InputAdapter {
    private WorldTest2 worldTest3;

    public WorldTest2InputAdapter(WorldTest2 worldTest3) {
        this.worldTest3 = worldTest3;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        worldTest3.selecting = worldTest3.getObject(screenX, screenY);
        return worldTest3.selecting >= 0;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (worldTest3.selecting < 0) return false;
        if (worldTest3.selected == worldTest3.selecting) {
            Ray ray = worldTest3.cam.getPickRay(screenX, screenY);
            final float distance = -ray.origin.y / ray.direction.y;
            worldTest3.position.set(ray.direction).scl(distance).add(ray.origin);
            worldTest3.instances.get(worldTest3.selected).transform.setTranslation(worldTest3.position);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (worldTest3.selecting >= 0) {
            if (worldTest3.selecting == worldTest3.getObject(screenX, screenY))
                worldTest3.setSelected(worldTest3.selecting);
            worldTest3.selecting = -1;
            return true;
        }
        return false;
    }


}