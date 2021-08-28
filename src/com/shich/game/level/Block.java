package com.shich.game.level;

import java.util.Hashtable;

import com.shich.game.entities.Entity;
import com.shich.game.entities.bounds.AABB;
import com.shich.game.entities.render.Model;
import com.shich.game.entities.render.Renderer;
import com.shich.game.entities.render.Texture;

import org.joml.Vector3f;

public class Block extends Entity {

    private static Model block_model = new Model(new Vector3f(0.5f, 0.5f, 0));
    private static Hashtable<Byte, Texture> block_set = new Hashtable<Byte, Texture>();
    private byte id;

    public Block(AABB bounds, byte type) {
        super(bounds, block_model, block_set.get(type));
        this.id = type;
    }

    public byte getType() {
        return id;
    }

    public void addBlockType(Byte type, String texture) {
        block_set.put(type, new Texture(texture));
    }

    public static void render(Renderer renderer, byte id, Vector3f offset) {
        Block block = block_set.get(id);
        if (block != null) {
            block.render(renderer, offset);
        }

    }

    public void render(Renderer renderer, Vector3f offset) {
        renderer.render(model, offset, texture);
    }

    public static Block getBlock(byte id) {
        return block_set.get(id);
    }

    public static void init() {
        byte[] types = new byte[] { 0, 1, 2, 8, 9 };
        for (byte i : types) {
            new Block(i, "block/block-" + i + ".png");
        }
    }
}
