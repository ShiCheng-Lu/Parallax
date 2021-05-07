package com.shich.game.states;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.shich.game.util.KeyHandler;
import com.shich.game.util.MouseHandler;

public class MenuState extends GameState {

    Button enterPlayState;
    Button enterCreateState;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        //TODO Auto-generated constructor stub
        enterPlayState = new Button(new Rectangle(518, 200, 500, 100));
        enterCreateState = new Button(new Rectangle(518, 350, 500, 100));
    }

    @Override
    public void input(KeyHandler key, MouseHandler mouse) {
        // TODO Auto-generated method stub
        enterPlayState.input(key, mouse);
        enterCreateState.input(key, mouse);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        enterPlayState.render(g);
        enterCreateState.render(g);
    }
    
}
