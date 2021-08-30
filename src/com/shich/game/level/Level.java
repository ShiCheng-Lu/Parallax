package com.shich.game.level;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.shich.game.entities.Entity;
import com.shich.game.entities.Player;
import com.shich.game.entities.render.Renderer;

import org.joml.Vector3f;

public class Level {
    public String name;
    public int layerNum;
    public ArrayList<Layer> layers;

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

    public void remove(int layer, int x, int y) {
        if (layer >= 0 && layer < layerNum) {
            layers.get(layer).set(x, y, (byte) 0);
        }
    }

    public void loadNext() {
        // if (parent instanceof PlayState) {
        //     PlayState p = (PlayState) parent;
        //     p.player.setPos(0, 0);
        //     int newlevel = Integer.parseInt(name.split("-")[1]) + 1;
        //     load("level-" + newlevel);
        // }
    }

    public void save(String name) {
        try {
            String filePath = "src/com/shich/game/level/levels/" + name + ".txt";
            File file = new File(filePath);
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(filePath);

            String output = "";

            for (Layer layer : layers) {
                // encode layer definitions
                output += layer.width + " ";
                output += layer.height + " ";
                output += layer.scale + " ";
                // encode layer datad
                for (int x = 0; x < layer.width; ++x) {
                    for (int y = 0; y < layer.height; ++y) {
                        output += layer.get(x, y);
                    }
                }
                output += '\n';
            }

            fileWriter.write(output);
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void load(String name) {
        try {
            layerNum = 0;
            layers = new ArrayList<Layer>();

            String filePath = "res/levels/" + name + ".txt";
            File file = new File(filePath);
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                int width = fileReader.nextInt();
                int height = fileReader.nextInt();
                int movementMod = fileReader.nextInt();
                String data = fileReader.nextLine().strip();
                
                Layer layer = new Layer(this, width, height, movementMod);

                int index = 0;
                for (int x = 0; x < width; ++x) {
                    for (int y = 0; y < height; ++y) {
                        byte blockType = (byte) (data.charAt(index) - '0');
                        layer.set(x, y, blockType);

                        index++;
                    }
                }
                layers.add(layer);
                layerNum++;
            }
            
            // layers.get(0).addAsset(new Entity(new AABB(0, -2, 50, 3), "assets/grass03.png"));
            // layers.get(0).addAsset(new Entity(new AABB(0, -0.3f, 50, 0.5f), "assets/set_grass1.png"));
            // layers.get(2).addAsset(new Entity(new AABB(0, 2, 70, 10), "assets/l2.png"));

            fileReader.close();
            this.name = name;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            load("selector");
        }
    }
}
