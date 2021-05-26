package com.shich.game.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f offset;
    private Vector3f position;
    private Matrix4f projection;

    private Window window;

    public Camera(Window window) {
        this.window = window;
        int width = window.getWidth();
        int height = window.getHeight();
        offset = new Vector3f();
        position = new Vector3f();
        projection = new Matrix4f().setOrtho2D(-width/2, width/2, -height/2, height/2).scale(128);
    }

    public void setOffset(Vector3f offset) {
        this.offset = offset;
    }

    public void setPosition(Vector3f position) {
        position.add(offset, this.position);
    }

    public void addPosition(Vector3f position) {
        this.position.add(position);
    }

    public Vector3f getPosition() { return position; }

    public void setProjection(int width, int height) {
        projection = new Matrix4f().setOrtho2D(-width/2, width/2, -height/2, height/2).scale(128);
    }

    public Matrix4f getProjection(Vector3f abs_pos) {
        Matrix4f camera_projection = new Matrix4f();
        Vector3f total_translation = new Vector3f();
        abs_pos.sub(position, total_translation);
        projection.translate(total_translation, camera_projection);
        return camera_projection;
    }

    public Vector3f reverseProjection(Vector3f input_pos) {
        Vector3f result = new Vector3f();
        input_pos.div(128, result);
        position.sub(result, result);
        return result;
    }

    public void update() {
        if (window.size_changed) {
            setProjection(window.getWidth(), window.getHeight());
        }
    }
}
