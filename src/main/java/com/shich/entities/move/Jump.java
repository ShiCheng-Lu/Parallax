package com.shich.entities.move;

import com.shich.util.Input;
import com.shich.util.KEYS;
import com.shich.util.Timer;

public class Jump {

    private Movement move;
    
    private boolean can_jump;
    private float jump_height;
    private float jump_dist;
    private float jump_vel;
    private float gravity;

    private boolean falling;

    public Jump(Movement move, float jump_height, float jump_dist) {
        this.move = move;

        this.can_jump = true;
        this.jump_height = jump_height;
        this.jump_dist = jump_dist;

        this.falling = false;
    }


    public void input(Input input) {
        if (can_jump) {
            if (input.isKeyPressed(KEYS.JUMP)) {
                move.vel.y = jump_vel;
                can_jump = false;
            }
        }
    }

    public void update(Timer timer) {
        if (!can_jump) {
            move.acc.y = -gravity;
        }
        if (move.pos.y < 0) {
            can_jump = true;
            move.pos.y = 0;
            move.vel.y = 0;
            move.acc.y = 0;
        }
    }

    public void calc_gravity(float x_vel) {
        gravity = (8 * jump_height * x_vel * x_vel) / (jump_dist * jump_dist);
        jump_vel = jump_height * 4 * x_vel / jump_dist;
    }


    public void setCanJump(boolean val) {
        can_jump = val;
    }
}
