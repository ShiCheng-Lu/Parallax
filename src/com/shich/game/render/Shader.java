package com.shich.game.render;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class Shader {
    private int program;
    private int vert;
    private int frag;

    public Shader(String file_name) {
        program = glCreateProgram();
        // create vertex shader
        vert = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vert, readFile(file_name + ".vert"));
        glCompileShader(vert);
        if (glGetShaderi(vert, GL_COMPILE_STATUS) == GL_FALSE) { // error checking
            System.err.println("Vertex Shader: " + glGetShaderInfoLog(vert));
            System.exit(1);
        }
        // create fragment shader
        frag = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(frag, readFile(file_name + ".frag"));
        glCompileShader(frag);
        if (glGetShaderi(frag, GL_COMPILE_STATUS) == GL_FALSE) { // error checking
            System.err.println("Fragment Shader: " + glGetShaderInfoLog(frag));
            System.exit(1);
        }

        glAttachShader(program, vert);
        glAttachShader(program, frag);

        glBindAttribLocation(program, 0, "vertices");

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Program Linking: " + glGetProgramInfoLog(program));
            System.exit(1);
        }
        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE) {
            System.err.println("Program Validate" + glGetProgramInfoLog(program));
            System.exit(1);
        }

        glDeleteShader(vert);
        glDeleteShader(frag);
    }

    public void bind() {
        glUseProgram(program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void destroy() {
        glDetachShader(program, vert);
        glDetachShader(program, frag);
        glDeleteShader(vert);
        glDeleteShader(frag);
        glDeleteProgram(program);
    }

    private String readFile(String file_path) {
        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("res/" + file_path)))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public void setUniform(String name, int value) {
        int location = glGetUniformLocation(program, name);
        if (location != -1) {
            glUniform1i(location, value);
        } else {
            System.out.println(name);
        }
    }

    public void setUniform(String name, Matrix4f value) {
        int location = glGetUniformLocation(program, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);
        if (location != -1) {
            glUniformMatrix4fv(location, false, buffer);
        } else {
            System.out.println(name);
        }
    }
}
