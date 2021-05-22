package com.shich.game.util;

public class Timer {
    
    public float delta;
    private long last_update_time;

    public Timer() {
        
    }

    public void update() {
        long current_time = System.nanoTime();
        delta = ns_to_s(current_time - last_update_time);
    }

    public float getTime() {
        return ns_to_s(System.nanoTime());
    }

    public float ns_to_s(long nano_sec) {
        return (float) nano_sec / 1000000000;
    }

}
