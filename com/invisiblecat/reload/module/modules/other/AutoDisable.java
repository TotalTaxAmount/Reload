package com.invisiblecat.reload.module.modules.other;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.notification.Notification;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationManager;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationType;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventJoinWorld;
import com.invisiblecat.reload.event.events.EventRecivePacket;
import com.invisiblecat.reload.event.events.EventRespawn;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class AutoDisable extends Module {

    private final BooleanSetting world = new BooleanSetting("World", true);
    private final BooleanSetting respawn = new BooleanSetting("Respawn", true);
    private final BooleanSetting flag = new BooleanSetting("Flag", true);
    private final BooleanSetting chat = new BooleanSetting("Chat", true);
    private final BooleanSetting notification = new BooleanSetting("Notification", true);

    boolean doChat = chat.isEnabled();
    boolean doNotification = notification.isEnabled();



    public AutoDisable() {
        super("AutoDisable", 0, Category.OTHER, AutoDisable.NONE);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventTarget
    public void onJoinWorld(EventJoinWorld event) {

        Reload.instance.moduleManager.getModules().forEach(m -> {
            if(m.getAutoDisable() == AutoDisable.WORLD && m.isEnabled() && world.isEnabled()) {
                m.setEnabled(false);
                if(doChat)ChatUtils.sendChatMessageClientWithDelay(m.getName() + " was disabled because of world change", ChatUtils.Type.INFO, 1000);
                if(doNotification) NotificationManager.show(new Notification(NotificationType.INFO, this.getName(), m.getName() + " was disabled because of world change", 1));
            }
        });
    }

    @EventTarget
    public void onRespawn(EventRespawn event) {
        Reload.instance.moduleManager.getModules().forEach(m -> {
            if(m.getAutoDisable() == AutoDisable.RESPAWN && m.isEnabled() && respawn.isEnabled()) {
                m.setEnabled(false);
                if(doChat)ChatUtils.sendChatMessageClient(m.getName() + " was disabled because of respawn", ChatUtils.Type.INFO);
                if(doNotification) NotificationManager.show(new Notification(NotificationType.INFO, this.getName(), m.getName() + " was disabled because of respawn", 1));

            }
        });
    }

    @EventTarget
    public void onRecivePacket(EventRecivePacket packet) {
        if(packet.getPacket() instanceof S08PacketPlayerPosLook) {
            Reload.instance.moduleManager.getModules().forEach(m -> {
                if(m.getAutoDisable() == AutoDisable.FLAG && m.isEnabled() && flag.isEnabled()) {
                    m.toggle(false);
                    if(doChat)ChatUtils.sendChatMessageClient(m.getName() + " was disabled because of flag", ChatUtils.Type.WARN  );
                    if(doNotification) NotificationManager.show(new Notification(NotificationType.WARNING, this.getName(), m.getName() + " was disabled because of flag", 1));

                }
            });
        }
    }
    //tsdgjvm,sjdmfgjsg8dig9dsg,gggi,.0-9ggk,9kghk90kdf90rsysuxr5 - Bordie 2022
}
