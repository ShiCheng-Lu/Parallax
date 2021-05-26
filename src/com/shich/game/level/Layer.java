package com.shich.game.level;

import java.util.ArrayList;

import com.shich.game.collision.AABB;
import com.shich.game.entities.Entity;
import com.shich.game.render.Renderer;

import org.joml.Vector3f;

public class Layer {
    protected Level level;
    public int width, height;
    protected byte[][] tiles;
    protected AABB[][] bounds;

    public int scale;

    protected ArrayList<Entity> assets = new ArrayList<Entity>();

    public Layer(Level level, int width, int height, int scale) {
        this.level = level;
        this.width = width;
        this.height = height;
        this.scale = scale;

        tiles = new byte[width][height];
        bounds = new AABB[width][height];
    }

    public void set(int x, int y, byte type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y] = type;
            bounds[x][y] = new AABB(x * scale, y * scale, scale * 2 - 1, scale * 2 - 1);
        }
    }

    public byte get(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[x][y];
        }
        return 0;
    }

    public void render(Renderer renderer, Vector3f offset) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (tiles[x][y] != 0) {
                    Block.render(renderer, tiles[x][y], offset.add(x, y, 0, new Vector3f()));
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