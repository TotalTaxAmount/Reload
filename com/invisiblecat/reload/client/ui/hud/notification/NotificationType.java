package com.invisiblecat.reload.client.ui.hud.notification;

import java.awt.*;

public enum NotificationType {
    INFO(new Color(53, 161, 238, 144)),
    SUCCESS(new Color(62, 232, 11, 220)),
    WARNING(new Color(206, 238, 8, 220)),
    ERROR(new Color(239, 5, 5, 220));

    public final Color color;
    NotificationType(Color color) {
        this.color = color;
    }
}
