package com.shich.entities;

import com.shich.entities.bounds.AABB;
import com.shich.entities.render.Renderer;
import com.shich.level.Level;
import com.shich.util.Input;
import com.shich.util.KEYS;
import com.shich.util.Timer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Player extends DynamicEntity {

    public Player(AABB bounds, Level level) {
        super(bounds, level);
        renderSetup("player.png");

        velocityMax = new Vector3f(9, 18, 0);
        accerlation = new Vector3f(2, 2, 0);

        gravity = (8 * jumpHeight * velocityMax.x * velocityMax.x) / (jumpDist * jumpDist);
        jumpVel = jumpHeight *4 * velocityMax.x / jumpDist;
        
        // additional tolerance
    }


    public void input(Input input) {
        if (input.isKeyDown(KEYS.LEFT)) {
            velocity.x = Math.max(velocity.x - accerlation.x, -velocityMax.x);
        }
        if (input.isKeyDown(KEYS.RIGHT)) {
            velocity.x = Math.min(velocity.x + accerlation.x, velocityMax.x);
        }
        if (input.isKeyPressed(KEYS.UP)) {
            velocity.y = 10;
        }
        if (input.isKeyDown(KEYS.DOWN)) {
            velocity.y = Math.max(velocity.y - accerlation.y, -velocityMax.x);
        }
        if (input.isButtonPressed(KEYS.MOUSE_RIGHT)) {
            System.out.println(position);
        }

    }

    

    public void update(Timer timer) {
        
        super.update(timer);
        
        if (position.y < 0) {
            velocity.y = 0;
            position.y = 0;
        } else {
            // velocity.y -= gravity * timer.delta;
        }
        velocity.x *= 0.8f;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    // public void render(Renderer renderer) {
    //     Matrix4f trans = new Matrix4f();
    //     if (velocity.x > 0) {
    //         trans.reflect(new Vector3f(0, 1, 0), new Vector3f());
    //     }
    //     trans.translate(position);

    //     renderer.render(trans, model, texture);
    // }
}