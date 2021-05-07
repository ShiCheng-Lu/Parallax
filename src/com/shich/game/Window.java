package com.shich.game;

import javax.swing.JFrame;
import java.awt.EventQueue;

public class Window extends JFrame {

    public Window() {

        setTitle("Parallax");
        add(new GamePanel(1536, 800));

        setResizable(true);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void initUI() {

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Window window = new Window();
            window.setVisible(true);
        });
    }
}
