package com.shich.game.level;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.shich.game.entities.Mob;

public class Level {
    public String name;
    public int layerNum;
    public List<Layer> layers;

    public Level() {
        layerNum = 0;
        layers = new ArrayList<Layer>();
    }

    public void render(Graphics g, double pX, double pY) {
        for (int i = layerNum - 1; i >= 0; --i) {
            int xOffset = (int) Math.round(pX * 32 * i / (i + 1));
            int yOffset = (int) Math.round(pY * 32 * i / (i + 1));
            layers.get(i).render(g, xOffset, -yOffset);
        }
    }

    public boolean checkCollide(Mob m) {
        // level boundary check
        if (m.xNew < 0 || m.xNew > 24 || m.yNew < 0 || m.yNew > 24) {
            return true;
        }
        // layer collide check
        for (Layer layer : layers) {
            if (layer.checkCollide(m)) {
                return true;
            }
        }
        return false;
    }

    public void set(int layer, int x, int y, char type) {
        layers.get(layer).set(x, y, type);
    }

    public void remove(int layer, int x, int y) {
        if (layer >= 0 && layer < layerNum) {
            layers.get(layer).set(x, y, Block.AIR);
        }
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
                output += layer.movementMod + " ";
                // encode layer datad
                for (int x = 0; x < layer.width; ++x) {
                    for (int y = 0; y < layer.height; ++y) {
                        output += layer.get(x, y).type;
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

            String filePath = "src/com/shich/game/level/levels/" + name + ".txt";
            File file = new File(filePath);
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                int width = fileReader.nextInt();
                int height = fileReader.nextInt();
                int movementMod = fileReader.nextInt();
                String data = fileReader.nextLine().strip();
                Layer layer = new Layer(width, height, movementMod);

                int index = 0;
                for (int x = 0; x < width; ++x) {
                    for (int y = 0; y < height; ++y) {
                        layer.set(x, y, data.charAt(index));
                        index++;
                    }
                }
                layers.add(layer);
                layerNum++;
            }
            
            fileReader.close();
            this.name = name;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
