package com.shich.game.level;

import java.util.ArrayList;

import com.shich.game.entities.Block;
import com.shich.game.entities.Entity;
import com.shich.game.entities.bounds.AABB;
import com.shich.game.entities.render.Renderer;

import org.joml.Vector3f;

public class Layer {
    protected Level level;
    public int width, height;
    protected Block[][] blocks;

    public int scale;

    protected ArrayList<Entity> assets = new ArrayList<Entity>();

    public Layer(Level level, int width, int height, int scale) {
        this.level = level;
        this.width = width;
        this.height = height;
        this.scale = scale;

        blocks = new Block[width][height];
    }

    public void set(int x, int y, byte type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            blocks[x][y] = new Block(type, new AABB(x * scale, y * scale, scale * 2 - 1, scale * 2 - 1));
        }
    }

    public Block get(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return blocks[x][y];
        }
        return null;
    }

    public void render(Renderer renderer, Vector3f offset) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                blocks[x][y].render(renderer);
            }
        }
    }

    public void renderAsset(Renderer renderer, Vector3f offset) {
        for (Entity e : assets) {
            e.getPos().add(offset);
            e.render(renderer);
            e.getPos().sub(offset);
        }
    }

    public void addAsset(Entity e) {
        assets.add(e);
    }
}