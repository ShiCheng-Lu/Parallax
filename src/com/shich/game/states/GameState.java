package com.shich.game.states;

import com.shich.game.util.Input;

public abstract class GameState {
    
    protected GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void input(Input input);

    public abstract void update(double deltaTime);

    public abstract void render();
    
}
