package com.shich.states;

import com.shich.entities.Player;
import com.shich.entities.bounds.AABB;
import com.shich.entities.render.Renderer;
import com.shich.level.Level;
import com.shich.util.*;

import org.joml.Vector3f;

public class PlayState extends GameState {

    public Player player;
    private Level level;
    private int selectedLevel;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        level = new Level();
        player = new Player(new AABB(0, 0, 1, 1), level);
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
        // if (level.name == "selector") {
        //     if (input.isKeyPressed(KEYS.ACTION) && selectedLevel >= 0) {
        //         loadLevel("level-" + selectedLevel);
        //     }
        // }
        player.input(input);
    }

    @Override
    public void update(Timer timer) {
        player.update(timer);
        gsm.setCameraPosition(player.getPos());
    }

    @Override
    public void render(Renderer renderer) {
        level.render(renderer, player);
    }
}
