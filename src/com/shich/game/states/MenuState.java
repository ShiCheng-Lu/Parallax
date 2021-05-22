package com.shich.game.states;

import com.shich.game.render.Renderer;
import com.shich.game.util.Input;

public class MenuState extends GameState {

    Button title;

    Button enterPlayState;
    Button enterCreateState;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        
        // title = new Button(new Rectangle(268, 100, 1000, 300));
        // title.loadImage("menu/title.png");

        // enterPlayState = new Button(new Rectangle(518, 450, 500, 100));
        // enterPlayState.loadImage("menu/playButton.png");

        // enterCreateState = new Button(new Rectangle(518, 550, 500, 100));
        // enterCreateState.loadImage("menu/levelEditorButton.png");
    }

    @Override
    public void input(Input input) {
        enterPlayState.input(input);
        enterCreateState.input(input);
    }

    @Override
    public void update(double deltaTime) {
        if (enterPlayState.clicked()) {
            gsm.remove(0);
            PlayState ps = new PlayState(gsm);
            ps.loadLevel("selector");
            gsm.addState(ps);
        }
        if (enterCreateState.clicked()) {
            gsm.remove(0);
            gsm.addState(new CreateState(gsm));
        }
    }

    @Override
    public void render(Renderer renderer) {
        title.render(renderer);
        // enterPlayState.render(renderer, true);
        // enterCreateState.render(renderer, true);
    }
}
