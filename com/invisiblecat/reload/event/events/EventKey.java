package com.invisiblecat.reload.event.events;

import com.invisiblecat.reload.event.Event;

public class EventKey extends Event {
    private int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
