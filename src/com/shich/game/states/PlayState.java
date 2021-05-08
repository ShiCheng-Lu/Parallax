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

        level.load("level1");
    }

    @Override
    public void input(KeyHandler key, MouseHandler mouse) {
        player.input(key, mouse);
    }

    // private void adjustCamera() {
    //     double camTrackSpeed = 0.3;
    //     int targetX = -(int) player.getX() + 640;
    //     int targetY = (int) player.getY() + 360;

    //     camX = (int) (camX + (targetX - camX) * camTrackSpeed);
    //     camY = (int) (camY + (targetY - camY) * camTrackSpeed);
    // }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void render(Graphics g) {
        player.drawHUD(g);
        // relatives renders:
        camX = 640 - (int) (player.x * 32);
        camY = 480 + (int) (player.y * 32);

        player.render(g);
        level.render(g, camX, camY, player.x, player.y);
    }
}
