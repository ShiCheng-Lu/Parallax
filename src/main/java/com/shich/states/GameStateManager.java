package com.shich.states;

import java.util.ArrayList;

import com.shich.entities.render.Renderer;
import com.shich.util.Camera;
import com.shich.util.Input;
import com.shich.util.Timer;

public class GameStateManager {

    Camera camera;
    /*
     * starts / end / pause the game
     */
    public ArrayList<GameState> states = new ArrayList<GameState>();

    public GameStateManager(Camera camera) {
        states.add(new MenuState(this));
        this.camera = camera;
    }

    public void update(Timer timer) {
        if (!states.isEmpty()) states.get(0).update(timer);
    }

    public void input(Input input) {
        if (!states.isEmpty()) {
            states.get(0).input(input);
            
            // if (input.isKeyReleased(KEYS.EXIT)) {
            //     remove(0);
            //     camera.setOffset(new Vector3f());
            //     camera.setPosition(new Vector3f());
            // }
        }
    }

    public void render(Renderer renderer) {
        if (!states.isEmpty()) states.get(0).render(renderer);
    }

    public void addState(GameState gs) {
        states.add(0, gs);
        gs.focus();
    }

    public void remove(int idx) {
        states.remove(idx);
        if (!states.isEmpty()) {
            states.get(0).focus();
        }
    }
}
