package com.shich.game.level;

import com.shich.game.entities.Entity;

public class Block extends Entity {

    public static char AIR = '.';
    public static char SOLID = '1';
    public static char WIN = '2';
    public static char SELECT = '9';
    
    public static int scale = 32;
    
    private int type;

    public Block(int x, int y, char type) {
        super(x, y, scale, scale);

        this.type = type;

        String blockName = "empty";
        if (type == Block.SOLID) {
            blockName = "block";
        } else if (type == Block.WIN) {
            // loadImage("src/com/")
        } else if (type == Block.SELECT) {
            blockName = "selectBlock";
        } else {
            
        }
        loadImage("src/com/shich/game/graphics/" + blockName + ".png");
    }

	public int type() {
		return type;
	}
}
