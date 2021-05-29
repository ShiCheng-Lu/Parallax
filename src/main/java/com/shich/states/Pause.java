package com.shich.states;

import com.shich.util.Input;
import com.shich.util.KEYS;

public abstract class Pause {
    
    public GameStateManager gsm;

    public Pause(GameStateManager gsm) {
        this.gsm = gsm;
    }


    public class CanPause extends Pause {

        public CanPause(GameStateManager gsm) {
            super(gsm);
        }

        @Override
        public void input(Input input) {
            if (input.isKeyPressed(KEYS.EXIT)) {
                gsm.addState(new PauseState(gsm));
            }
        }
    }

    public abstract void input(Input input);
}
