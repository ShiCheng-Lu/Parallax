package com.shich.game.states;

import java.util.ArrayList;

import com.shich.game.render.Renderer;
import com.shich.game.util.Input;

public class GameStateManager {
    /*
     * starts / end / pause the game
     */
    private ArrayList<GameState> states = new ArrayList<GameState>();

    public GameStateManager() {
        states.add(new MenuState(this));
    }

    public void update(double deltaTime) {
        states.get(0).update(deltaTime);
    }

    public void input(Input input) {
        states.get(0).input(input);
    }

    public void render(Renderer renderer) {
        for (GameState state : states) {
            state.render(renderer);
        }
    }

    public void addState(GameState gs) {
        states.add(0, gs);
    }

    public void remove(int idx) {
        states.remove(idx);
    }
}
