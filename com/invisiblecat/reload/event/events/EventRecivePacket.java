package com.invisiblecat.reload.event.events;

import com.invisiblecat.reload.event.Event;
import net.minecraft.network.Packet;

public class EventRecivePacket extends Event {
    private final Packet packet;

    private boolean isCancelled = false;

    public EventRecivePacket(Packet packet) {
        this.packet = packet;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }


    public Packet getPacket() {
        return packet;
    }


}
