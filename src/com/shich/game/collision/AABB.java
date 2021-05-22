package com.shich.game.collision;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class AABB { // axis aligned bounding box
    public Vector3f center;
    public Vector3f half_extent;

    public AABB(Vector3f center, Vector3f half_extent) {
        this.center = center;
        this.half_extent = half_extent;
    }

    public AABB(float x, float y, float z, float width, float height, float depth) {
        this(new Vector3f(x, y, z), new Vector3f(width / 2, height / 2, depth / 2));
    }

    public AABB(float x, float y, float width, float height) {
        this(x, y, 0, width, height, 0);
    }

    public Collision getCollision(AABB other) {
        Vector3f distance = center.sub(other.center, new Vector3f()).absolute();

        distance.sub(half_extent);
        distance.sub(other.half_extent);

        return new Collision(distance);
    }

    public void correctPosition(AABB other, Collision collision) {
        if (collision.intersects) {
            if (collision.distance.x > collision.distance.y) {
                if (center.x > other.center.x) {
                    center.sub(collision.distance.x, 0, 0);
                } else {
                    center.add(collision.distance.x, 0, 0);
                }
            } else {
                if (center.x > other.center.x) {
                    center.sub(0, collision.distance.y, 0);
                } else {
                    center.add(0, collision.distance.y, 0);
                }
            }
            // ignores z axis for now
        }
    }

    public float getMinX() {
        return center.x - half_extent.x;
    }

    public float getMaxX() {
        return center.x + half_extent.x;
    }

    public float getMinY() {
        return center.y - half_extent.y;
    }

    public float getMaxY() {
        return center.y + half_extent.y;
    }

}
