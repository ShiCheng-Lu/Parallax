package com.shich.game.states;

import java.awt.Graphics;

import com.shich.game.entities.Entity;
import com.shich.game.util.KeyHandler;
import com.shich.game.util.MouseHandler;

public class PauseState extends GameState {

    static private Button pauseButton;

    public PauseState(GameStateManager gsm) {
        super(gsm);

        pauseButton = new Button("src/com/shich/game/graphics/pauseButton.png", 640, 360);
    }

    private class Button extends Entity {

        public Button(String image, int x, int y) {
            super(x, y);
            loadImage(image);
        }
    }

    @Override
    public void input(KeyHandler key, MouseHandler mouse) {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        
    }

}
