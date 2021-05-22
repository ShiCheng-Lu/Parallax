package com.shich.game.util;

import com.shich.game.render.Shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f position;
    private Matrix4f projection;

    private Window window;
    private Shader shader;

    public Camera(Window window, Shader shader) {
        this.window = window;
        this.shader = shader;
        int width = window.getWidth();
        int height = window.getHeight();
        position = new Vector3f(0, 0, 0);
        projection = new Matrix4f().setOrtho2D(-width/2, width/2, -height/2, height/2).scale(64);

        shader.bind();
        shader.setUniform("projection", getProjection());
        shader.unbind();
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void addPosition(Vector3f position) {
        this.position.add(position);
    }

    public Vector3f getPosition() { return position; }

    public void setProjection(int width, int height) {
        projection = new Matrix4f().setOrtho2D(-width/2, width/2, -height/2, height/2).scale(64);
    }

    public Matrix4f getProjection() {
        Matrix4f target = new Matrix4f();
        Matrix4f pos = new Matrix4f().setTranslation(position);
        target = projection.mul(pos, target);
        return target;
    }

    public void update() {
        if (window.size_changed) {
            setProjection(window.getWidth(), window.getHeight());
            shader.bind();
            shader.setUniform("projection", getProjection());
            shader.unbind();
        }
    }
}
