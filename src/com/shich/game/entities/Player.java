package com.shich.game.entities;

import com.shich.game.level.Level;
import com.shich.game.util.KeyHandler;
import com.shich.game.util.MouseHandler;

import java.awt.Graphics;
import java.awt.Color;

public class Player extends Mob {


    public Player(String name, int x, int y, Level level) {
        super(x, y, 1, 1, level);
        loadImage("player.png");
        setRenderSetting(32, -32, 0, 0);
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

    public void input(KeyHandler key, MouseHandler mouse) {
        if (dashTimer == 0) {
            if (key.left.pressed()) {
                xDir = -1;
                xVel = xVelMax;
            }
            if (key.right.pressed()) {
                xDir = 1;
                xVel = xVelMax;
            }
        }

        if (key.dash.clicked() && dashCooldownTimer == 0) {
            dash();
        }

        if (key.jump.clicked()) {
            if (onGround) { // || coyoteTimer > 0) { coyote time, unimplemented
                jump();
            }
        }
        if (key.jump.pressed() && jumpHeld) {
            // extend jump
        } else {
            jumpHeld = false;
        }
    }

    private boolean dashUpdate() {
        if (dashCooldownTimer > 0) {
            dashCooldownTimer--;
        }
        if (dashTimer > 0) {
            // in dash
            xVel = dashSpeed;
            yVel = 0;
            dashTimer--;
            return true;
        } else {
            // normal movement
            return false;
        }
    }

    @Override
    public void update() { 
        // handels jump
        if (yVel > 0 && !jumpHeld) {
            yVel -= 2 * gravity;
        }
        if (yVel < 0) {
            onGround = false;
        }
        dashUpdate();
        super.update();
    }

    public void drawHUD(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString(String.format("x: %.2f, y: %.2f", x, y), 1000, 20);
    }

    public void render(Graphics g) {
        super.render(g);
        g.drawRect(640, 480, 32, 32);
    }
}