package com.shich.states;

import com.shich.entities.Button;
import com.shich.entities.Entity;
import com.shich.entities.bounds.AABB;
import com.shich.entities.render.Renderer;
import com.shich.util.Input;
import com.shich.util.Timer;

import org.joml.Vector3f;

public class MenuState extends GameState {

    Entity title;

    Button enterPlayState;
    Button enterCreateState;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        
        title = new Entity(new AABB(0, 0.6f, 1.4f, 0.6f), "menu/title.png", true);

        enterPlayState = new Button(new AABB(0, -0.1f, 0.8f, 0.3f), "menu/playButton.png", true) {
            @Override
            public void released() {
                PlayState ps = new PlayState(MenuState.this.gsm);
                ps.loadLevel("level-0");
                MenuState.this.gsm.addState(ps);
            }
        };

        enterCreateState = new Button(new AABB(0, -0.5f, 0.8f, 0.3f), "menu/levelEditorButton.png", true) {
            @Override
            public void released() {
                CreateState cs = new CreateState(MenuState.this.gsm);
                MenuState.this.gsm.addState(cs);
            }
        };
    }

    @Override
    public void focus() {
        gsm.camera.setScale(128);
        gsm.camera.setOffset(new Vector3f(0, 0, 0));
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
