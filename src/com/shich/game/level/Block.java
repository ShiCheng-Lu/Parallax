package com.shich.game.level;

import java.util.Hashtable;

import com.shich.game.entities.Entity;

public class Block extends Entity {

    private static Hashtable<Byte, Block>block_set = new Hashtable<Byte, Block>();
    private Byte id;
    
    public char type;

    public Block(Byte id, String image_file) {
        super(null, null);
        this.id = id;
        block_set.put(id, this);
    }

    public char getType() {
        return type;
    }

    public static void render(byte id, float x, float y) {
        block_set.get(id).render(x, y);
    }

    public void render(float x, float y) {
        
    }

    public Block getBlock(Byte id) {
        return block_set.get(id);
    }
} 
