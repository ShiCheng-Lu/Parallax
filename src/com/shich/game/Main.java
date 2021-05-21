package com.shich.game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.glfw.GLFWVidMode;

import com.shich.game.util.Input;
import com.shich.game.util.Window;

import org.lwjgl.opengl.GL;

public class Main implements Runnable {
    private Thread game;
    private Window window;
    private Input input;

    public void start() {
        game = new Thread(this, "game thread");
        game.start();
    }

    private void init() {
        Window.setErrorCallbacks();

        if (!glfwInit()) {
            System.err.println("GLFW failed to initiate");
            System.exit(1); // exit with error code 1
        }

        window = new Window(1920, 1080, "Parallax", false);
        input = window.getInput();
    }

    @Override
    public void run() {
        init();
        while (!window.shouldClose()) {
            input();
            update();
            render();
        }
        destroy();
    }

    public void input() {
        if (input.isButtonPressed(GLFW_MOUSE_BUTTON_1)) {
            System.out.println("x: " + input.mouse_x + "y: " + input.mouse_y);
        }


        window.input();
    }

    public void update() {
        window.update();
    }

    public void render() {
        window.setBackgroundColour(0.5f, 0.5f, 1f);
        // window.clear();
        window.swapBuffers();
    }

    public void destroy() {
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
        //     current_time = Timer.getTime();
        //     unprecessed += current_time - last_update_time;
        //     last_update_time = current_time;

        //     while (unprecessed >= frame_cap) {
        //         unprecessed -= frame_cap;
        //         // frame counter, 
        //         frame_time += frame_cap;
        //         frame_count += 1;
        //         if (frame_time >= 1) {
        //             System.out.println("FPS: " + frame_count);
        //             frame_count = 0;
        //             frame_time = 0;
        //         }
        //         // update
        //         new_render = true;
        //         window.update(frame_cap);
        //         if (window.getInput().isKeyDown(GLFW_KEY_LEFT_CONTROL) && window.getInput().isKeyPressed(GLFW_KEY_W)) {
        //             window.setShouldClose(true);
        //         }
                
        //     }

        //     if (new_render) {
        //         glClear(GL_COLOR_BUFFER_BIT);

        //         window.swapBuffers();
        //     }
        // }
    }

}
