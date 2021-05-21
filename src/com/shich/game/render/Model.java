package com.shich.game.render;

import org.lwjgl.opengl.GL15;


public class Model {

    private Vertex[] vertices;

    public Model(float[] vertices, float[] texture, int[] indices) {

    }

    public void createModel() {
        vao = GL30.glGenVertexArray();
    }
}
