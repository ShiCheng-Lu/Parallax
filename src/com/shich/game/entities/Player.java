package com.shich.game.entities;

import com.shich.game.entities.bounds.AABB;
import com.shich.game.entities.components.Visual;
import com.shich.game.entities.render.Model;
import com.shich.game.entities.render.Texture;
import com.shich.game.level.Level;
import com.shich.game.util.Input;
import com.shich.game.util.Timer;

import org.joml.Vector3f;

public class Player extends Entity {
    Level level;

    public Player(AABB bounds, Level level) {
        super(bounds);
        this.level = level;

        collidable.activate(this);
        controllable.activate(this);
        movable.activate(this, 20, 1);
        visual.activate(this, new Model(bounds.half_extent), new Texture("player.png"));
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void update(Timer timer) {
        level.getLayer(0);



        this.collidable.setTargets();
        super.update(timer);
    }
}