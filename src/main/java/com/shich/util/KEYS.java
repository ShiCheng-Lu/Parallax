package com.shich.util;

import static org.lwjgl.glfw.GLFW.*;

public enum KEYS {
    ACTION,
    LEFT,
    RIGHT,
    UP,
    DOWN,
    JUMP,
    DASH,
    FULLSCREEN,
    EXIT,
    MOUSE_LEFT,
    MOUSE_MIDDLE,
    MOUSE_RIGHT,
    ;
    public static final int size;
    static {
        size = KEYS.values().length;
    }
}
