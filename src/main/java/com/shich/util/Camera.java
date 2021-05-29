package com.shich.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f offset;
    private Vector3f translate;
    private Matrix4f transform;
    private Matrix4f projection;

    private Window window;

    public Camera(Window window) {
        this.window = window;
        int width = window.getWidth();
        int height = window.getHeight();
        offset = new Vector3f();
        translate = new Vector3f();
        transform = new Matrix4f().setOrtho2D(-width / 2, width / 2, -height / 2, height / 2).scale(128);
    }

    public void setOffset(Vector3f offset) {
        this.offset = offset;
    }

    public void setPosition(Vector3f position) {
        position.negate(translate);
        translate.sub(offset);
    }

    public void addPosition(Vector3f position) {
        translate.add(position);
    }

    public Vector3f getPosition() {
        return translate;
    }

    public void setProjection(int width, int height) {
        projection = new Matrix4f().setOrtho2D(-width / 2, width / 2, -height / 2, height / 2).scale(128);
    }

    public Matrix4f getProjection() {
        return new Matrix4f(projection);
    }

    public Vector3f reverseProjection(Vector3f input_pos) {
        Vector3f result = new Vector3f();
        // input_pos.div(128, result);
        // translate.sub(result, result);
        return result;
    }

    public void update() {
        if (window.size_changed) {
            setProjection(window.getWidth(), window.getHeight());
        }
        projection = new Matrix4f(transform).translate(translate);
    }
}
