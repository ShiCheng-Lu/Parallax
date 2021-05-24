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

    public Collision getCollision(AABB other, Vector3f other_dir) {

        Vector3f original_half_extent = new Vector3f(half_extent);
        half_extent.add(other.half_extent);

        // do rayCollision;
        Collision result = rayCollision(other.center, other_dir);

        half_extent = original_half_extent;

        if (result.time <= 1 && result.time >= -0.0001f) {
            return result;
        } else {
            return new Collision(false, null, 0);
        }
    }

    public Collision rayCollision(Vector3f origin, Vector3f direction) {
        Vector3f invdir = new Vector3f(1, 1, 1).div(direction);

        Vector3f t_near = new Vector3f();
        center.sub(origin, t_near).sub(half_extent);
        t_near.mul(invdir);

        Vector3f t_far = new Vector3f();
        center.sub(origin, t_far).add(half_extent);
        t_far.mul(invdir);

        if (Float.isNaN(t_near.x) || Float.isNaN(t_near.y) || Float.isNaN(t_far.x) || Float.isNaN(t_far.y)) {
            return new Collision(false, null, 0);
        }

        if (t_near.x > t_far.x) {
            float temp = t_near.x;
            t_near.x = t_far.x;
            t_far.x = temp;
        }

        if (t_near.y > t_far.y) {
            float temp = t_near.y;
            t_near.y = t_far.y;
            t_far.y = temp;
        }

        if (t_near.x > t_far.y || t_near.y > t_far.x) {
            return new Collision(false, null, 0);
        }

        float t_hit_near = Math.max(t_near.x, t_near.y);
        float t_hit_far = Math.min(t_far.x, t_far.y);

        if (t_hit_far < 0) {
            return new Collision(false, null, 0);
        }

        Vector3f normal = new Vector3f();
        if (t_near.x > t_near.y) {
            if (direction.x > 0) {
                normal = new Vector3f(-1, 0, 0);
            } else {
                normal = new Vector3f(-1, 0, 0);
            }
        } else if (t_near.x < t_near.y) {
            if (direction.y > 0) {
                normal = new Vector3f(0, -1, 0);
            } else {
                normal = new Vector3f(0, -1, 0);
            }
        }

        // Vector3f contace_loc = new Vector3f();
        // direction.mul(t_hit_near, contace_loc).add(origin);

        return new Collision(true, normal, t_hit_near);
    }

    // public boolean correctPosition(Collision collision) {
    // if (collision.intersects) {

    // }
    // return false;
    // }

    // public boolean correctPositionY(AABB other, Collision collision) {
    // if (collision.intersects) {
    // if (collision.distance.x > collision.distance.y) {
    // if (center.y > other.center.y) {
    // center.sub(0, collision.distance.y, 0);
    // } else {
    // center.add(0, collision.distance.y, 0);
    // }
    // return true;
    // }
    // }
    // return false;
    // }

    public boolean contains(Vector3f pos) {
        Vector3f distance = new Vector3f();
        pos.sub(center, distance);
        distance.absolute();

        return (distance.x <= half_extent.x && distance.y <= half_extent.y && distance.z <= half_extent.z);
    }
}
