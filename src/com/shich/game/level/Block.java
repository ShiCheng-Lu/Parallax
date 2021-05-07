package com.shich.game.level;

import com.shich.game.entities.Entity;

public class Block extends Entity {

    public static int SOLID = 0;
    public static int SEMISOLID = 1;
    public static int scale = 32;
    
    private int type;

    public Block(int x, int y, int type) {
        super(x, y, 32, 32);

        this.type = type;

        if (type == Block.SOLID) {
            loadImage("src/com/shich/game/graphics/block.png");
        } else if (type == Block.SEMISOLID) {
            loadImage("src/com/shich/game/graphics/platform.png");
        }
    }



	public int type() {
		return type;
	}
}
