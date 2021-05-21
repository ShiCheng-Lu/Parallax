package com.shich.game.entities;

import com.shich.game.level.Level;
import com.shich.game.util.Input;

import java.awt.Graphics;
import java.awt.Color;

public class Player extends Mob {


    public Player(String name, int x, int y, Level level) {
        super(x, y, level);
        renderSetup("player.png");
        //
        xVelMax = 0.15;
        yVelMax = 0.3;

        dashCooldown = 30;
        dashDist = 5;
        dashTime = 10;
        dashSpeed = (double) dashDist / dashTime;

        jumpDist = 7;
        jumpHeight = 5;
        // coyoteTime = 3;

        gravity = (8 * jumpHeight * xVelMax * xVelMax) / (jumpDist * jumpDist);
        jumpVel = (4 * jumpHeight * xVelMax) / jumpDist + (gravity * 0.1); // additional tolerance
    }

    private void dash() {
        dashTimer = dashTime;
        dashCooldownTimer = dashCooldown;
        xVel = dashSpeed;
    }

    private void jump() {
        yVel = jumpVel;
        onGround = false;
        jumpHeld = true;
    }

    private boolean dashUpdate() {
        if (dashCooldownTimer > 0) {
            dashCooldownTimer--;
        }
        if (dashTimer > 0) {
            // in dash
            velocity.set(dashSpeed, 0);
            dashTimer--;
            return true;
        } else {
            // normal movement
            return false;
        }
    }

    public void drawHUD(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString(String.format("x: %.2f, y: %.2f", getX(), getY()), 1000, 20);
    }

    public void input(Input input) {
        if (dashTimer == 0) {
            if (input.isKeyDown(GLFW_KEY_W)) {
                velocity.x += accerlation.x;
            }
            if (key.right.pressed()) {
                velocity.x += accerlation.x;
            }
        }

        if (key.dash.clicked() && dashCooldownTimer == 0) {
            dash();
        }

        if (key.jump.clicked()) {
            if (onGround || coyoteTimer > 0) {
                jump();
            }
        }
        if (key.jump.pressed() && jumpHeld) {
            // extend jump
        } else {
            jumpHeld = false;
        }
    }

    public void update(double deltaTime) { 
        // handles jump

        // dashupdate must be after jump handling to kill vertical velocity
        dashUpdate();

        super.update(deltaTime);
    }
}