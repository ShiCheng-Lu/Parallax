package com.shich.game.collision;

import org.joml.Vector2f;

// help with collision resolution
public class Collision {
    public Vector2f distance;
    public boolean intersects;

    public Collision(Vector2f distance) {
        this.distance = distance;
        intersects = (distance.x < 0 && distance.y < 0);
    }
}