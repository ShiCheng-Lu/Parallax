package com.shich.level;

import java.util.ArrayList;
import java.util.Hashtable;

import com.shich.entities.Entity;
import com.shich.entities.bounds.AABB;
import com.shich.entities.render.Renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Block extends Entity {

    private static Hashtable<Byte, Block> block_set = new Hashtable<Byte, Block>();
    private static ArrayList<Byte> id_list = new ArrayList<Byte>();
    private byte id;

    public Block(Byte id, String texture_file) {
        super(new AABB(0, 0, 1, 1), texture_file);
        this.id = id;
        block_set.put(id, this);
    }

    public byte getType() {
        return id;
    }

    public static void render(Renderer renderer, byte id, Matrix4f trans) {
        Block block = block_set.get(id);
        if (block != null) {
            block.render(renderer, trans);
        }
    }

    public void render(Renderer renderer, Matrix4f trans) {
        renderer.render(trans, model, texture);
    }

    public static Block getBlock(byte id) {
        return block_set.get(id);
    }

    public static void init() {
        byte[] types = new byte[] {0, 1, 2, 8, 9};
        for (byte t : types) {
            id_list.add(t);
            new Block(t, "block/block-" + t + ".png");
        }
    }

    public static ArrayList<Byte> id_list() {
        return id_list;
    }
 } 
