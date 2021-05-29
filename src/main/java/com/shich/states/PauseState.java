package com.shich.states;

import com.shich.entities.render.Renderer;
import com.shich.util.Input;
import com.shich.util.Timer;

public class PauseState extends GameState {

    // static private Button pauseButton;

    public PauseState(GameStateManager gsm) {
        super(gsm);

        // pauseButton = new Button("src/com/shich/game/graphics/pauseButton.png", 640, 360);
    }

    @Override
    public void input(Input input) {

    }

    @Override
    public void update(Timer timer) {

    }

    @Override
    public void render(Renderer renderer) {
        
    }
}
