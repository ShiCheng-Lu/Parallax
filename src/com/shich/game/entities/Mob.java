package com.shich.game.entities;

import com.shich.game.collision.*;
import com.shich.game.level.Layer;
import com.shich.game.level.Level;
import com.shich.game.util.Timer;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Mob extends Entity {

    protected Vector3f velocity;
    protected Vector3f accerlation;
    protected Vector3f velocityMax;

    // dash
    protected int dashTime, dashDist, dashCooldown;
    protected int dashTimer = 0, dashCooldownTimer = 0;
    protected float dashSpeed;
    // jump
    protected int jumpTime = 0, jumpTimer;
    protected boolean onGround = true, jumpHeld;
    protected int coyoteTime, coyoteTimer;

    protected float maxHeight;

    // jumps
    protected float jumpDist, jumpHeight, jumpVel;
    protected float gravity;

    protected Level level;

    
    protected Vector3f old_pos;

    public Mob(AABB bounds, Level level) {
        super(bounds);
        this.level = level;

        position = bounds.center;
        velocity = new Vector3f();
        accerlation = new Vector3f();
    }

    private void collideWithLayer(Layer layer, Timer timer) {
        layer.collisionMod.transformPosition(position);
        layer.collisionMod.transformPosition(velocity);

        float closest_time = 1;
        Collision closest_collision = null;

        for (int x = (int) position.x - 2; x <= (int) position.x + 2; ++x) {
            for (int y = (int) position.y - 2; y <= (int) position.y + 2; ++y) {

                byte type = layer.get(x, y);
                if (type != (byte) 0) {
                    AABB bounds = layer.getBoundingBox(x, y);

                    Collision collision = bounds.getCollision(bounding_box, velocity.mul(timer.delta, new Vector3f()));

                    if (collision.intersects && collision.time < closest_time) {

                        Vector3f vel_dif = collision.normal.mul(velocity).mul(1 - collision.time);
                        velocity.add(vel_dif);
                        closest_collision = collision;
                    }
                }
            }
        }
        layer.collisionModinverted.transformPosition(velocity);
        layer.collisionModinverted.transformPosition(position);

        // if (closest_collision != null) {
        // System.out.println(position);

        // System.out.println(vel_dif);
        // System.out.println(velocity);

        // velocity.add(vel_dif);
        // }
    }

    public void collideX(int i) {
        Layer layer = level.getLayer(i);

        for (int x = (int) (position.x / (i + 1)) - 2; x <= (int) (position.x / (i + 1)) + 2; ++x) {
            for (int y = (int) (position.y / (i + 1)) - 2; y <= (int) (position.y / (i + 1)) + 2; ++y) {

                byte type = layer.get(x, y);
                if (type != (byte) 0) {
                    AABB bounds = layer.getBoundingBox(x, y);

                    if (bounds.getCollision(bounding_box)) {
                        velocity.x = 0;
                        if (old_pos.x / (i + 1) < x) {
                            position.x = (float) Math.floor(position.x);
                        } else {
                            position.x = (float) Math.ceil(position.x);
                        }
                    }
                }
            }
        }
    }

    public void collideY(int i) {
        Layer layer = level.getLayer(i);

        for (int x = (int) (position.x / (i + 1)) - 2; x <= (int) (position.x / (i + 1)) + 2; ++x) {
            for (int y = (int) (position.y / (i + 1)) - 2; y <= (int) (position.y / (i + 1)) + 2; ++y) {

                byte type = layer.get(x, y);
                if (type != (byte) 0) {
                    AABB bounds = layer.getBoundingBox(x, y);

                    if (bounds.getCollision(bounding_box)) {
                        velocity.y = 0;
                        if (old_pos.y / (i + 1) < y) {
                            position.y = (float) Math.floor(position.y);
                        } else {
                            position.y = (float) Math.ceil(position.y);
                        }
                    }
                }
            }
        }
    }

    public void update(Timer timer) {
        old_pos = new Vector3f(position);

        position.add(velocity.x * timer.delta, 0, 0);
        for (int i = 0; i < level.layerNum; ++i) {
            collideX(i);
        }

        position.add(0, velocity.y * timer.delta, 0);
        for (int i = 0; i < level.layerNum; ++i) {
            collideY(i);
        }
    }
}
