package com.shich.game.states;

import com.shich.game.render.Renderer;
import com.shich.game.util.Input;
import com.shich.game.util.Timer;

public abstract class GameState {
    
    protected GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void input(Input input);

    public abstract void update(Timer timer);

    public abstract void render(Renderer renderer);
    
}
