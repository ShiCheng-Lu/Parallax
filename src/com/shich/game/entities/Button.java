package com.shich.game.entities;

import com.shich.game.entities.bounds.AABB;
import com.shich.game.entities.render.Renderer;
import com.shich.game.util.Input;

public class Button extends StaticEntity {

    private boolean canActivate;
    private boolean hovered;

    /**
     * create a button
     * 
     * @param bounds
     * @param gs
     */
    public Button(AABB bounds, String texture_file) {
        super(bounds, texture_file);
        hovered = false;
    }

    /**
     * handles inputs from user
     * 
     * @param key   key inputs
     * @param mouse mouse inputs
     */
    public void input(Input input) {
        if (bounding_box.contains(input.mouse_pos)) {
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

    public void renderHovered(Renderer renderer) {}

    public void pressed() {}

    public void released() {}
}