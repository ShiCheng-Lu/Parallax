package com.shich.game.states;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.shich.game.entities.Player;
import com.shich.game.level.Block;
import com.shich.game.level.Layer;
import com.shich.game.level.Level;
import com.shich.game.util.KeyHandler;
import com.shich.game.util.MouseHandler;

public class CreateState extends GameState {

    private Player player;
    private Level level;
    private LayerInputs[] layerInputs;
    private Button saveButton, loadButton;

    private class LayerInputs {
        private Layer layer;
        private int width, height;
        public Rectangle display, outDisplay;
        private Button[][] inputs;

        public LayerInputs(Layer layerBase, Rectangle display, Rectangle outDisplay) {
            this.width = layerBase.width;
            this.height = layerBase.height;
            this.inputs = new Button[width][height];
            this.layer = layerBase;
            this.outDisplay = outDisplay;

            int xStart = display.x;
            int yStart = display.y;

            int xSize = display.width / width;
            int ySize = display.height / height;

            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    inputs[x][y] = new Button(new Rectangle(xStart + xSize * x, yStart + ySize * y, xSize, ySize));
                    if (layer.get(x, y).type != Block.AIR) {
                        inputs[x][y].toggle = true;
                    }
                }
            }
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
                    char type;
                    if (inputs[x][y].toggle) {
                        type = Block.SOLID;
                    } else {
                        type = Block.AIR;
                    }
                    layer.set(x, width - y - 1, type);
                }
            }
        }

        public void render(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    inputs[x][y].render(g);

                    if (inputs[x][y].toggle) {
                        int blockWidth = (layer.movementMod * 2 - 1) * 32;

                        Rectangle r = new Rectangle(700 + 32 * (layer.movementMod * (x - 1) + 1),
                                32 * (layer.movementMod * (y - 1) + 1), blockWidth, blockWidth);

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

        Rectangle outDisplay = new Rectangle(700, 0, 25 * 32, 25 * 32);

        layerInputs = new LayerInputs[4];
        layerInputs[0] = new LayerInputs(level.layers.get(0), new Rectangle(700, 0, 25 * 32, 25 * 32), outDisplay);
        layerInputs[1] = new LayerInputs(level.layers.get(1), new Rectangle(20, 20, 13 * 32, 13 * 32), outDisplay);
        layerInputs[2] = new LayerInputs(level.layers.get(2), new Rectangle(20, 480, 9 * 32, 9 * 32), outDisplay);
        layerInputs[3] = new LayerInputs(level.layers.get(3), new Rectangle(350, 480, 7 * 32, 7 * 32), outDisplay);
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
            level.save("temp");
            saveButton.toggle = false;
        }
    }

    @Override
    public void render(Graphics g) {
        player.drawHUD(g);
        // relatives renders:

        player.render(g, 700, 768);

        for (LayerInputs layer : layerInputs) {
            layer.render(g);
        }
        saveButton.render(g);
    }
}
