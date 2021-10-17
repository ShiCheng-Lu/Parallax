package com.shich.game.states;

import java.util.ArrayList;

import com.shich.game.entities.render.Renderer;
import com.shich.game.util.Camera;
import com.shich.game.util.Input;
import com.shich.game.util.Timer;

import org.joml.Vector3f;

public class GameStateManager {
    /*
     * starts / end / pause the game
     */

    Camera camera;
    public ArrayList<GameState> states = new ArrayList<GameState>();

    public GameStateManager(Camera camera) {
        states.add(new MenuState(this));
        this.camera = camera;
    }

    public void update(Timer timer) {
        if (!states.isEmpty()) {
            states.get(0).update(timer);
        }
    }

    public void input(Input input) {
        states.get(0).input(input);

        if (input.isKeyReleased(input.EXIT)) {
            if (!states.isEmpty())
                remove(0);
            setCameraOffset(new Vector3f());
            setCameraPosition(new Vector3f());
        }
    }

    public void render(Renderer renderer) {
        if (!states.isEmpty())
            states.get(0).render(renderer);
    }

    public void addState(GameState gs) {
        states.add(0, gs);
    }

    public void remove(int idx) {
        states.remove(idx);
    }

    public void setCameraPosition(Vector3f pos) {
        camera.setPosition(pos);
    }

    public void setCameraOffset(Vector3f pos) {
        camera.setOffset(pos);
    }
}
