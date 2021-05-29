package com.shich.entities;

import com.shich.entities.bounds.AABB;
import com.shich.entities.render.Model;
import com.shich.entities.render.Renderer;
import com.shich.entities.render.Texture;
import com.shich.util.Input;
import com.shich.util.Timer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Entity {

    public AABB bounding_box;
    protected Model model;
    protected Texture texture;
    
    protected Vector3f position;


    public Entity(AABB bounds) {
        bounding_box = bounds;
        position = bounds.center;
    }

    public Entity(AABB bounds, String texture_file) {
        this(bounds);
        renderSetup(texture_file);
    }

    public void renderSetup(String texture_file) {
        this.model = new Model(bounding_box.half_extent);
        this.texture = new Texture(texture_file);
    }

    public void input(Input input) {
    }

    public void update(Timer timer) {
    }

    public void render(Renderer renderer) {
        Matrix4f trans = new Matrix4f();
        trans.translate(position);
        
        renderer.render(trans, model, texture);
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
}
