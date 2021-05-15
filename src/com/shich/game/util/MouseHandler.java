package com.shich.game.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.shich.game.GamePanel;

public class MouseHandler extends MouseAdapter {

    public int x, y;
    public Button left = new Button(MouseEvent.BUTTON1);
    public Button middle = new Button(MouseEvent.BUTTON2);
    public Button right = new Button(MouseEvent.BUTTON3);

    public MouseHandler(GamePanel game) {
        game.addMouseListener(this);
        game.addMouseMotionListener(this);
    }

    public class Button {
        public boolean pressed, clicked;
        public int buttonNum;

        public Button(int buttonNum) {
            this.buttonNum = buttonNum;
        }

        public void toggle(Boolean pressed) {
            this.pressed = pressed;
        }

        public boolean clicked() {
            if (clicked) {
                clicked = false;
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int button = e.getButton();
        if (button == 1) {
            left.clicked = true;
        } else if (button == 2) {
            middle.clicked = true;
        } else if (button == 3) {
            right.clicked = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        if (button == 1) {
            left.toggle(true);
        } else if (button == 2) {
            middle.toggle(true);
        } else if (button == 3) {
            right.toggle(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        if (button == 1) {
            left.toggle(false);
        } else if (button == 2) {
            middle.toggle(false);
        } else if (button == 3) {
            right.toggle(false);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public void reset_clicks() {
        left.clicked();
        middle.clicked();
        right.clicked();
    }
}
