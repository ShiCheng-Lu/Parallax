package com.shich.game.entities.components;

import com.shich.game.entities.Entity;
import com.shich.game.entities.render.Renderer;
import com.shich.game.util.Input;
import com.shich.game.util.Timer;

public class Component {
    protected Entity master;
    protected boolean active = false;

    public Component() {
        this.active = false;
    }

    public void activate(Entity master) {
        this.master = master;
        this.active = true;
    }

    public void input(Input input) {
        return;
    }

    public void update(Timer timer) {
        return;
    }

    public void render(Renderer renderer) {
        return;
    }
}
