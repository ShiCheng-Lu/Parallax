package com.shich.game.entities.components;

import com.shich.game.entities.Entity;
import com.shich.game.entities.bounds.AABB;
import com.shich.game.util.Timer;

public class Collidable extends Component {

    public void update(Timer timer) {
        // ArrayList<AABB> targets = new ArrayList<>();

        // velocity.mul(timer.delta);

        // for (int i = 0; i < level.layerNum; ++i) {
        //     Vector3f mod_pos = position.div(i + 1, new Vector3f());
        //     Layer layer = level.getLayer(i);

        //     for (int x = (int) mod_pos.x - 2; x <= (int) mod_pos.x + 2; ++x) {
        //         for (int y = (int) mod_pos.y - 2; y <= (int) mod_pos.y + 2; ++y) {
    
        //             byte type = layer.get(x, y);
        //             if (type == (byte) 1 || (type == (byte) 2 && mod_pos.y >= y + 1)) {
        //                 targets.add(layer.getBoundingBox(x, y));
        //             }
        //         }
        //     }
        // }

        // bounding_box.resolveCollision(velocity, targets);
        
        // position.add(velocity);
        // velocity.div(timer.delta);
    }
}
