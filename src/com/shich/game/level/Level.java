package com.shich.game.level;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level {
    public int layerNum;
    public List<Layer> layers;

    public Level() {
        layerNum = 4;
        layers = new ArrayList<Layer>();

        layers.add(new Layer(25, 25));
        layers.add(new Layer(13, 13));
        layers.add(new Layer(9, 9));
        layers.add(new Layer(7, 7));
    }

    public void render(Graphics g, int camX, int camY, double pX, double pY) {
        for (int i = layerNum - 1; i >= 0; --i) {
            int xOffset = (int) (pX * 32 * i / (i + 1));
            int yOffset = (int) (pY * 32 * i / (i + 1));
            layers.get(i).render(g, camX + xOffset, camY - yOffset);
        }
    }

    public boolean checkCollide(double x, double y, double width, double height) {
        if (x < 0 || x > 24 || y < 0 || y > 24) {
            return true;
        }
        for (int i = 0; i < layerNum; ++i) {
            if (layers.get(i).checkCollide(x / (i + 1), y / (i + 1), width, height)) {
                return true;
            }
        }
        return false;
    }

    public void set(int layer, int x, int y) {
        layers.get(layer).set(x, y);
    }

    public void remove(int layer, int x, int y) {
        if (layer >= 0 && layer < layerNum) {
            layers.get(layer).remove(x, y);
        }
    }

    public void save(String name) {
        try {
            String filePath = "./levels/" + name + ".txt";
            File file = new File(filePath);
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(filePath);

            String output = "";

            for (Layer layer : layers) {
                output += layer.width + " " + layer.height + " ";
                for (int x = 0; x < layer.width; ++x) {
                    for (int y = 0; y < layer.height; ++y) {
                        if (layer.get(x, y) != null) {
                            output += '1';
                        } else {
                            output += '0';
                        }
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

            String filePath = "./levels/" + name + ".txt";
            File file = new File(filePath);
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                int width = fileReader.nextInt();
                int height = fileReader.nextInt();
                String data = fileReader.nextLine().strip();
                Layer layer = new Layer(width, height);

                int index = 0;
                for (int x = 0; x < width; ++x) {
                    for (int y = 0; y < height; ++y) {
                        if (data.charAt(index) == '1') {
                            layer.set(x, y);
                        }
                        index++;
                    }
                }
                layers.add(layer);
                layerNum++;
            }
            
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
