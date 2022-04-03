package com.invisiblecat.reload.client.ui.hud.notification;

import java.awt.*;

public enum NotificationType {
    INFO(new Color(77, 178, 232, 144)),
    SUCCESS(new Color(62, 232, 11, 220)),
    WARNING(new Color(213, 239, 34, 220)),
    ERROR(new Color(239, 5, 5, 220));

    public Color color;
    NotificationType(Color color) {
        this.color = color;
//        switch (this) {
//            case INFO:
//                color = new Color(77, 178, 232, 144);
//                break;
//            case SUCCESS:
//                color = new Color(62, 232, 11, 220);
//                break;
//            case WARNING:
//                color = new Color(213, 239, 34, 220);
//                break;
//            case ERROR:
//                color = new Color(239, 5, 5, 220);
//                break;
//        }
    }
}
