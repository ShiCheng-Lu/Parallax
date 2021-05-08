package com.shich.game.states;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.shich.game.entities.Player;
import com.shich.game.level.Level;
import com.shich.game.util.KeyHandler;
import com.shich.game.util.MouseHandler;

public class CreateState extends GameState {

    private Player player;
    private Level level;
    private LayerInputs[] layerInputs;
    private Button saveButton, loadButton;
    
    private class LayerInputs {
        private int width, height, layer;
        public Rectangle display, outDisplay;
        private Button[][] inputs;

        public LayerInputs(int width, int height, Rectangle display, int layer, Rectangle outDisplay) {
            this.width = width;
            this.height = height;
            this.inputs = new Button[width][height];
            this.layer = layer;
            this.outDisplay = outDisplay;

            int xStart = display.x;
            int yStart = display.y;

            int xSize = display.width / width;
            int ySize = display.height / height;

            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    inputs[x][y] = new Button(new Rectangle(xStart + xSize * x, yStart + ySize * y, xSize, ySize));
                }
            }
        }

        public LayerInputs(int size, int layer, Rectangle display, Rectangle outDisplay) {
            this(size, size, display, layer, outDisplay);
        }        

        public void input(KeyHandler key, MouseHandler mouse) {
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    inputs[x][y].input(key, mouse);
                }
            }
        }

        public void update() {
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    if (inputs[x][y].toggle) {
                        level.set(layer, x, width - y - 1);
                    } else {
                        level.remove(layer, x, width - y - 1);
                    }

                }
            }
        }

        public void render(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    inputs[x][y].render(g);

                    if (inputs[x][y].toggle) {
                        Rectangle r = new Rectangle(700 + (layer + 1) * x * 32 - 32 * layer, (layer + 1) * y * 32 - 32 * layer, (layer * 2 + 1) * 32, (layer * 2 + 1) * 32);

                        g2d.fill(outDisplay.intersection(r));
                    }
                }
            }
        }
    }

    public CreateState(GameStateManager gsm) {
        super(gsm);

        level = new Level();
        player = new Player("bob", 0, 0, level);
        
        saveButton = new Button(new Rectangle(500, 50, 150, 50));
        saveButton.loadImage("src/com/shich/game/graphics/menu/saveButton.png");
        saveButton.x = 500;
        saveButton.y = 50;

        Rectangle outDisplay = new Rectangle(700, 0, 25 * 32, 25 * 32);

        layerInputs = new LayerInputs[4];
        layerInputs[0] = new LayerInputs(25, 0, new Rectangle(700, 0, 25 * 32, 25 * 32), outDisplay);
        layerInputs[1] = new LayerInputs(13, 1, new Rectangle(20, 20, 13 * 32, 13 * 32), outDisplay);
        layerInputs[2] = new LayerInputs(9, 2, new Rectangle(20, 480, 9 * 32, 9 * 32), outDisplay);
        layerInputs[3] = new LayerInputs(7, 3, new Rectangle(350, 480, 7 * 32, 7 * 32), outDisplay);
    }

    @Override
    public void input(KeyHandler key, MouseHandler mouse) {
        player.input(key, mouse);
        for (LayerInputs layer : layerInputs) {
            layer.input(key, mouse);
        }
        saveButton.input(key, mouse);
    }

    @Override
    public void update() {
        player.update();
        for (LayerInputs layer : layerInputs) {
            layer.update();
        }
        if (saveButton.clicked()) {
            level.save("level" + 1);
            saveButton.toggle = false;
        }
    }

    @Override
    public void render(Graphics g) {
        player.drawHUD(g);
        // relatives renders:

        player.render(g, 700, 770);

        for (LayerInputs layer : layerInputs) {
            layer.render(g);
        }
        saveButton.render(g);
    }
}
