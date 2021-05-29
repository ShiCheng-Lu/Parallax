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

    public Jump(float jump_height, float jump_dist) {
        this.can_jump = false;
        this.jump_height = jump_height;
        this.jump_dist = jump_dist;

        
    }


    public void input(Input input) {
        if (can_jump) {
            if (input.isKeyPressed(KEYS.JUMP)) {
                move.vel.y = jump_vel;
            }
        }
    }

    public void update(Timer timer) {

    }

    public void calc_gravity(float x_vel) {
        gravity = (8 * jump_height * x_vel * x_vel) / (jump_dist * jump_dist);
        jump_vel = jump_height *4 * x_vel / jump_dist;
    }
}
