package com.shich.game.level;

import java.awt.Graphics;
import java.util.ArrayList;

import com.shich.game.entities.Entity;
import com.shich.game.entities.Mob;

public class Layer {
    protected Level level;
    public int width, height, movementMod;
    protected Block[][] tiles;

    protected ArrayList<Entity> assets = new ArrayList<Entity>();

    public Layer(Level level, int width, int height, int movementMod) {
        this.level = level;
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
        return new Block(0, 0, Block.AIR);
    }

    public void render(Graphics g, int xOffset, int yOffset) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (tiles[x][y] != null) {
                    tiles[x][y].render(g, xOffset, yOffset);
                }
            }
        }

        for (Entity e : assets) {
            e.render(g, xOffset, yOffset);
        }
        // g.drawRect(camX, camY - (height - 1) * 32, width * 32, height * 32);
    }

    public Block collision(int x, int y) {
        if (0 <= x && x < width && 0 <= y && y < height) {
            return tiles[x][y];
        }
        return null;
    }

    public boolean checkCollide(Mob m) {
        double xNew = m.xNew / movementMod;
        double yNew = m.yNew / movementMod;
        // double x = m.x / movementMod;
        double y = m.y / movementMod;

        for (int tx = (int) Math.floor(xNew); tx < (int) Math.ceil(xNew + m.width); ++tx) {
            for (int ty = (int) Math.floor(yNew); ty < (int) Math.ceil(yNew + m.height); ++ty) {
                switch (collision(tx, ty).getType()) {
                    case Block.SOLID:
                        return true;
                    case Block.SEMISOLID:
                        if (y >= ty + 1) { // if the player starting position is above the block
                            return true;
                        }
                        break;
                    case Block.AIR:
                        break;
                    case Block.WIN:
                        level.loadNext();
                        break;
                    default:
                        break;
                }
            }
        }
        return false;
    }

    public void addAsset(double x, double y, String file) {
        Entity e = new Entity(x, y);
        e.setRenderSetting(1, 1, 0, 0);
        e.loadImage(file);
        assets.add(e);
    }
}