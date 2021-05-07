package com.shich.game;

import javax.swing.JPanel;
import java.awt.Graphics;

import com.shich.game.states.GameStateManager;
import com.shich.game.util.KeyHandler;
import com.shich.game.util.MouseHandler;

import java.awt.Dimension;

public class GamePanel extends JPanel implements Runnable {
    public static int width;
    public static int height;

    private boolean running = false;
    private Thread thread;

    private GameStateManager gameStateManager;
    private MouseHandler mouse;
    private KeyHandler key;

    public GamePanel(int width, int height) {
        GamePanel.width = width;
        GamePanel.height = height;

        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        render(g);
    }

    @Override
    public void addNotify() { // runs at start by JPanel
        super.addNotify();

        thread = new Thread(this, "game thread");
        thread.start();
    }

    private void init() {
        running = true;

        gameStateManager = new GameStateManager(this);
        key = new KeyHandler(this);
        mouse = new MouseHandler(this);
    }

    @Override
    public void run() {
        init();

        final long TPS = 60;
        final long MSPT = (long) Math.pow(10, 9) / TPS; // MilliSecond Per Tick

        long now;
        long sleepTime, timeDiff;
        int frameCounter = 0;
        long lastSecond = System.nanoTime();

        while (running) {
            now = System.nanoTime();

            update();
            repaint();
            input();

            frameCounter++;

            if (frameCounter % 60 == 0) { // show FPS
                System.out.println((now - lastSecond) + ", " + frameCounter + ", " + HEIGHT + ", " + width);
            }

            timeDiff = System.nanoTime() - now;
            sleepTime = Math.max(0, (MSPT - timeDiff) / 1000000); // calculate the ms to sleep
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException error) {
                System.out.println("game thread" + error);
            }
        }
    }

    public void update() {
        gameStateManager.update();
    }

    public void render(Graphics g) {
        gameStateManager.render(g);
    }

    public void input() {
        gameStateManager.input(this.key, this.mouse);

        mouse.left.clicked();
    }
}
