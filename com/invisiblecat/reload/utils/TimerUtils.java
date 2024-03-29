package com.invisiblecat.reload.utils;

public class TimerUtils {
    private long lastMs = System.currentTimeMillis();

    public boolean hasTimePassed(long ms, boolean b) {
        ms = lastMs + ms;

        if(b && System.currentTimeMillis() >= ms) {
            reset();
        }
        return System.currentTimeMillis() >= ms;
    }

    public long timeLeft(long ms) {
        return ms + lastMs - System.currentTimeMillis();
    }

    public long timePassed() {
        return System.currentTimeMillis() - lastMs;
    }

    public void reset() {
        lastMs = System.currentTimeMillis();
    }
}
