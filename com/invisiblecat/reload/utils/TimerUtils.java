package com.invisiblecat.reload.utils;

<<<<<<< HEAD
public class TimerUtils {
    private long lastMs = -1;

    public boolean hasTimePassed(long ms, boolean b) {
        ms = lastMs + ms;

        if(b) {
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
=======

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
>>>>>>> c1af8cb565bffe32bd63ec46d99b619c118cdb85
