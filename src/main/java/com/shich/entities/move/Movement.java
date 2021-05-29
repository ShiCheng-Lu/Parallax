package com.shich.entities.move;

import com.shich.util.Timer;

import org.joml.Vector3f;

public class Movement {
    
    public Vector3f pos;
    public Vector3f vel;
    public Vector3f acc;

    Vector3f max_vel;

    public Movement(Vector3f pos, Vector3f vel, Vector3f acc) {

    }

    public void update(Timer time) {
        pos.add(vel);
        vel.add(acc);
    }

    public void setGravity(float g) {
        acc.y = g;
    }
}
