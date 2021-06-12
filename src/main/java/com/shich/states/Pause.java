package com.shich.states;

import java.util.ArrayList;

import com.shich.entities.Button;
import com.shich.entities.Entity;
import com.shich.entities.bounds.AABB;
import com.shich.entities.render.Renderer;
import com.shich.util.Input;
import com.shich.util.KEYS;
import com.shich.util.Timer;

public class Pause {
    
    public boolean paused;
    public GameStateManager gsm;

    protected Entity dark;

    protected ArrayList<Entity> assets;

    public Pause(GameStateManager gsm) {
        this.gsm = gsm;
        paused = false;
        assets = new ArrayList<Entity>();

        dark = new Entity(new AABB(0, 0, 2, 2), "menu/dark.png", true);

        // return button
        assets.add(new Button(new AABB(0, 0.5f, 0.45f, 0.8f), "menu/pauseButton.png", true) {
            @Override
            public void released() {
                Pause.this.gsm.remove(0);
            }
        });

        // resume button
        assets.add(new Button(new AABB(0, -0.5f, 0.3f, 0.3f), "menu/pauseButton.png", true) {
            @Override
            public void released() {
                Pause.this.paused = false;
            }
        });
    }

    public void addAsset(Entity e) {
        assets.add(e);
    }

    public void input(Input input) {
        if (input.isKeyPressed(KEYS.EXIT)) {
            paused = !paused;
        }
        if (!paused) return;

        for (Entity asset : assets) {
            asset.input(input);
        }
    }

    public void update(Timer timer) {
        if (!paused) return;

        for (Entity asset : assets) {
            asset.update(timer);
        }
    }
    
    public void render(Renderer renderer) {
        if (!paused) return;

        // mask screen
        dark.render(renderer);
        // render on top
        for (Entity asset : assets) {
            asset.render(renderer);
        }
    }
}
