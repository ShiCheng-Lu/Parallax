package com.shich.game.states;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.shich.game.util.KeyHandler;
import com.shich.game.util.MouseHandler;

public class MenuState extends GameState {

    Button title;

    Button enterPlayState;
    Button enterCreateState;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        
        title = new Button(new Rectangle(268, 100, 1000, 300));
        title.loadImage("src/com/shich/game/graphics/menu/title.png");

        enterPlayState = new Button(new Rectangle(518, 450, 500, 100));
        enterPlayState.loadImage("src/com/shich/game/graphics/menu/playButton.png");

        enterCreateState = new Button(new Rectangle(518, 550, 500, 100));
        enterCreateState.loadImage("src/com/shich/game/graphics/menu/levelEditorButton.png");
    }

    @Override
    public void input(KeyHandler key, MouseHandler mouse) {
        enterPlayState.input(key, mouse);
        enterCreateState.input(key, mouse);
    }

    @Override
    public void update() {
        if (enterPlayState.clicked()) {
            gsm.remove(0);
            gsm.addState(new PlayState(gsm));
        }
        if (enterCreateState.clicked()) {
            gsm.remove(0);
            gsm.addState(new CreateState(gsm));
        }
    }

    @Override
    public void render(Graphics g) {
        title.render(g, false);
        enterPlayState.render(g);
        enterCreateState.render(g);
    }
}
