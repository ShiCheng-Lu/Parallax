package com.shich.game.states;

import java.awt.Graphics;

import com.shich.game.entities.*;
import com.shich.game.level.Level;
import com.shich.game.util.KeyHandler;
import com.shich.game.util.MouseHandler;

public class PlayState extends GameState {

    private Player player;
    private Level level;
    private int selectedLevel;

    private int camX = 0;
    private int camY = 0;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        level = new Level();
        player = new Player("bob", 0, 0, level);
    }

    public void loadLevel(String levelName) {
        level.load(levelName);
    }

    @Override
    public void input(KeyHandler key, MouseHandler mouse) {
        player.input(key, mouse);

        if (key.action.clicked() && level.name == "selector") {
            int pos = (int) player.x;
            if (((int) player.x) % 4 == 0) {
                String newLevel = "level" + pos / 4;
                loadLevel(newLevel);
            }
        }
    }

    @Override
    public void update() {
        player.update();

        // level selector
        if (level.name == "selector") {
            // find the closest multiple of 4
            int closestLevel = (int) Math.round(player.x / 4);
            // check if it is within 1.3 of player position
            if (Math.abs(player.x - closestLevel * 4) < 0.8) {
                selectedLevel = closestLevel;
            } else {
                selectedLevel = -1;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        player.drawHUD(g);
        // relatives renders:
        camX = (int) Math.round(player.x * 32);
        camY = (int) Math.round(player.y * 32);

        g.translate(1536 / 2 - camX, 800 / 2 + 200 + camY);

        player.render(g);
        level.render(g, player.x, player.y);

        if (level.name == "selector" && selectedLevel >= 0) {
            g.drawString("level" + selectedLevel, (int) Math.round((player.x + 3) * 32 * 3 / 4), -10);
        }
    }
}
