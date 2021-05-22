package com.shich.game.entities;

import com.shich.game.collision.*;
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

        position = new Vector3f();
        velocity = new Vector3f();
        accerlation = new Vector3f();
    }

    public void update(Timer timer) {
        super.update(timer);
        position.add(velocity.mul(timer.delta));

        // position.add(velocity.mul(deltaTime));

        // int lowerX = (int) Math.floor(bounding_box.getMinX());
        // int upperX = (int) Math.ceil(bounding_box.getMaxX());
        // int lowerY = (int) Math.floor(bounding_box.getMinY());
        // int upperY = (int) Math.ceil(bounding_box.getMaxY());

        // for (int x = lowerX; x <= upperX; ++x) {
        //     for (int y = lowerY; x <= upperY; ++y) {
                
        //         byte type = level.getBlockType(x, y);
        //         AABB bounds = level.getBoundingBox(x, y);

        //         Collision collision = bounding_box.getCollision(bounds);

        //         if (collision.intersects) {
        //             bounding_box.correctPosition(bounds, collision);
        //         }
        //     }
        // }
    }
}
