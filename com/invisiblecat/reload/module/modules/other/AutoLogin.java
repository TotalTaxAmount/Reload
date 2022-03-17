package com.invisiblecat.reload.module.modules.other;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventJoinWorld;
import com.invisiblecat.reload.event.events.EventRecivePacket;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.Timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AutoLogin extends Module {
    private boolean login = false;

    public AutoLogin() {
        super("AutoLogin", 0, Category.OTHER, AutoDisable.NONE);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        login = false;
    }
    @EventTarget
    public void  onJoinWorld(EventJoinWorld event) {
        login = false;
    }

    @EventTarget
    public void onRecivePacket(EventRecivePacket event) {
        if(login)
            return;
        Packet packet = event.getPacket();

        if(packet instanceof S45PacketTitle) {
            if(((S45PacketTitle) packet).getMessage() == null)
                return;
            processMsg(((S45PacketTitle) packet).getMessage().getUnformattedText());
        }
        if(packet instanceof S02PacketChat) {
            processMsg(((S02PacketChat) packet).getChatComponent().getUnformattedText());
        }
    }

    private void processMsg(String msg) {
        if(msg.contains("/register")) {
            delatedMessage("/register yes12345 yes12345");
        }
        if(msg.contains("/login")) {
            delatedMessage("/login yes12345");
        }
    }

    private void delatedMessage(String msg) {
        login = true;
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> ChatUtils.sendChatMessageServer(msg), 1000, TimeUnit.MILLISECONDS);
    }

}
