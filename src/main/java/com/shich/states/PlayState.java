package com.shich.states;

import com.shich.entities.Player;
import com.shich.entities.bounds.AABB;
import com.shich.entities.render.Renderer;
import com.shich.level.Level;
import com.shich.util.*;

import org.joml.Vector3f;

public class PlayState extends GameState {

    protected Pause pause;

    public Player player;
    protected Level level;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        level = new Level();
        player = new Player(new AABB(0, 0, 1, 1), level);
        player.setLevel(level);
        pause = new Pause(gsm);
    }

    public void loadLevel(String levelName) {
        level.load(levelName);
        player.setLevel(level);
    }

    @Override
    public void focus() {
        gsm.camera.setScale(128);
        gsm.camera.setOffset(new Vector3f(0, 4, 0));
    }

    @Override
    public void input(Input input) {
        pause.input(input);
        if (pause.paused) {
            return;
        }

        player.input(input);
    }

    @Override
    public void update(Timer timer) {
        if (pause.paused) {
            pause.update(timer);
            return;
        }
        player.update(timer);
        gsm.camera.setPosition(player.getPos());

        if (player.getBoundingBox().getCollision(level.win_bounds)) {
            // load next level
            int newlevel = Integer.parseInt(level.name.split("-")[1]) + 1;
            level = new Level();
            loadLevel("level-" + newlevel);
            System.out.println(level.name);
        }
    }

    @Override
    public void render(Renderer renderer) {
        level.render(renderer, player);
        pause.render(renderer);
    }
}
