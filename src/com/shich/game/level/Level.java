package com.shich.game.level;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.shich.game.collision.AABB;
import com.shich.game.entities.Entity;
import com.shich.game.entities.Mob;
import com.shich.game.entities.Player;
import com.shich.game.render.Renderer;
import com.shich.game.states.GameState;
import com.shich.game.states.PlayState;

import org.joml.Matrix4f;
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
        for (int i = layerNum - 1; i >= 0; --i) {
            layers.get(i).render(renderer, player.getPos().mul(-1/(i + 1), new Vector3f()));
        }
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
                output += layer.collisionMod + " ";
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
                Layer layer = new Layer(this, width, height, new Matrix4f().scale(1 / movementMod));

                int index = 0;
                for (int x = 0; x < width; ++x) {
                    for (int y = 0; y < height; ++y) {
                        layer.set(x, y, (byte) (data.charAt(index) - '0'));
                        index++;
                    }
                }
                layers.add(layer);
                layerNum++;
            }
            
            // int[] startloc = {-576, -384, -192, 0, 192, 384, 576, 768, 960, 1152};
            // for (int x : startloc) {
            //     layers.get(0).addAsset(x, 32, "assets/grass03.png");
            // }
            
            // layers.get(1).addAsset(-768, -128, 2304, 226, "assets/l2.png");

            fileReader.close();
            this.name = name;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            load("selector");
        }
    }
}
