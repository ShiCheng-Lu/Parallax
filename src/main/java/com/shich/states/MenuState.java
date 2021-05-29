package com.shich.states;

import com.shich.entities.Button;
import com.shich.entities.bounds.AABB;
import com.shich.entities.render.Renderer;
import com.shich.util.Input;
import com.shich.util.Timer;

public class MenuState extends GameState {

    Button title;

    Button enterPlayState;
    Button enterCreateState;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        
        title = new Button(new AABB(0, 5, 25, 7), "menu/title.png");

        enterPlayState = new Button(new AABB(0, -1, 12, 3), "menu/playButton.png") {
            @Override
            public void released() {
                PlayState ps = new PlayState(MenuState.this.gsm);
                ps.loadLevel("level-3");
                MenuState.this.gsm.addState(ps);
            }
        };

        enterCreateState = new Button(new AABB(0, -4, 12, 3), "menu/levelEditorButton.png") {
            @Override
            public void released() {
                // gsm.addState(new CreateState(gsm));
            }
        };
    }

    @Override
    public void input(Input input) {
        enterPlayState.input(input);
        enterCreateState.input(input);
    }

    @Override
    public void update(Timer timer) {
    }

    @Override
    public void render(Renderer renderer) {
        title.render(renderer);
        enterPlayState.render(renderer);
        enterCreateState.render(renderer);
    }
}
