package com.shich.game.render;

import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Model {

    private int draw_count;
    private int vao;
    private int vbo, tbo, ibo;

    private int[] indices;

    public Model(float[] vertices, float[] texture, int[] indices) {
        draw_count = indices.length;
        this.indices = indices;

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = storeData(floatBuffer(vertices), 0, 3);
        tbo = storeData(floatBuffer(texture), 1, 2);

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.indices, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int storeData(FloatBuffer buffer, int index, int size) {
        int buffer_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, buffer_id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return buffer_id;
    }

    public int getDraw_count() {
        return draw_count;
    }

    public int getVao() {
        return vao;
    }

    public int getVbo() {
        return vbo;
    }

    public int getTbo() {
        return tbo;
    }

    public int getIbo() {
        return ibo;
    }

    public FloatBuffer floatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public void destroy() {
        glDeleteBuffers(vbo);
        glDeleteBuffers(tbo);
        glDeleteBuffers(ibo);
        glDeleteVertexArrays(vao);
    }
}
