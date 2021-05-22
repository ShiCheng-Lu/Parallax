package com.shich.game.util;

import com.shich.game.render.Shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f position;
    private Matrix4f projection;

    public Camera(int width, int height) {
        position = new Vector3f();
        projection = new Matrix4f().ortho2D(-width/2, width/2, -height/2, height/2).scale(64);
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void addPosition(Vector3f position) {
        this.position.add(position);
    }

    public Vector3f getPosition() { return position; }

    public void setProjection(int width, int height) {
        projection = new Matrix4f().ortho2D(-width/2, width/2, -height/2, height/2).scale(64);
    }

    public Matrix4f getProjection() {
        Matrix4f target = new Matrix4f();
        projection.translate(position, target);
        return target;
    }
}
