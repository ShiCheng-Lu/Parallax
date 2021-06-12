package com.shich.level;

import java.util.ArrayList;

import com.shich.entities.Entity;
import com.shich.entities.Player;
import com.shich.entities.bounds.AABB;
import com.shich.entities.render.Renderer;
import com.shich.util.Files;

import org.joml.Vector3f;

public class Level {
    public String name;
    public int layerNum;
    public ArrayList<Layer> layers;

    public AABB win_bounds;

    protected ArrayList<Entity> assets = new ArrayList<Entity>();

    public Level() {
        layerNum = 0;
        layers = new ArrayList<Layer>();
    }

    public void render(Renderer renderer, Player player) {
        for (int i = 1; i < layerNum; ++i) {
            layers.get(i).renderAsset(renderer, player.getPos().mul(i/(i + 1.0f), new Vector3f()));
        }
        for (int i = layerNum - 1; i >= 0; --i) {
            layers.get(i).render(renderer, player.getPos().mul(i/(i + 1.0f), new Vector3f()));
        }
        player.render(renderer);
        layers.get(0).renderAsset(renderer, new Vector3f());
    }

    public Layer getLayer(int i) {
        return layers.get(i);
    }

    public void set(int layer, int x, int y, byte type) {
        layers.get(layer).set(x, y, type);
    }

    public void setSafe(int layer, int x, int y, byte type) {
        layers.get(layer).setSafe(x, y, type);
    }

    public void save(String name) {
            StringBuilder result = new StringBuilder();
            for (Layer layer : layers) {
                result.append(layer.save());
                result.append("\n");
            }
            Files.writeFile("levels/" + name, result.toString());
    }

    public void load(String name) {
        String[] level_data = Files.readFile("levels/" + name).split("\n");

        for (String layer_data : level_data) {
            Layer layer = new Layer(this);
            layer.load(layer_data);
            layers.add(layer);
        }

        layerNum = level_data.length;

        layers.get(0).addAsset(new Entity(new AABB(0, -2, 50, 3), "assets/grass03.png"));
        // layers.get(0).addAsset(new Entity(new AABB(0, -0.3f, 50, 0.5f), "assets/set_grass1.png"));
        layers.get(2).addAsset(new Entity(new AABB(0, 2, 70, 10), "assets/l2.png"));
        this.name = name;
    }
}
