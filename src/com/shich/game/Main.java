package com.shich.game;

import static org.lwjgl.glfw.GLFW.*;

import com.shich.game.collision.AABB;
import com.shich.game.entities.Entity;
import com.shich.game.entities.Player;
import com.shich.game.level.Block;
import com.shich.game.level.Layer;
import com.shich.game.level.Level;
import com.shich.game.render.Model;
import com.shich.game.render.Renderer;
import com.shich.game.render.Shader;
import com.shich.game.render.Texture;
import com.shich.game.util.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;

public class Main implements Runnable {
    private Thread game;
    private Window window;
    private Shader shader;
    private Input input;
    private Camera camera;
    private Renderer renderer;
    private Timer timer;

    // temps
    private Player e;
    private Level l;

    public void start() {
        game = new Thread(this, "game thread");
        game.start();
    }

    private void init() {
        GLFWErrorCallback.createPrint().set();

        if (!glfwInit()) {
            System.err.println("GLFW failed to initiate");
            System.exit(1); // exit with error code 1
        }

        window = new Window(1920, 1080, "Parallax", true);
        input = new Input(window.window);
        shader = new Shader("shaders/shader");
        camera = new Camera(window);
        renderer = new Renderer(shader, camera);
        timer = new Timer();

        window.setBackgroundColour(1, 1, 1);
    }

    @Override
    public void run() {
        init();

        l = new Level();
        l.load("level-3");

        Block.init();

        e = new Player(new AABB(1, 1, 0.5f, 0.5f), l);
        e.renderSetup("player.png");

        while (!window.shouldClose()) {
            input(input);
            update(timer);
            render(renderer);
        }
        destroy();
    }

    public void input(Input input) {
        if (input.isButtonPressed(GLFW_MOUSE_BUTTON_1)) {
            System.out.println("pos: " + e.getPos() + "    camera: " + camera.getPosition());
        }

        e.input(input);
        window.input(input);
    }

    public void update(Timer timer) {
        timer.update();

        camera.setPosition(e.getPos());

        e.update(timer);

        input.update();
        camera.update();
        window.update(timer);
    }

    public void render(Renderer renderer) {
        e.render(renderer);
        l.render(renderer, e);

        window.swapBuffers();
    }

    public void destroy() {
        input.freeCallbacks();
        window.destroy();
    }

    public static void main(String[] args) {
        new Main().start();

        // double frame_cap = 1.0 / mode.refreshRate();
        // double last_update_time = Timer.getTime();
        // double current_time = last_update_time;
        // double unprecessed = 0;

        // boolean new_render = false;

        // double frame_time = 0;
        // int frame_count = 0;

        // while (!window.shouldClose()) {
        // current_time = Timer.getTime();
        // unprecessed += current_time - last_update_time;
        // last_update_time = current_time;

        // while (unprecessed >= frame_cap) {
        // unprecessed -= frame_cap;
        // // frame counter,
        // frame_time += frame_cap;
        // frame_count += 1;
        // if (frame_time >= 1) {
        // System.out.println("FPS: " + frame_count);
        // frame_count = 0;
        // frame_time = 0;
        // }
        // // update
        // new_render = true;
        // window.update(frame_cap);
        // if (window.getInput().isKeyDown(GLFW_KEY_LEFT_CONTROL) &&
        // window.getInput().isKeyPressed(GLFW_KEY_W)) {
        // window.setShouldClose(true);
        // }

        // }

        // if (new_render) {
        // glClear(GL_COLOR_BUFFER_BIT);

        // window.swapBuffers();
        // }
        // }
    }

}
