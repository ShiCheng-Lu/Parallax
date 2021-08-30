package com.shich.game.entities;

import com.shich.game.entities.bounds.AABB;
import com.shich.game.entities.components.Collidable;
import com.shich.game.entities.components.Controllable;
import com.shich.game.entities.components.Movable;
import com.shich.game.entities.components.Visual;
import com.shich.game.entities.render.Renderer;
import com.shich.game.util.Input;
import com.shich.game.util.Timer;

import org.joml.Vector3f;

public class Entity {
    public AABB bounds;

    public Movable movable = new Movable();
    public Controllable controllable = new Controllable();
    public Collidable collidable = new Collidable();
    public Visual visual = new Visual();

    public Entity(AABB bounds) {
        this.bounds = bounds;
    }

    public void input(Input input) {
        controllable.input(input);
    }

    public void update(Timer timer) {
        movable.update(timer);
    }

    public void render(Renderer renderer) {
        // renderer.render(model, position, texture);
        visual.render(renderer);
    }

    public void setPos(float x, float y, float z) {
        // position.set(x, y, z);
    }

    public Vector3f getPos() {
        return bounds.center;
    }
}
