package com.shich.game.collision;

import org.joml.Vector2f;

public class AABB { // axis aligned bounding box
    public Vector2f center;
    public Vector2f half_extent;

    public AABB(Vector2f center, Vector2f half_extent) {
        this.center = center;
        this.half_extent = half_extent;
    }

    public Collision getCollision(AABB other) {
        Vector2f distance = center.sub(other.center, new Vector2f()).absolute();

        distance.sub(half_extent);
        distance.sub(other.half_extent);

        return new Collision(distance);
    }

    public void correctPosition(AABB other, Collision collision) {
        if (collision.intersects) {
            if (collision.distance.x > collision.distance.y) {
                if (center.x > other.center.x) {
                    center.sub(collision.distance.x, 0);
                } else {
                    center.add(collision.distance.x, 0);
                }
            } else {
                if (center.x > other.center.x) {
                    center.sub(0, collision.distance.y);
                } else {
                    center.add(0, collision.distance.y);
                }
            }
        }
    }

    public float getMinX() { return center.x - half_extent.x; }
    public float getMaxX() { return center.x + half_extent.x; }
    public float getMinY() { return center.y - half_extent.y; }
    public float getMaxY() { return center.y + half_extent.y; }
    
}
