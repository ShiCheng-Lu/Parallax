package com.shich.game.level;

import com.shich.game.entities.Entity;

public class Block extends Entity {

    public static final char AIR = '0';
    public static final char SOLID = '1';
    public static final char SEMISOLID = '2';
    public static final char WIN = '8';
    public static final char SELECT = '9';

    public static String knownTypes = ".1289";
    
    public static int scale = 32;
    
    public char type;

    public Block(int x, int y, char type) {
        super(x, y, scale, scale);

        setType(type);
        setRenderSetting(32, -32, 0, 0);

        if (type == Block.SELECT) {
            width *= 2;
            height *= 2;
        }
    }

    public void setType(char type) {
        if (knownTypes.indexOf(type) == -1) {
            type = Block.AIR;
        }

        this.type = type;
        loadImage("block/block-" + type + ".png");
    }

    public char getType() {
        return type;
    }
}
