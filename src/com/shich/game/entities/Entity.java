package com.shich.game.entities;

import javax.swing.ImageIcon;

import com.shich.game.collision.AABB;
import com.shich.game.level.*;
import com.shich.game.render.*;
import com.shich.game.util.Input;
import com.shich.game.util.Timer;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Entity {

    protected AABB bounding_box;
    protected Vector3f position;

    protected Model model;
    protected Texture texture;

    public Entity(AABB bounds) {
        bounding_box = bounds;
        position = bounds.center;
    }

    public Entity(AABB bounds, String texture_file) {
        this(bounds);
        renderSetup(texture_file);
    }

    public void renderSetup(String texture_file) {
        float width = bounding_box.half_extent.x;
        float height = bounding_box.half_extent.y;

        float[] vertices = new float[] { 
            -width, height, 0, 
            width, height, 0,
            width, -height, 0,
            -width, -height, 0, };

        float[] texCoords = new float[] { 0, 0, 1, 0, 1, 1, 0, 1, };

        int[] indices = new int[] { 0, 1, 2, 2, 3, 0, };

        this.model = new Model(vertices, texCoords, indices);
        this.texture = new Texture(texture_file);
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
}
