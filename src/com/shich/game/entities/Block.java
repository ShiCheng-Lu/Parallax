package com.shich.game.entities;

import java.util.Hashtable;

import com.shich.game.entities.bounds.AABB;
import com.shich.game.entities.render.Model;
import com.shich.game.entities.render.Texture;

import org.joml.Vector3f;

public class Block extends Entity {
    private static Model block_model = new Model(new Vector3f(0.5f, 0.5f, 0));
    private static Hashtable<Byte, Texture> texture_set = new Hashtable<Byte, Texture>();
    private byte id;

    public Block(Byte id, AABB bounds) {
        super(bounds);
        collidable.activate(this);
        visual.activate(this, block_model, texture_set.get(id));

        this.id = id;
    }

    public byte getType() {
        return id;
    }

    public static void init() {
        byte[] types = new byte[] { 0, 1, 2, 8, 9 };
        for (byte i : types) {
            texture_set.put(i, new Texture("block/block-" + i + ".png"));
        }
    }
}
