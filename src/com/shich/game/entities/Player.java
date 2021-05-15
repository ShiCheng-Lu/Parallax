package com.shich.game.entities;

import com.shich.game.level.Level;
import com.shich.game.util.KeyHandler;
import com.shich.game.util.MouseHandler;

import java.awt.Graphics;
import java.awt.Color;

public class Player extends Entity {

    // movement
    protected int xDir = 1;
    protected double xVel, yVel, xAcc;
    protected double xNew, yNew;
    protected double xVelMax, yVelMax;
    // dash
    protected int dashTime, dashDist, dashCooldown;
    protected int dashTimer = 0, dashCooldownTimer = 0;
    protected double dashSpeed;
    // jump
    protected int jumpTime = 0, jumpTimer;
    protected boolean onGround = true, jumpHeld;
    protected int coyoteTime, coyoteTimer;

    protected double maxHeight;

    // jumps
    protected double jumpDist, jumpHeight, jumpVel;
    protected double gravity;

    private Level level;

    public Player(String name, int x, int y, Level level) {
        super(x, y, 1, 1);
        loadImage("src/com/shich/game/graphics/player.png");
        this.level = level;
        //
        xVelMax = 0.15;
        yVelMax = 0.3;

        dashCooldown = 30;
        dashDist = 5;
        dashTime = 10;
        dashSpeed = (double) dashDist / dashTime;

        jumpDist = 7;
        jumpHeight = 5;
        coyoteTime = 3;

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

    private void collisionUpdateX() {

        if (level.checkCollide(xNew, y, width, height)) {
            if (xDir == 1) {
                xNew = Math.floor(xNew);
                xVel = 0;
            } else {
                xNew = Math.ceil(xNew);
                xVel = 0;
            }
        }
    }

    private void collisionUpdateY() {

        if (level.checkCollide(xNew, yNew, width, height)) {
            if (yVel > 0) {
                yNew = Math.floor(yNew);
                yVel = 0;
            } else {
                yNew = Math.ceil(yNew);
                yVel = 0;
                onGround = true;
                coyoteTimer = coyoteTime;
            }
        }

        if (onGround == false && coyoteTimer > 0) {
            coyoteTimer--;
        }
    }

    @Override
    public void update() {
        super.update();
        double deltaTime = 1;

        if (yVel > 0 && !jumpHeld) {
            yVel -= 2 * gravity;
        }
        if (yVel < 0) {
            onGround = false;
        }

        dashUpdate();

        // simplified velocity verlet
        xNew = x + xVel * xDir * deltaTime;
        collisionUpdateX();
        yNew = y + yVel * deltaTime - gravity * 0.5 * deltaTime * deltaTime;
        collisionUpdateY();
        // update to new coord
        x = xNew;
        y = yNew;
        // update to velocity
        xVel = 0;
        yVel -= gravity * deltaTime;

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