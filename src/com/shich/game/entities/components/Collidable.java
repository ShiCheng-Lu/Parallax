package com.shich.game.entities.components;

import java.util.ArrayList;

import com.shich.game.entities.Entity;
import com.shich.game.entities.bounds.AABB;
import com.shich.game.entities.bounds.Collision;
import com.shich.game.util.Timer;

public class Collidable extends Component {

    private ArrayList<Entity> targets;

    public void update(Timer timer) {
        ArrayList<Collision> collisions = new ArrayList<Collision>();

        for (Entity target : this.targets) {
            Collision collision = target.bounds.getCollision(master.bounds, master.movable.velocity);

            collisions.add(collision);
        }

        collisions.sort(null);
        for (Collision c : collisions) {
            // collide and update velocity
            Collision new_c = c.target.getCollision(master.bounds, master.movable.velocity);
            if (new_c.intersects) {
                master.movable.velocity.sub(new_c.normal.mul(master.movable.velocity).mul(1 - new_c.time));
            }
        }
        // position.add(velocity);
        // velocity.div(timer.delta);
    }

    public void setTargets(ArrayList<Entity> targets) {
        this.targets = targets;
    }
}
