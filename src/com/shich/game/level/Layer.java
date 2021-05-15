package com.shich.game.level;

import java.awt.Graphics;

public class Layer {
    public int width, height, movementMod;
    protected Block[][] tiles;

    public Layer(int width, int height, int movementMod) {
        this.width = width;
        this.height = height;
        this.movementMod = movementMod;

        tiles = new Block[width][height];
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                tiles[x][y] = new Block(x, y, Block.AIR);
            }
        }
    }

    public void set(int x, int y, char type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y].setType(type);
        }
    }

    // remove not used
    // public void remove(int x, int y) {
    //     if (x >= 0 && x < width && y >= 0 && y < height) {
    //         tiles[x][y] = null;
    //     }
    // }

    public Block get(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[x][y];
        }
        return null;
    }

    public void render(Graphics g, int xOffset, int yOffset) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (tiles[x][y] != null) {
                    tiles[x][y].render(g, xOffset, yOffset);
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
        x /= movementMod;
        y /= movementMod;
        for (int tx = (int) Math.floor(x); tx < (int) Math.ceil(x + width); ++tx) {
            for (int ty = (int) Math.floor(y); ty < (int) Math.ceil(y + height); ++ty) {
                if (collision(tx, ty).getType() == Block.SOLID) {
                    return true;
                }
            }
        }
        return false;
    }
}