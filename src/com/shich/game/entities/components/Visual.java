package com.shich.game.entities.components;

import com.shich.game.entities.Entity;
import com.shich.game.entities.render.Model;
import com.shich.game.entities.render.Renderer;
import com.shich.game.entities.render.Texture;
import com.shich.game.util.Timer;

import org.joml.Vector3f;

public class Visual extends Component {
    private Model model;
    private Texture texture;
    private Vector3f visual_pos;

    public void activate(Entity master, Model model, Texture texture) {
        super.activate(master);
        this.model = model;
        this.texture = texture;
        visual_pos = master.getPos();
    }

    public void update(Timer timer) {
        visual_pos = master.getPos();
    }

    public void render(Renderer renderer) {
        if (!active) {
            return;
        }
        renderer.render(model, visual_pos, texture);
    }
}
