package com.invisiblecat.reload.event.events;

import com.invisiblecat.reload.event.Event;

public class Event3D extends Event {
    private final float partialTicks;

    public Event3D(float partialTicks) {
        this.partialTicks = partialTicks;

    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
