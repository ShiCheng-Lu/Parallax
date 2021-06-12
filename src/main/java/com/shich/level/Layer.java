package com.shich.level;

import java.util.ArrayList;

import com.shich.entities.Entity;
import com.shich.entities.bounds.AABB;
import com.shich.entities.render.Renderer;

import org.joml.Matrix4f;
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

    public Layer(Level level) {
        this.level = level;
    }

    public String save() {
        StringBuilder result = new StringBuilder();

        result.append(width + " ");
        result.append(height + " ");
        result.append(scale + " ");

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                result.append(get(x, y) + '0');
            }
        }
        
        return result.toString();
    }

    public void load(String layer_data) {
        String[] data = layer_data.split(" ");
        width = Integer.parseInt(data[0]);
        height = Integer.parseInt(data[1]);
        scale = Integer.parseInt(data[2]);

        tiles = new byte[width][height];
        bounds = new AABB[width][height];

        int idx = 0;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                set(x, y, (byte) (data[3].charAt(idx) - '0'));

                if (data[3].charAt(idx) - '0' == 8) {
                    level.win_bounds = getBoundingBox(x, y);
                }
                
                ++idx;
            }
        }

    }

    public void set(int x, int y, byte type) {
        tiles[x][y] = type;
        bounds[x][y] = new AABB(x * scale, y * scale, scale * 2 - 1, scale * 2 - 1);
    }

    public void setSafe(int x, int y, byte type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            set(x, y, type);
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
                    Block.render(renderer, tiles[x][y], new Matrix4f().translate(offset).translate(x, y, 0));
                }
            }
        }
    }

    public void renderBounds(Renderer renderer) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (tiles[x][y] != 0) {
                    Block.render(renderer, tiles[x][y], new Matrix4f().translate(x * scale, y * scale, 0).scale(scale * 2 - 1));
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