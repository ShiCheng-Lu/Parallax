package com.shich.game.states;

import com.shich.game.collision.AABB;
import com.shich.game.entities.Player;
import com.shich.game.level.Level;
import com.shich.game.render.Renderer;
import com.shich.game.util.Input;
import com.shich.game.util.Timer;

import org.joml.Vector3f;

public class PlayState extends GameState {

    public Player player;
    private Level level;
    private int selectedLevel;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        level = new Level();
        player = new Player(new AABB(0, 0, 1, 1), level);
        player.setPos(0, 0, 0);
        player.setLevel(level);
        gsm.setCameraOffset(new Vector3f(0, 4, 0));
    }

    public void loadLevel(String levelName) {
        level.load(levelName);
        player.setLevel(level);
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
    public void update(Timer timer) {
        player.update(timer);
        gsm.setCameraPosition(player.getPos());
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
        level.render(renderer, player);
    }
}
