package com.shich.game.entities;

import java.util.ArrayList;

import com.shich.game.collision.AABB;
import com.shich.game.level.Layer;
import com.shich.game.level.Level;
import com.shich.game.render.Renderer;
import com.shich.game.util.Timer;

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

    public static Renderer renderer;

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

        ArrayList<AABB> targets = new ArrayList<>();

        velocity.mul(timer.delta);

        for (int i = 0; i < level.layerNum; ++i) {
            Vector3f mod_pos = position.div(i + 1, new Vector3f());
            Layer layer = level.getLayer(i);

            for (int x = (int) mod_pos.x - 2; x <= (int) mod_pos.x + 2; ++x) {
                for (int y = (int) mod_pos.y - 2; y <= (int) mod_pos.y + 2; ++y) {
    
                    byte type = layer.get(x, y);
                    if (type == (byte) 1 || (type == (byte) 2 && mod_pos.y >= y + 1)) {
                        targets.add(layer.getBoundingBox(x, y));
                    }
                }
            }
        }

        bounding_box.resolveCollision(velocity, targets);
        
        position.add(velocity);
        velocity.div(timer.delta);
    }
}
