package com.shich.game.states;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.ImageIcon;

import com.shich.game.util.KeyHandler;
import com.shich.game.util.MouseHandler;

public abstract class GameState {

    public class Button {
        protected Shape shape;
        protected boolean clicked;
        public boolean toggle;
        public int x, y;
        public Image img;

        public Button(Shape shape) {
            this.shape = shape;
            clicked = false;
            
        }

        public void loadImage(String file) {
            ImageIcon ii = new ImageIcon(file);
            img = ii.getImage();
        }

        public void input(KeyHandler key, MouseHandler mouse) {
            if (shape.contains(mouse.x, mouse.y) && mouse.left.clicked()) {
                clicked = true;
                toggle = !toggle;
            }
        }

        public boolean clicked() {
            if (clicked) {
                clicked = false;
                return true;
            }
            return false;
        }

        public void render(Graphics g) {
            g.drawImage(img, x, y, null);
            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(shape);
            if (toggle) {
                g2d.fill(shape);
            }
        }
    }

    protected GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void input(KeyHandler key, MouseHandler mouse);

    public abstract void update();

    public abstract void render(Graphics g);

}
