package com.deeep.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.deeep.game.classes.Assets;

/**
 * Created by Elmar on 14-12-2014.
 */
public class Tank extends MovingEntity {
    private Gun gun;
    private Animation animation;
    Sprite sprites[] = new Sprite[6];
    private float animationTimer = 0;

    public Tank(float x, float y) {
        super(x, y);
        gun = new Gun(this);
        TextureRegion textureRegion[][] = Assets.getAssets().getRegion("tank").split(31, 31);
        for (int i = 0; i < textureRegion[0].length; i++) {
            sprites[i] = new Sprite(textureRegion[0][i]);
            sprites[i].setOrigin(12, 16);
        }
        animation = new Animation(100, sprites);
        friction = 0.5f;
        maxSpeed = 100;
    }

    @Override
    public void updateSpecific(float deltaT) {
        gun.update(deltaT);
        gun.setPosition(x, y);
        updateSprites();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speed += 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            angle += (Math.PI * 2) * deltaT * ((speed * .5f) / maxSpeed) * .5f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            angle -= (Math.PI * 2) * deltaT * ((speed * .5f) / maxSpeed) * .5f;
        }
    }

    private void updateSprites() {
        float degrees = (float) Math.toDegrees(angle);
        for (Sprite sprite : sprites) {
            sprite.setCenter(x, y);
            sprite.setRotation(degrees);
            sprite.setScale(1.5f);
        }
        animationTimer += speed / maxSpeed;
        animationTimer %= 6;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        sprites[((int) animationTimer)].draw(spriteBatch);
        gun.draw(spriteBatch);
    }
}
