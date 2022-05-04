package com.invisiblecat.reload.client.ui.hud.notification;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class NotificationManager {
    private static LinkedBlockingQueue<Notification> pendingNotifications = new LinkedBlockingQueue<>();
    private static Notification currentNotification;

    public static void show(Notification notification) {
        pendingNotifications.add(notification);
    }
    public static void update() {
        if (currentNotification != null && !currentNotification.isShow()) {
            currentNotification = null;
        }
        if (currentNotification == null && !pendingNotifications.isEmpty())  {
            currentNotification = pendingNotifications.poll();
            currentNotification.show();
        }
    }
    public static void render() {
        update();

        if (currentNotification != null) {
            currentNotification.render();
        } else {
            int offset = 10;
            for (Notification notification : pendingNotifications) {
                notification.offsetPlacement = offset;
                notification.render();
                offset += notification.getHeight() + 10;
            }
        }
    }
}
