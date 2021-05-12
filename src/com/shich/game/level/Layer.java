package com.shich.game.level;

import java.awt.Graphics;

public class Layer {
    public int width, height;
    protected Block[][] tiles;

    public Layer(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Block[width][height];
    }

    public void set(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y] = new Block(x, y, 0);
        }
    }

    public void remove(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y] = null;
        }
    }

    public Block get(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[x][y];
        }
        return null;
    }

    public void render(Graphics g, int camX, int camY) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (tiles[x][y] != null) {
                    tiles[x][y].render(g, camX, camY);
                }
            }
        }
        // g.drawRect(camX, camY - (height - 1) * 32, width * 32, height * 32);
    }

    public Block collision(int x, int y) {
        if (0 <= x && x < width && 0 <= y && y < height) {
            return tiles[x][y];
        }
        return null;
    }

    public boolean checkCollide(double x, double y, double width, double height) {
        for (int tx = (int) Math.floor(x); tx < (int) Math.ceil(x + width); ++tx) {
            for (int ty = (int) Math.floor(y); ty < (int) Math.ceil(y + height); ++ty) {
                if (collision(tx, ty) != null) {
                    return true;
                }
            }
        }
        return false;
    }
}