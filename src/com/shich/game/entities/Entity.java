package com.shich.game.entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import java.awt.Graphics;

public abstract class Entity {

    private static int scale = 32;

    protected static ArrayList<Entity> allEntities = new ArrayList<Entity>();

    public double x, y, width, height;
    protected Image img;

    public Entity(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Entity(double x, double y) {
        this(x, y, 0, 0);
    }

    public void loadImage(String file) {
        ImageIcon ii = new ImageIcon(file);
        img = ii.getImage();
    }

    public void update() {
        // should handle collisoins here;
    }

    public void render(Graphics g, int xOffset, int yOffset) {
        g.drawImage(img, toInt(x * scale) + xOffset, toInt(-y * scale) + yOffset, null);
    }

    public void render(Graphics g) {
        render(g, 0, 0);
    }

    public int toInt(double num) {
        return (int) Math.round(num);
    }

    public Rectangle getBounds() {
        return new Rectangle(toInt(x - width / 2), toInt(y - height / 2), toInt(width), toInt(height));
    }
}
