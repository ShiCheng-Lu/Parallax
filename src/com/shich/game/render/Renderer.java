package com.shich.game.render;

import com.shich.game.util.Camera;

import static org.lwjgl.opengl.GL30.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Renderer {

    private Shader shader;
    private Camera camera;

    public Renderer(Shader shader, Camera camera) {
        this.camera = camera;
        this.shader = shader;
    }

    public void render(Model model, Vector3f position, Texture texture) {
        shader.bind();
        texture.bind();

        shader.setUniform("tex", 0);
        shader.setUniform("projection", camera.getProjection(position));
        renderModel(model);

        texture.unbind();
        shader.unbind();
    }

    private void renderModel(Model model) {
        
        glEnable (GL_BLEND);
        glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glBindVertexArray(model.getVao());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, model.getIbo());

        glDrawElements(GL_TRIANGLES, model.getDraw_count(), GL_UNSIGNED_INT, 0);
        // unbind and disable state
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }
}
