package com.shich.game.level;

import java.util.ArrayList;

import com.shich.game.entities.Entity;
import com.shich.game.entities.bounds.AABB;
import com.shich.game.entities.render.Model;
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
            blocks[x][y] = new Block(new AABB(x * scale, y * scale, scale * 2 - 1, scale * 2 - 1), type);
        }
    }

    public Block get(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return blocks[x][y];
        }
        return 0;
    }

    public void render(Renderer renderer, Vector3f offset) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (blocks[x][y].getType() != 0) { // is not empty
                    blocks[x][y].render(renderer, offset.add(x, y, 0, new Vector3f()));
                }
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

    public AABB getBoundingBox(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return bounds[x][y];
        }
        return null;
    }

    public void addAsset(Entity e) {
        assets.add(e);
    }
}