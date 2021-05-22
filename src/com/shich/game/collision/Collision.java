package com.shich.game.collision;

import org.joml.Vector3f;

// help with collision resolution
public class Collision {
    public Vector3f distance;
    public boolean intersects;

    public Collision(Vector3f distance) {
        this.distance = distance;
        intersects = (distance.x < 0 && distance.y < 0 && distance.z < 0);
    }
}