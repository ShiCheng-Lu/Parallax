package com.shich.game.util;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Input {

    public int ACTION = GLFW_KEY_SPACE;
    public int LEFT   = GLFW_KEY_A;
    public int RIGHT  = GLFW_KEY_D;
    public int UP     = GLFW_KEY_W;
    public int DOWN   = GLFW_KEY_S;
    public int JUMP   = GLFW_KEY_K;
    public int DASH   = GLFW_KEY_L;
    public int FULLSCREEN = GLFW_KEY_F11; 
    public int EXIT = GLFW_KEY_ESCAPE;

    public int MOUSE_LEFT   = GLFW_MOUSE_BUTTON_1;
    public int MOUSE_RIGHT  = GLFW_MOUSE_BUTTON_2;
    public int MOUSE_MIDDLE = GLFW_MOUSE_BUTTON_3;

    private GLFWKeyCallback key_callback;
    private GLFWMouseButtonCallback mouse_callback;
    private GLFWCursorPosCallback cursor_callback;

    private Window window;

    private boolean[] key_action = new boolean[GLFW_KEY_LAST];
    private boolean[] button_action = new boolean[GLFW_MOUSE_BUTTON_LAST];
    public Vector3f mouse_pos; // with z = 0;
    public Camera camera;

    public Input(Window window, Camera camera) {
        this.window = window;
        this.camera = camera;

        mouse_pos = new Vector3f();

        key_callback = new GLFWKeyCallback(){
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action != GLFW_REPEAT) {
                    key_action[key] = true;
                }
            }
        };

        mouse_callback = new GLFWMouseButtonCallback(){
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (button != GLFW_REPEAT) {
                    button_action[button] = true;
                }
            }
        };

        cursor_callback = new GLFWCursorPosCallback(){
            @Override
            public void invoke(long window, double xpos, double ypos) {
                float width = Input.this.window.getWidth();
                float height = Input.this.window.getHeight();
                mouse_pos = camera.reverseProjection(new Vector3f((float) xpos - width / 2, (float) ypos - height / 2, 0));
            }
        };

        glfwSetKeyCallback(window.window, key_callback);
        glfwSetMouseButtonCallback(window.window, mouse_callback);
        glfwSetCursorPosCallback(window.window, cursor_callback);
    }

    public boolean isKeyPressed(int key) {
        return (key_action[key] && isKeyDown(key));
    }

    public boolean isKeyReleased(int key) {
        return (key_action[key] && !isKeyDown(key));
    }
    
    public boolean isKeyDown(int key) {
        return glfwGetKey(window.window, key) == GLFW_PRESS;
    }

    public boolean isButtonPressed(int button) {
        return (button_action[button] && isButtonDown(button));
    }

    public boolean isButtonReleased(int button) {
        return (button_action[button] && !isButtonDown(button));
    }

    public boolean isButtonDown(int button) {
        return glfwGetMouseButton(window.window, button) == GLFW_PRESS;
    }

    public void update() {
        for (int i = GLFW_KEY_SPACE; i < GLFW_KEY_LAST; ++i) {
            key_action[i] = false;
        }
        for (int i = 0; i < GLFW_MOUSE_BUTTON_LAST; ++i) {
            button_action[i] = false;;
        }
    }

    public void destroy() {
        key_callback.free();
        mouse_callback.free();
        cursor_callback.free();
    }
}
