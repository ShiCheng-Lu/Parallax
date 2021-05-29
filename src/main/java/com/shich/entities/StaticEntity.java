package com.shich.entities;

import com.shich.entities.bounds.AABB;

public class StaticEntity extends Entity {

    public StaticEntity(AABB bounds) {
        super(bounds);
    }

    public StaticEntity(AABB bounds, String texture_file) {
        super(bounds, texture_file);
    }
}
