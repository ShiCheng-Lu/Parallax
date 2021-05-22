package com.shich.game.states;

import java.awt.Graphics;

import com.shich.game.collision.AABB;
import com.shich.game.entities.*;
import com.shich.game.level.Level;
import com.shich.game.render.Renderer;
import com.shich.game.util.Input;

public class PlayState extends GameState {

    public Player player;
    private Level level;
    private int selectedLevel;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        level = new Level(this);
        player = new Player(new AABB(0, 0, 1, 1), level);
    }

    public void loadLevel(String levelName) {
        level.load(levelName);
    }

    @Override
    public void input(Input input) {
        // level selector
        if (level.name == "selector") {
            if (input.isKeyPressed(input.ACTION) && selectedLevel >= 0) {
                loadLevel("level-" + selectedLevel);
            }
        }
        player.input(input);
    }

    @Override
    public void update(double deltaTime) {
        player.update(deltaTime);

        // level selector
        // if (level.name == "selector") {
        //     // find the closest multiple of 4
        //     int closestLevel = (int) Math.round(player.x / 4);
        //     // check if it is within 1.3 of player position
        //     if (Math.abs(player.x - closestLevel * 4) < 0.8) {
        //         selectedLevel = closestLevel;
        //     } else {
        //         selectedLevel = -1;
        //     }
        // }
    }

    @Override
    public void render(Renderer renderer) {
        player.render(renderer);
    }

    public void transition(Graphics g, int centetX, int centerY) {
        
    }
}
