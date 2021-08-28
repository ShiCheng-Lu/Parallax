package com.shich.game.entities;

import com.shich.game.entities.bounds.AABB;
import com.shich.game.entities.render.Renderer;

public class StaticEntity extends Entity {

    public StaticEntity(AABB bounds) {
        super(bounds);
    }

    public StaticEntity(AABB bounds, String texture_file) {
        super(bounds, texture_file);
    }
}
