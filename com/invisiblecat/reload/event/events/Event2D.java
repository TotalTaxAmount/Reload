package com.invisiblecat.reload.event.events;

import com.invisiblecat.reload.event.Event;

public class Event2D extends Event {
    public float width, height;

    public Event2D(float width, float height) {
        this.width = width;
        this.height = height;

    }
    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
