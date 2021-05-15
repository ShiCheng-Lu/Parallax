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
        public Image img, imgClicked;

        /**
         * Create a button that with the shape
         * @param shape
         */
        public Button(Shape shape) {
            this.shape = shape;
            clicked = false;
            x = shape.getBounds().x;
            y = shape.getBounds().y;
        }

        /**
         * load an image as the button
         * @param file the path to a file
         */
        public void loadImage(String file, String fileClicked) {
            ImageIcon ii = new ImageIcon(file);
            img = ii.getImage();
            ImageIcon iiClicked = new ImageIcon(fileClicked);
            imgClicked = iiClicked.getImage();
        }
        public void loadImage(String file) {
            loadImage(file, file);
        }

        /**
         * handles inputs from user
         * @param key key inputs
         * @param mouse mouse inputs
         */
        public void input(KeyHandler key, MouseHandler mouse) {
            if (shape.contains(mouse.x, mouse.y) && mouse.left.clicked()) {
                clicked = true;
                toggle = !toggle;
            }
        }

        /**
         * check if this button was clicked
         * @return true if the button was clicked
         */
        public boolean clicked() {
            if (clicked) {
                clicked = false;
                return true;
            }
            return false;
        }

        /**
         * display the button on screen
         * @param g graphic to display the button on
         * @param outline optional, if true, a outline of the button shape will be drawn
         */
        public void render(Graphics g, boolean outline) {
            g.drawImage(img, x, y, null);
            Graphics2D g2d = (Graphics2D) g;
            if (outline) {
                g2d.draw(shape);
            }
            if (toggle) {
                g2d.fill(shape);
            }
        }
        public void render(Graphics g) {
            render(g, true);
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
