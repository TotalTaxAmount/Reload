package com.invisiblecat.reload.event.events;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.event.Event;

public class EventChat extends Event {
    private String message;

    public EventChat(String message) {
        this.message = message;
        Reload.instance.commandManager.HandleChat(this);

    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
