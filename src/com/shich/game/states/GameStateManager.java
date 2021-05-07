package com.shich.game.states;

import java.util.ArrayList;
import java.awt.Graphics;

import com.shich.game.GamePanel;
import com.shich.game.util.KeyHandler;
import com.shich.game.util.MouseHandler;

public class GameStateManager {
    /*
     * starts / end / pause the game
     */
    private ArrayList<GameState> states = new ArrayList<GameState>();

    public GameStateManager(GamePanel game) {
        states.add(new MenuState(this));
    }

    public void update() {
        states.get(0).update();
    }

    public void input(KeyHandler key, MouseHandler mouse) {
        states.get(0).input(key, mouse);
    }

    public void render(Graphics g) {
        for (GameState state : states) {
            state.render(g);
        }
    }

    public void addState(GameState gs) {
        states.add(0, gs);
    }

    public void remove(int idx) {
        states.remove(idx);
    }
}
