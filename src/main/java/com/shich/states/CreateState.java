package com.shich.states;

import java.util.ArrayList;

import com.shich.entities.Button;
import com.shich.entities.Entity;
import com.shich.entities.bounds.AABB;
import com.shich.entities.render.Renderer;
import com.shich.level.Block;
import com.shich.util.Input;
import com.shich.util.KEYS;
import com.shich.util.Timer;

import org.joml.Vector3f;

public class CreateState extends PlayState {

    // private LayerInputs[] layerInputs;
    private int edit_layer;
    private byte block_type;

    private ArrayList<Button> buttons;
    private ArrayList<Entity> assets;

    public CreateState(GameStateManager gsm) {
        super(gsm);
        loadLevel("empty");

        edit_layer = 0;
        block_type = 1;
        buttons = new ArrayList<Button>();
        assets = new ArrayList<Entity>();
        // overlay
        buttons.add(new Button(new AABB(-0.8f, 0.8f, 0.09f, 0.16f), "block/block-" + block_type + ".png", true) {
            private ArrayList<Byte> block_ids = Block.id_list();
            private byte block_type = CreateState.this.block_type;

            @Override
            public void released() {
                int idx = block_ids.indexOf(block_type) + 1;
                if (idx >= block_ids.size()) {
                    idx = 1;
                }
                block_type = block_ids.get(idx);
                CreateState.this.block_type = block_type;

                renderSetup("block/block-" + block_type + ".png");
            }

            @Override
            public void render(Renderer renderer) {
                super.render(renderer);
                // display text

            }
        });
        assets.add(new Entity(new AABB(-0.8f, 0.65f, 0.09f, 0.06f), "menu/blockSelect.png", true));

        buttons.add(new Button(new AABB(-0.6f, 0.8f, 0.09f, 0.16f), "menu/layerIndicator.png", true) {
            int layer = 0;

            @Override
            public void released() {
                layer += 1;
                if (layer >= 4) {
                    layer = 0;
                }
                CreateState.this.edit_layer = layer;
            }

            @Override
            public void render(Renderer renderer) {
                super.render(renderer);
                switch (layer) {
                    case 3:
                        position.add(bounding_box.half_extent);
                        super.render(renderer);
                        position.sub(bounding_box.half_extent);
                    case 2:
                        position.y += bounding_box.half_extent.y;
                        super.render(renderer);
                        position.y -= bounding_box.half_extent.y;
                    case 1:
                        position.x += bounding_box.half_extent.x;
                        super.render(renderer);
                        position.x -= bounding_box.half_extent.x;
                    default:
                }
            }
        });
        assets.add(new Entity(new AABB(-0.6f, 0.65f, 0.1f, 0.06f), "menu/layerSelect.png", true));

    }

    public void reset() {

    }

    public boolean edit(Input input) {
        boolean on_overlay = false;
        for (Button b : buttons) {
            if (b.hovered) {
                on_overlay = true;
            }
            b.input(input);
        }
        if (on_overlay)
            return true;

        Vector3f offset = player.getPos().mul(edit_layer / (edit_layer + 1.0f), new Vector3f());

        if (input.isButtonDown(KEYS.MOUSE_LEFT)) {
            level.setSafe(edit_layer, (int) Math.round(input.mouse_pos.x - offset.x),
                    (int) Math.round(input.mouse_pos.y - offset.y), block_type);
        }
        if (input.isButtonDown(KEYS.MOUSE_RIGHT)) {
            level.setSafe(edit_layer, (int) Math.round(input.mouse_pos.x - offset.x),
                    (int) Math.round(input.mouse_pos.y - offset.y), (byte) 0);
        }
        return false;
    }

    @Override
    public void input(Input input) {
        pause.input(input);
        if (pause.paused) {
            return;
        }
        if (edit(input)) {
            return;
        }
        super.input(input);
    }

    @Override
    public void update(Timer timer) {
        super.update(timer);
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);

        for (Button b : buttons) {
            b.render(renderer);
        }
        for (Entity e : assets) {
            e.render(renderer);
        }
    }
}
