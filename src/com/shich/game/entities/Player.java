package com.shich.game.entities;

import com.shich.game.collision.AABB;
import com.shich.game.level.Level;
import com.shich.game.util.Input;
import com.shich.game.util.Timer;

import org.joml.Vector3f;

public class Player extends Mob {

    public Player(AABB bounds, Level level) {
        super(bounds, level);
        renderSetup("player.png");

        velocityMax = new Vector3f(9, 18, 0);
        accerlation = new Vector3f(2, 2, 0);
        // //
        // xVelMax = 0.15;
        // yVelMax = 0.3;

        // dashCooldown = 30;
        // dashDist = 5;
        // dashTime = 10;
        // dashSpeed = (double) dashDist / dashTime;

        jumpDist = 7;
        jumpHeight = 5;
        // coyoteTime = 3;

        gravity = (8 * jumpHeight * velocityMax.x * velocityMax.x) / (jumpDist * jumpDist);
        jumpVel = jumpHeight *4 * velocityMax.x / jumpDist;
        
        // additional tolerance
    }

    // private void dash() {
    // dashTimer = dashTime;
    // dashCooldownTimer = dashCooldown;
    // xVel = dashSpeed;
    // }

    // private void jump() {
    // yVel = jumpVel;
    // onGround = false;
    // jumpHeld = true;
    // }

    // private boolean dashUpdate() {
    // if (dashCooldownTimer > 0) {
    // dashCooldownTimer--;
    // }
    // if (dashTimer > 0) {
    // // in dash
    // velocity.set(dashSpeed, 0);
    // dashTimer--;
    // return true;
    // } else {
    // // normal movement
    // return false;
    // }
    // }

    // public void drawHUD(Graphics g) {
    // g.setColor(Color.BLACK);
    // g.drawString(String.format("x: %.2f, y: %.2f", getX(), getY()), 1000, 20);
    // }

    public void input(Input input) {
        if (input.isKeyDown(input.LEFT)) {
            velocity.x = Math.max(velocity.x - accerlation.x, -velocityMax.x);
        }
        if (input.isKeyDown(input.RIGHT)) {
            velocity.x = Math.min(velocity.x + accerlation.x, velocityMax.x);
        }
        if (input.isKeyPressed(input.UP)) {
            velocity.y = jumpVel;
        }
        if (input.isKeyDown(input.DOWN)) {
            velocity.y = Math.max(velocity.y - accerlation.y, -velocityMax.x);
        }
        // if (dashTimer == 0) {
        // if (input.isKeyDown(GLFW_KEY_W)) {
        // velocity.x += accerlation.x;
        // }
        // if (key.right.pressed()) {
        // velocity.x += accerlation.x;
        // }
        // }

        // if (key.dash.clicked() && dashCooldownTimer == 0) {
        // dash();
        // }

        // if (key.jump.clicked()) {
        // if (onGround || coyoteTimer > 0) {
        // jump();
        // }
        // }a
        // if (key.jump.pressed() && jumpHeld) {
        // // extend jump
        // } else {
        // jumpHeld = false;
        // }
        if (input.isButtonPressed(input.MOUSE_RIGHT)) {
            System.out.println(position);
        }

    }

    

    public void update(Timer timer) {
        
        super.update(timer);
        
        if (position.y < 0) {
            velocity.y = 0;
            position.y = 0;
        } else {
            velocity.y -= gravity * timer.delta;
        }
        velocity.x *= 0.8f;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}