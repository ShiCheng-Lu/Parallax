package com.shich.game.entities;

import com.shich.game.entities.bounds.AABB;
import com.shich.game.entities.render.Model;
import com.shich.game.entities.render.Renderer;
import com.shich.game.entities.render.Texture;
import com.shich.game.util.Input;
import com.shich.game.util.Timer;

import org.joml.Vector3f;

public class Entity {

    protected AABB bounding_box;
    protected Model model;
    protected Texture texture;

    protected Vector3f position;

    public Entity(AABB bounds) {
        bounding_box = bounds;
        position = bounds.center;
    }

    public Entity(AABB bounds, Model model, Texture texture) {
        this(bounds);
        this.model = model;
        this.texture = texture;
    }

    public void input(Input input) {
    }

    public void update(Timer timer) {
    }

    public void render(Renderer renderer) {
        renderer.render(model, position, texture);
    }

    public float getX() {
        return bounding_box.center.x;
    }

    public float getY() {
        return bounding_box.center.y;
    }

    public void setPos(float x, float y, float z) {
        position.set(x, y, z);
    }

    public Vector3f getPos() {
        return position;
    }

    public void setPos2D(float x, float y) {
        bounding_box.center.set(x, y, 0);
    }

    public void setPos(Vector3f direction) {
        position.set(direction);
    }

    public void resolveCollision(Entity other) {
        bounding_box
    }
}
