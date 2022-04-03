package com.invisiblecat.reload.discord;

import com.invisiblecat.reload.client.Reload;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class DiscordRP {

    private boolean running = true;
    private long created = 0;

    public void init() {

        this.created = System.currentTimeMillis();

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(DiscordUser discordUser) {
                //System.out.println("Discord connected to user " + discordUser.username + "#" + discordUser.discriminator);
                Reload.instance.reloadLogger.info("Discord connected to user " + discordUser.username + "#" + discordUser.discriminator);
            }
        }).build();

        DiscordRPC.discordInitialize("953465658048393256", handlers, true);

        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while (running) {
                    if(Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) {
                        update("Main Menu", "Idle");
                    }
                    DiscordRPC.discordRunCallbacks();
                }
            }


        }.start();

    }
    public void stop() {
        running = false;
        DiscordRPC.discordShutdown();
    }
    public void update(String line1, String line2) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(line2);
        b.setBigImage("large", "Reload");
        b.setDetails(line1);
        b.setStartTimestamps(created);
        DiscordRPC.discordUpdatePresence(b.build());
    }
}
