package com.shich.game.entities.components;

import com.shich.game.entities.Entity;
import com.shich.game.util.Timer;

import org.joml.Vector3f;

public class Movable extends Component {
    private Vector3f velocity = new Vector3f();
    private float max_vel;
    private float acceleration;

    public void activate(Entity master, float max_vel, float acceleration) {
        super.activate(master);
        this.max_vel = max_vel;
        this.acceleration = acceleration;
    }

    public void update(Timer timer) {
        if (!active) {
            return;
        }
        velocity.mul(timer.delta);
        // move and collide
        master.collidable.update(timer);
        master.bounds.center.add(velocity);

        velocity.div(timer.delta);
        // friction
        velocity.mul(0.8f);
    }

    public void accerlate(Vector3f acc) {
        if (!active) {
            return;
        }
        velocity.add(acc.mul(acceleration));
        float cur_vel = velocity.x * velocity.x + velocity.y * velocity.y;
        if (cur_vel > max_vel) {
            velocity.mul(max_vel).div(cur_vel);
        }
    }
}
