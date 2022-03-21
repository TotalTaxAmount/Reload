package com.invisiblecat.reload.utils;


public class TimerUtils {
    private long time = -1L;

    public boolean hasTimePassed(long ms) {
        return System.currentTimeMillis() >= time + ms;
    }

    public long hasTimeLeft(long ms) {
        return ms + time - System.currentTimeMillis();
    }

    public long timePassed() {
        return System.currentTimeMillis() - time;
    }
    public void reset() {
        time = System.currentTimeMillis();
    }
}
