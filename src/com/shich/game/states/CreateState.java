package com.shich.game.states;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

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
    private Button saveButton;

    private class TileButton extends Button {

        public char state;

        public TileButton(Shape shape) {
            super(shape);
            state = Block.AIR;
        }

        public void input(KeyHandler key, MouseHandler mouse) {
            if (shape.contains(mouse.x, mouse.y)) {
                if (mouse.left.clicked()) {
                    if (state == '3') {
                        state = 'a';
                    } else if (state == Block.AIR) {
                        state = Block.SOLID;
                    } else {
                        state++;
                    }
                }
                if (mouse.right.clicked()) {
                    state = 'a';
                }
                loadImage("block/block-" + state + ".png");
            }
        }
            
        public void renderAbsolute(Graphics g, int xD, int yD) {
            g.drawImage(img, xD, yD, null);
        }
    }

    private class LayerInputs {
        private Layer layer;
        private int width, height;
        private TileButton[][] inputs;

        public LayerInputs(Layer layerBase, Rectangle display, Rectangle outDisplay) {
            this.width = layerBase.width;
            this.height = layerBase.height;
            this.inputs = new TileButton[width][height];
            this.layer = layerBase;

            int xStart = display.x;
            int yStart = display.y;

            int xSize = display.width / width;
            int ySize = display.height / height;

            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    TileButton tile = new TileButton(new Rectangle(xStart + xSize * x, yStart + ySize * y, xSize, ySize));
                    tile.loadImage("block-..png");
                    tile.state = layer.get(x, y).type;
                    inputs[x][y] = tile;
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
                    layer.set(x, width - y - 1, inputs[x][y].state);
                }
            }
        }

        public void render(Graphics g) {
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    inputs[x][y].render(g);

                    if (inputs[x][y].state != 'a') {
                        int blockWidth = layer.movementMod * 2 - 1;
                        int startX = 700 + 32 * (layer.movementMod * (x - 1) + 1);
                        int startY = 32 * (layer.movementMod * (y - 1) + 1);

                        if (inputs[x][y].state == Block.SOLID) {
                            
                            for (int dx = 0; dx < blockWidth; dx ++) {
                                for (int dy = 0; dy < blockWidth; dy ++) {
                                    inputs[x][y].renderAbsolute(g, startX + dx * 32, startY + dy * 32);
                                }
                            }
                        }
                        if (inputs[x][y].state == Block.SEMISOLID) {
                            for (int dx = 0; dx < blockWidth; dx ++) {
                                inputs[x][y].renderAbsolute(g, startX + dx * 32, startY);
                            }
                        }
                    }
                }
            }
        }
    }

    public CreateState(GameStateManager gsm) {
        super(gsm);

        level = new Level(this);
        level.load("empty");
        player = new Player("bob", 0, 0, level);

        saveButton = new Button(new Rectangle(500, 50, 150, 50));
        saveButton.loadImage("menu/saveButton.png");

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
        for (LayerInputs layerInput : layerInputs) {
            layerInput.input(key, mouse);
        }
        saveButton.input(key, mouse);
    }

    @Override
    public void update() {
        player.update();
        for (LayerInputs layerInput : layerInputs) {
            layerInput.update();
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

        for (LayerInputs layerInput : layerInputs) {
            layerInput.render(g);
        }
        saveButton.render(g);
    }
}
