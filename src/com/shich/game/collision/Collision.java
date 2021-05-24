package com.shich.game.collision;

import org.joml.Vector3f;

// help with collision resolution
public class Collision {
    public boolean intersects;
    public Vector3f normal;
    public float time;
    public Vector3f contact_loc;

    public Collision(boolean intersects, Vector3f normal, float time) {
        this.intersects = intersects;
        this.normal = normal;
        this.time = time;
        // this.contact_loc = contact_loc;
    }
}