package com.shich.entities;

import java.util.ArrayList;

import com.shich.entities.bounds.AABB;
import com.shich.entities.move.Dash;
import com.shich.entities.move.Jump;
import com.shich.entities.move.Movement;
import com.shich.entities.render.Model;
import com.shich.entities.render.Renderer;
import com.shich.entities.render.Texture;
import com.shich.level.Layer;
import com.shich.level.Level;
import com.shich.util.Input;
import com.shich.util.KEYS;
import com.shich.util.Timer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Player {

    AABB bounds;
    Dash dash;
    Jump jump;
    Movement move;
    
    Model model;
    Texture text;

    Level level;

    public Player(AABB bounds, Level level) {
        this.bounds = bounds;
        this.move = new Movement(bounds.center, new Vector3f(), new Vector3f());
        this.dash = new Dash(move);
        this.jump = new Jump(move, 4, 5);
        this.jump.calc_gravity(10);

        this.model = new Model(bounds.half_extent);
        this.text = new Texture("player.png");
    }

    public void collide(Timer timer) {
        ArrayList<AABB> targets = new ArrayList<>();

        move.vel.mul(timer.delta);

        for (int i = 0; i < level.layerNum; ++i) {
            Vector3f mod_pos = move.pos.div(i + 1, new Vector3f());
            Layer layer = level.getLayer(i);

            for (int x = (int) mod_pos.x - 2; x <= (int) mod_pos.x + 2; ++x) {
                for (int y = (int) mod_pos.y - 2; y <= (int) mod_pos.y + 2; ++y) {
    
                    byte type = layer.get(x, y);
                    if (type == (byte) 1 || (type == (byte) 2 && mod_pos.y >= y + 1)) {
                        targets.add(layer.getBoundingBox(x, y));
                    }
                }
            }
        }

        if (bounds.resolveCollision(move.vel, targets)) {
            jump.setCanJump(true);
        }
        
        move.pos.add(move.vel);
        move.vel.div(timer.delta);
    }
    
    public void setLevel(Level level) {
        this.level = level;
    }

    public Vector3f getPos() {
        return move.pos;
    }

    public void input(Input input) {
        jump.input(input);
        dash.input(input);
        move.input(input);
    }

    public void update(Timer timer) {
        if (bounds.getCollision(level.win_bounds)) {
            System.out.println("YOU WIN");
        }

        jump.update(timer);
        dash.update(timer);

        collide(timer);

        move.update(timer);
    }

    public void render(Renderer renderer) {
        Matrix4f trans = new Matrix4f();
        
        trans.translate(move.pos);

        if (move.facing == -1) {
            trans.reflect(new Vector3f(1, 0, 0), new Vector3f());
        }

        trans.scale(1.2f);
        renderer.render(trans, model, text);
    }
}