package com.shich.game.level;

import java.util.ArrayList;

import com.shich.game.collision.AABB;
import com.shich.game.entities.Entity;
import com.shich.game.render.Renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Layer {
    protected Level level;
    public int width, height;
    protected byte[][] tiles;
    protected AABB[][] bounds;

    public Matrix4f collisionMod, collisionModinverted;

    protected ArrayList<Entity> assets = new ArrayList<Entity>();

    public Layer(Level level, int width, int height, Matrix4f collisionMod) {
        this.level = level;
        this.width = width;
        this.height = height;
        this.collisionMod = collisionMod;
        collisionModinverted = collisionMod.invert(new Matrix4f());

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

    public void render(Renderer renderer, Vector3f offset) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (tiles[x][y] != 0) {
                    Block.render(renderer, tiles[x][y], offset.add(x, y, 0, new Vector3f()));
                }
            }
        }

        for (Entity e : assets) {
            e.render(renderer);
        }
        // g.drawRect(camX, camY - (height - 1) * 32, width * 32, height * 32);
    }

    public AABB getBoundingBox(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return bounds[x][y];
        }
        return null;
    }
}