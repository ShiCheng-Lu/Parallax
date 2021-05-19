package com.shich.game.entities;

import com.shich.game.level.Level;

public class Mob extends Entity {

    // movement
    protected int xDir = 1;
    protected double xVel, yVel;
    protected double xVelMax, yVelMax;
    public double xNew, yNew;

    protected int xAcc;
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

    protected Level level;

    public Mob(double x, double y, double width, double height, Level level) {
        super(x, y, width, height);
        this.level = level;
    }
    
    private void collisionUpdateX() {
        if (level.checkCollide(this)) {
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
        if (level.checkCollide(this)) {
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

    public void update() {
        double deltaTime = 1;

        // simplified velocity verlet
        xNew = x + xVel * xDir * deltaTime;
        collisionUpdateX();
        yNew = y + yVel * deltaTime - gravity * 0.5 * deltaTime * deltaTime;
        collisionUpdateY();
        // update to new coord
        x = xNew;
        y = yNew;
        // update velocity
        xVel = 0;
        yVel -= gravity * deltaTime;
    }
}
