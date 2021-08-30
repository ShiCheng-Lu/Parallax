package com.shich.game.entities.components;

import com.shich.game.entities.Entity;
import com.shich.game.util.Input;

import org.joml.Vector3f;

public class Controllable extends Component {

    public void input(Input input) {
        if (!active) {
            return;
        }

        if (input.isKeyDown(input.LEFT)) {
            master.movable.accerlate(new Vector3f(-1, 0, 0));
        }
        if (input.isKeyDown(input.RIGHT)) {
            master.movable.accerlate(new Vector3f(1, 0, 0));
        }
        if (input.isKeyDown(input.UP)) {
            master.movable.accerlate(new Vector3f(0, 1, 0));
        }
        if (input.isKeyDown(input.DOWN)) {
            master.movable.accerlate(new Vector3f(0, -1, 0));
        }
    }
}
