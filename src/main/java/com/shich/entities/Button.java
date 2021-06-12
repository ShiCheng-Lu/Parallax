package com.shich.entities;

import com.shich.entities.bounds.AABB;
import com.shich.entities.render.Renderer;
import com.shich.util.Input;
import com.shich.util.KEYS;

public class Button extends Entity {

    private boolean canActivate;
    public boolean hovered;

    /**
     * create a button
     * 
     * @param bounds
     * @param gs
     */
    public Button(AABB bounds, String texture_file, boolean is_static) {
        super(bounds, texture_file, is_static);
        hovered = false;
    }

    public Button(AABB bounds, String texture_file) {
        this(bounds, texture_file, false);
    }

    /**
     * handles inputs from user
     * 
     * @param key   key inputs
     * @param mouse mouse inputs
     */
    public void input(Input input) {
        if (bounding_box.contains(input.mouse_pos_raw)) {
            if (input.isButtonPressed(KEYS.MOUSE_LEFT)) {
                canActivate = true;
                pressed();

            }
            if (canActivate && input.isButtonReleased(KEYS.MOUSE_LEFT)) {
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