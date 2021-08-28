package com.shich.game.states;

import com.shich.game.entities.render.Renderer;
import com.shich.game.util.Input;
import com.shich.game.util.Timer;

public class GameState {
    
    protected GameStateManager gsm;
    protected Pause pause;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public void input(Input input) {
        if (pause != null) pause.input(input);
    }

    public void update(Timer timer) {}

    public void render(Renderer renderer) {}
    
}
