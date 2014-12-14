package com.deeep.game.entities;

/**
 * Created by Elmar on 14-12-2014.
 */
public abstract class MovingEntity extends Entity {
    //The movement per timestep
    protected float deltaX, deltaY;
    //The speed the movement will be multiplied with
    protected float speed;
    protected float maxSpeed = 10;
    protected float friction = 0;

    public MovingEntity() {
        super();
    }

    public MovingEntity(float x, float y) {
        super(x, y);
    }

    public void update(float deltaT) {
        x += Math.cos(angle) * speed * deltaT;
        y += Math.sin(angle) * speed * deltaT;
        if (speed < friction) {
            speed += friction;
        } else if (speed > friction) {
            speed -= friction;
        } else {
            speed = 0;
        }
        if (speed > maxSpeed) {
            speed = maxSpeed;
        }
        updateSpecific(deltaT);
    }

    public abstract void updateSpecific(float deltaT);


}
