package com.shich.game.level;

import java.awt.Graphics;
import java.util.ArrayList;

import com.shich.game.collision.AABB;
import com.shich.game.entities.Entity;

public class Layer {
    protected Level level;
    public int width, height, movementMod;
    protected byte[][] tiles;
    protected AABB[][] bounds;

    protected ArrayList<Entity> assets = new ArrayList<Entity>();

    public Layer(Level level, int width, int height, int movementMod) {
        this.level = level;
        this.width = width;
        this.height = height;
        this.movementMod = movementMod;

        tiles = new byte[width][height];
        bounds = new AABB[width][height];
    }

    public void set(int x, int y, byte type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y] = type;
        }
    }

    public byte get(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[x][y];
        }
        return 0;
    }

    public void render(Graphics g, int xOffset, int yOffset) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                Block.render(tiles[x][y], x, y);
            }
        }

        for (Entity e : assets) {
            e.render();
        }
        // g.drawRect(camX, camY - (height - 1) * 32, width * 32, height * 32);
    }
}