package com.shich.game.states;

import com.shich.game.collision.AABB;
import com.shich.game.entities.Entity;
import com.shich.game.util.Input;

import org.joml.Vector2f;

public class Button extends Entity {


    private boolean clicked;
    /**
     * Create a button that with the shape
     * @param shape
     */
    public Button(AABB bounds, GameState gs) {
        super(bounds);
        clicked = false;
    }

    /**
     * handles inputs from user
     * @param key key inputs
     * @param mouse mouse inputs
     */
    public void input(Input inputs) {
        
    }

    /**
     * check if this button was clicked
     * @return true if the button was clicked
     */
    public boolean clicked() {
        if (clicked) {
            clicked = false;
            return true;
        }
        return false;
    }

    /**
     * display the button on screen
     * @param g graphic to display the button on
     */
    public void render() {
    }
}