package com.shich.game.entities;

import com.shich.game.collision.AABB;
import com.shich.game.collision.Collision;
import com.shich.game.level.Layer;
import com.shich.game.level.Level;
import com.shich.game.util.Input;
import com.shich.game.util.Timer;

import org.joml.Vector3f;

public class Player extends Mob {

    public Player(AABB bounds, Level level) {
        super(bounds, level);
        renderSetup("player.png");
        // //
        // xVelMax = 0.15;
        // yVelMax = 0.3;

        // dashCooldown = 30;
        // dashDist = 5;
        // dashTime = 10;
        // dashSpeed = (double) dashDist / dashTime;

        // jumpDist = 7;
        // jumpHeight = 5;
        // // coyoteTime = 3;

        // gravity = (8 * jumpHeight * xVelMax * xVelMax) / (jumpDist * jumpDist);
        // jumpVel = (4 * jumpHeight * xVelMax) / jumpDist + (gravity * 0.1); //
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
            position.add(new Vector3f(-0.1f, 0, 0));
        }
        if (input.isKeyDown(input.RIGHT)) {
            position.add(new Vector3f(0.1f, 0, 0));
        }
        if (input.isKeyDown(input.UP)) {
            position.add(new Vector3f(0, 0.1f, 0));
        }
        if (input.isKeyDown(input.DOWN)) {
            position.add(new Vector3f(0, -0.1f, 0));
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
        // }
        // if (key.jump.pressed() && jumpHeld) {
        // // extend jump
        // } else {
        // jumpHeld = false;
        // }
    }

    public void update(Timer timer, Layer layer) {
        super.update(timer);

        layer.collisionMod.transformPosition(position);

        int lowerX = (int) Math.floor(bounding_box.getMinX());
        int upperX = (int) Math.ceil(bounding_box.getMaxX());
        int lowerY = (int) Math.floor(bounding_box.getMinY());
        int upperY = (int) Math.ceil(bounding_box.getMaxY());

        for (int x = lowerX; x <= upperX; ++x) {
            for (int y = lowerY; x <= upperY; ++y) {

                byte type = layer.get(x, y);
                if (type != 0) {
                    AABB bounds = layer.getBoundingBox(x, y);

                    Collision collision = bounding_box.getCollision(bounds);

                    if (collision.intersects) {
                        bounding_box.correctPosition(bounds, collision);
                    }
                }

            }
        }

        layer.collisionModinverted.transformPosition(position);
        if (position != bounding_box.center) {
            System.out.println("desynced??");
        }
    }
}