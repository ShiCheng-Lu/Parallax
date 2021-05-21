package com.shich.game.entities;

import javax.swing.ImageIcon;

import com.shich.game.collision.AABB;
import com.shich.game.level.*;
import com.shich.game.render.*;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Entity {

    protected AABB bounding_box;

    protected Vector2f scale;

    public Entity(AABB bounds) {
        bounding_box = bounds;
    }

    protected void renderSetup(String texture_file) {
        float[] vertices = new float[] {
            -1f, 1f, 0,
            1f, 1f, 0,
            1f, -1f, 0,
            -1f, -1f
        };

        float[] texture = new float[] {
            0,0,
            1,0,
            1,1,
            0,1
        };

        int[] indices = new int[] {
            0,1,2,
            2,3,0
        };

        model = new Model(vertices, texture, indices);
        tex = new Texture(texture_file);
    }


    public void input() {
    }

    public void update(double deltaTime) {
    }

    public void render() {
    }

    public float getX() { return bounding_box.center.x; }
    public float getY() { return bounding_box.center.y; }

    public void setPos(float x, float y) {
        bounding_box.center.set(x, y);
    }
}
