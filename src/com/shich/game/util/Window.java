package com.shich.game.util;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;


public class Window {

    public long window;
    private int[] xpos, ypos;
    private int width, height;
    private String title;

    private Input input;
    private boolean fullscreen;
    
    private int frame_couter = 0;
    private double time = getTime();

    public static void setErrorCallbacks() {
        glfwSetErrorCallback(new GLFWErrorCallback() {
            @Override
            public void invoke(int error, long description) {
                throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
            }
        });
    }

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.xpos = new int[1];
        this.ypos = new int[1];

        this.title = title;
        this.fullscreen = false;

        createWindow();

        glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                glViewport(0, 0, width / 2, height / 2);
                System.out.println("" + width + "  " + height);
                clear();
            }
        });
    }

    public Window(int width, int height, String title, boolean maximize) {
        this(width, height, title);
        if (maximize) {
            glfwMaximizeWindow(window);
        }
    }

    public void createWindow() {
        window = glfwCreateWindow(width, height, title, 0, 0);

        GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(window, (mode.width() - width) / 2, (mode.height() - height) / 2);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glfwSwapInterval(1);    // set fps
        glfwShowWindow(window);

        glClearColor(1, 1, 1, 0);

        input = new Input(window);
    }

    public void setBackgroundColour(float r, float g, float b) {
        glClearColor(r, g, b, 0);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setPos(int xpos, int ypos) {
        this.xpos[0] = xpos;
        this.ypos[0] = ypos;
        glfwSetWindowPos(window, xpos, ypos);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Input getInput() { return input; }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void setShouldClose(boolean val) {
        glfwSetWindowShouldClose(window, val);
    }

    public void setFullScreen(boolean val) {
        System.out.println(fullscreen);
        fullscreen = !fullscreen;

        if (val) {
            glfwGetWindowPos(window, xpos, ypos);
            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        } else {
            glfwSetWindowMonitor(window, 0, xpos[0], ypos[0], width, height, 0);
            
        }
    }

    public void swapBuffers() {
        glfwSwapBuffers(window);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void input() {
        // fullscreen, not used
        // if (input.isKeyPressed(input.FULLSCREEN)) {
        //     setFullScreen(!fullscreen);
        // }

        if (input.isKeyPressed(input.EXIT)) {
            setShouldClose(true);
        }
        
        if (input.isKeyReleased(GLFW_KEY_A)) {
            System.out.println("A key pressed");
        }
    }

    public void update() {
        input.update();
        glfwPollEvents();
        // frame counter
        frame_couter++;
        double current_time = getTime();
        if (current_time > time + 1) {
            time = current_time;
            System.out.println("FPS:" + frame_couter);
            
            glfwSetWindowTitle(window, "Parallax    FPS: " + frame_couter);
            frame_couter = 0;
        }
    }

    public void destroy() {
        input.freeCallbacks();
        glfwTerminate();
    }

    public static double getTime() {
        return (double) System.nanoTime() / 1000000000;
    }
}
