package com.shich.game.entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import java.awt.Graphics;

public abstract class Entity {

    public static int scale = 32;

    protected static ArrayList<Entity> allEntities = new ArrayList<Entity>();

    protected double x, y, width, height;
    protected Image img;

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Entity(int x, int y) {
        this(x, y, 0, 0);
    }

    protected void loadImage(String file) {
        ImageIcon ii = new ImageIcon(file);
        img = ii.getImage();
    }

    public double distance(double nx, double ny) {
        return Math.pow(Math.pow((x - nx), 2) + Math.pow((y - ny), 2), 0.5);
    }

    public double distance(Entity e) {
        return distance(e.x, e.y);
    }

    public void update() {
        // should handle collisoins here;
    }

    public void render(Graphics g, int xOffset, int yOffset) {
        g.drawImage(img, toInt(x * scale) + xOffset, toInt(-y * scale) + yOffset, null);
    }

    public Image getImage() {
        return img;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int toInt(double num) {
        return (int) Math.round(num);
    }

    public Rectangle getBounds() {
        return new Rectangle(toInt(x - width / 2), toInt(y - height / 2), toInt(width), toInt(height));
    }
}
