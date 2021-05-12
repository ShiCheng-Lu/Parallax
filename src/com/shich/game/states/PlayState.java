package com.shich.game.states;

import java.awt.Graphics;

import com.shich.game.entities.*;
import com.shich.game.level.Level;
import com.shich.game.util.KeyHandler;
import com.shich.game.util.MouseHandler;

public class PlayState extends GameState {

    private Player player;
    private Level level;

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

    // private void adjustCamera() {
    // double camTrackSpeed = 0.3;
    // int targetX = -(int) player.getX() + 640;
    // int targetY = (int) player.getY() + 360;

    // camX = (int) (camX + (targetX - camX) * camTrackSpeed);
    // camY = (int) (camY + (targetY - camY) * camTrackSpeed);
    // }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void render(Graphics g) {
        player.drawHUD(g);
        // relatives renders:
        camX = (int) (player.x * 32);
        camY = (int) (-player.y * 32);

        g.translate(camX, camY);

        player.render(g);
        level.render(g, camX, camY, player.x, player.y);

        if (level.name == "selector") {
            switch ((int) Math.round(player.x)) {
                case 0:
                    g.drawString("level 1", 500, 500);
                    break;
                case 4:
                    g.drawString("level 2", 500, 500);
                    break;
                case 8:
                    g.drawString("level 3", 500, 500);
                    break;
                case 12:
                    g.drawString("level 4", 500, 500);
                    break;
                case 16:
                    g.drawString("level 5", 500, 500);
                    break;
                case 20:
                    g.drawString("level 6", 500, 500);
                    break;
                case 24:
                    g.drawString("level 7", 500, 500);
                    break;

            }
        }
    }
}
