package com.shich.game.render;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.*;

public class Texture {

    private int texture_id;
    private IntBuffer width, height, channels;

    public Texture(String texture_file) {
        width = BufferUtils.createIntBuffer(1);
        height = BufferUtils.createIntBuffer(1);
        channels = BufferUtils.createIntBuffer(1);
        
        ByteBuffer image = STBImage.stbi_load("res/graphics/" + texture_file, width, height, channels, 0);
        
        if (image != null) {
            texture_id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, texture_id);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            glGenerateMipmap(GL_TEXTURE_2D);
        } else {
            System.out.println("texture not found: " + texture_file);
        }
        System.out.println(width.get(0) + "  -  " + height.get(0));
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture_id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
