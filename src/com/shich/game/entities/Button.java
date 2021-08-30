package com.shich.game.entities;

import com.shich.game.entities.bounds.AABB;
import com.shich.game.entities.render.Model;
import com.shich.game.entities.render.Renderer;
import com.shich.game.entities.render.Texture;
import com.shich.game.util.Input;

public class Button extends Entity {

    private boolean canActivate;
    private boolean hovered;

    public Button(AABB bounds, String texture_file) {
        super(bounds);
        visual.activate(this, new Model(bounds.half_extent), new Texture(texture_file));
        hovered = false;
    }

    public void input(Input input) {
        super.input(input);
        if (bounds.contains(input.mouse_pos)) {
            if (input.isButtonPressed(input.MOUSE_LEFT)) {
                canActivate = true;
                pressed();

            }
            if (canActivate && input.isButtonReleased(input.MOUSE_LEFT)) {
                released();
            }
            hovered = true;
        } else {
            canActivate = false;
            hovered = false;
        }
    }

    public void render(Renderer renderer) {
        super.render(renderer);
        if (hovered) {
            renderHovered(renderer);
        }
    }

    public void renderHovered(Renderer renderer) {
    }

    public void pressed() {
    }

    public void released() {
    }
}