package com.shich.entities.move;

import com.shich.util.Input;
import com.shich.util.KEYS;
import com.shich.util.Timer;

public class Dash {
    
    private Movement move;

    private boolean can_dash;
    private boolean in_dash;
    private float time;
    private float dash_time;
    private float cooldown;
    private float dash_vel;

    public Dash() {

    }

    public void input(Input input) {
        // cannot dash, no input to take
        if (!can_dash) {
            return;
        }

        if (input.isKeyPressed(KEYS.DASH)) {
            time = 0;
            can_dash = false;
            in_dash = true;
        }
    }

    public void update(Timer timer) {
        // can dash, no update for dash
        if (can_dash) {
            return;
        }
        // cannot dash
        if (in_dash) {
            // update dash
            move.vel.y = 0;
            move.vel.x = dash_vel;
            
            if (time >= dash_time) {
                in_dash = false;
            }
        } else if (time >= dash_time + cooldown) {
            can_dash = true;
        }
    }








}
