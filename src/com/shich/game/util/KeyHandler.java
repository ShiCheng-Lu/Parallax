package com.shich.game.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

import com.shich.game.GamePanel;

public class KeyHandler extends KeyAdapter {

    public static Hashtable<Integer, Key> keys = new Hashtable<Integer, Key>();

    public Key up = new Key(KeyEvent.VK_W);
    public Key down = new Key(KeyEvent.VK_S);
    public Key left = new Key(KeyEvent.VK_A);
    public Key right = new Key(KeyEvent.VK_D);
    public Key action = new Key(KeyEvent.VK_SPACE);
    public Key pause = new Key(KeyEvent.VK_ESCAPE);
    public Key dash = new Key(KeyEvent.VK_L);
    public Key jump = new Key(KeyEvent.VK_K);

    public KeyHandler(GamePanel game) {
        game.addKeyListener(this);
    }

    public class Key {

        protected boolean pressed, clicked;
        protected int downCount = 0, releaseCount = 0;
        protected int keyConst;

        public Key(int keyConst) {
            keys.put(keyConst, this);
            this.keyConst = keyConst;
        }

        public void changeKeyBind(int newKeyConst) {
            keys.remove(keyConst);
            keys.put(newKeyConst, this);
            this.keyConst = newKeyConst;
        }

        public void toggle(boolean pressed) {
            this.pressed = pressed;

            if (pressed) {
                if (downCount == releaseCount) {
                    clicked = true;
                    downCount++;
                }
            } else {
                releaseCount++;
            }
        }

        public boolean clicked() {
            if (clicked) {
                clicked = false;
                return true;
            } else {
                return false;
            }
        }

        public boolean pressed() {
            return pressed;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        keys.get(key).toggle(true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        keys.get(key).toggle(false);
    }
}
