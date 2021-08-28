package com.shich.game.states;

import com.shich.game.util.Input;

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
            if (input.isKeyPressed(input.EXIT)) {
                gsm.addState(new PauseState(gsm));
            }
        }
        
    }

    public abstract void input(Input input);
}
