package com.shich.game.collision;

import org.joml.Vector3f;

// help with collision resolution
public class Collision implements Comparable<Collision> {
    public AABB target;
    public boolean intersects;
    public Vector3f normal;
    public float time;
    public Vector3f contact_loc;

    public Collision(boolean intersects, AABB target, Vector3f normal, float time) {
        this.intersects = intersects;
        this.normal = normal;
        this.time = time;
        this.target = target;
    }

    public Collision(boolean intersects, AABB target) {
        this(intersects, target, null, -1);
    }

    @Override
    public int compareTo(Collision o) {
        if (time <= o.time) {
            return -1;
        } else if (time > o.time) {
            return 1;
        } else {
            return 0;
        }
    }
}