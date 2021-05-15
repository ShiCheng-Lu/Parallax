package com.shich.game.level;

import com.shich.game.entities.Entity;

public class Block extends Entity {

    public static char AIR = 'a';
    public static char SOLID = '1';
    public static char SEMISOLID = '2';
    public static char WIN = '8';
    public static char SELECT = '9';

    public static String knownTypes = ".1289";
    
    public static int scale = 32;
    
    public char type;

    public Block(int x, int y, char type) {
        super(x, y, scale, scale);

        setType(type);
    }

    public void setType(char type) {
        if (knownTypes.indexOf(type) == -1) {
            type = Block.AIR;
        }

        this.type = type;
        loadImage("src/com/shich/game/graphics/block/block-" + type + ".png");
    }

    public char getType() {
        return type;
    }
}
