package com.shich.game.entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import java.awt.Graphics;

public abstract class Entity {

    protected static ArrayList<Entity> allEntities = new ArrayList<Entity>();

    public double x, y, width, height;
    protected Image img;
    public int xScale = 1, yScale = 1, xOffset = 0, yOffset = 0;

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
        ImageIcon ii = new ImageIcon("src/com/shich/game/graphics/" + file);
        img = ii.getImage();
    }

    public void update() {
    }

    public void render(Graphics g, int xScale, int yScale, int xOffset, int yOffset) {
        g.drawImage(img, toInt(x * xScale) + xOffset, toInt(y * yScale) + yOffset, null);
    }

    public void render(Graphics g, int xOffset, int yOffset) {
        render(g, xScale, yScale, xOffset, yOffset); // use entity default scale
    }

    public void render(Graphics g) {
        render(g, xScale, yScale, xOffset, yOffset); // use entity default scale and offset
    }

    public void setRenderSetting(int xScale, int yScale, int xOffset, int yOffset) {
        this.xScale = xScale;
        this.yScale = yScale;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int toInt(double num) {
        return (int) Math.round(num);
    }

    public Rectangle getBounds() {
        return new Rectangle(toInt(x - width / 2), toInt(y - height / 2), toInt(width), toInt(height));
    }
}
