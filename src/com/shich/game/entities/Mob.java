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

    public Mob(AABB bounds, Level level) {
        super(bounds);
        this.level = level;

        position = bounds.center;
        velocity = new Vector3f();
        accerlation = new Vector3f();
    }

    private void collideWithLayer(Layer layer, Timer timer) {
        layer.collisionMod.transformPosition(position);

        float closest_time = 1;
        Collision closest_collision = null;

        for (int x = (int) position.x - 2; x <= (int) position.x + 2; ++x) {
            for (int y = (int) position.y - 2; y <= (int) position.y + 2; ++y) {

                byte type = layer.get(x, y);
                if (type != (byte) 0) {
                    AABB bounds = layer.getBoundingBox(x, y);

                    Collision collision = bounds.getCollision(bounding_box, velocity.mul(timer.delta, new Vector3f()));

                    if (collision.intersects && collision.time < closest_time) {
                        closest_collision = collision;
                    }
                }
            }
        }
        layer.collisionModinverted.transformPosition(position);

        if (closest_collision != null) {
            Vector3f vel_dif = closest_collision.normal.mul(velocity).mul(1 - closest_collision.time);
            System.out.println(position);
            
            System.out.println(vel_dif);
            System.out.println(velocity);

            
            velocity.add(vel_dif);
        }
    }

    public void update(Timer timer) {
        for (int i = 0; i < level.layerNum; ++i) {
            collideWithLayer(level.getLayer(i), timer);
        }
        position.add(velocity.mul(timer.delta, new Vector3f()));
        velocity.mul(0.9f);
    }
}
