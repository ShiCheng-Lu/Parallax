package com.shich.game.entities;

import java.util.ArrayList;
import java.util.Arrays;

import com.shich.game.collision.*;
import com.shich.game.level.Block;
import com.shich.game.level.Layer;
import com.shich.game.level.Level;
import com.shich.game.render.Renderer;
import com.shich.game.util.Timer;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Mob extends Entity {

    private static Renderer renderer;
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

    private void collideWithLayer(Layer layer, Timer timer, Vector3f mod_pos, Vector3f mod_vel) {
        // layer.collisionMod.transformPosition(position);
        // layer.collisionMod.transfordwmPosition(velocity);
        mod_vel.mul(timer.delta);
        // float closest_time = 1;
        ArrayList<Collision> collisions = new ArrayList<>();

        for (int x = (int) mod_pos.x - 1; x <= (int) mod_pos.x + 2; ++x) {
            for (int y = (int) mod_pos.y - 1; y <= (int) mod_pos.y + 2; ++y) {

                byte type = layer.get(x, y);
                if (type != (byte) 0) {
                    AABB bounds = layer.getBoundingBox(x, y);

                    Collision collision = bounds.getCollision(bounding_box, mod_vel);
                    collisions.add(collision);
                }
            }
        }

        collisions.sort(null);
        for (Collision c : collisions) {
            // collide and update velocity
            Collision new_c = c.target.getCollision(bounding_box, mod_vel);
            if (new_c.intersects) {
                mod_vel.sub(new_c.normal.mul(mod_vel).mul(1 - new_c.time));
                Block.render(renderer, (byte)1, new Vector3f(new_c.target.center));
            }
            
            Block.render(renderer, (byte)2, new Vector3f(new_c.target.center));
        }
        mod_vel.div(timer.delta);
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

        for (int i = 3; i < level.layerNum; ++i) {
            Vector3f mod_pos = position.div(i + 1, new Vector3f());
            Vector3f mod_vel =  velocity.div(i + 1, new Vector3f());
            collideWithLayer(level.getLayer(i), timer, mod_pos, mod_vel);
            velocity = mod_vel.mul(i + 1);

            if (Math.abs(mod_vel.x) < Math.abs(velocity.x)) {
                velocity.x = mod_vel.x;
            }
            if (Math.abs(mod_vel.y) < Math.abs(velocity.y)) {
                velocity.y = mod_vel.y;
            }
        }
        
        position.add(velocity.mul(timer.delta, new Vector3f()));
    }

    public static void setRenderer(Renderer renderer) {
        Mob.renderer = renderer;
    }
}
