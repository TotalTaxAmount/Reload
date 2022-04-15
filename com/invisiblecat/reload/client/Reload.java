package com.invisiblecat.reload.client;

import com.invisiblecat.reload.client.ui.hud.GuiHUDConfig;
import com.invisiblecat.reload.command.CommandManager;
import com.invisiblecat.reload.discord.DiscordRP;
import com.invisiblecat.reload.event.EventManager;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.file.FileManager;
import com.invisiblecat.reload.module.ModuleManager;
import com.invisiblecat.reload.event.events.EventKey;
import com.invisiblecat.reload.client.ui.hud.HUD;
import com.invisiblecat.reload.utils.HWID;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;





public class Reload {
    public String clientName = "Reload", version = "0.1", creates = "InvisibleCat#0001 and Cosmics#0001";

    public static Reload instance = new Reload();
    public Logger reloadLogger = LogManager.getLogger("Reload");
    public EventManager eventManager;
    public ModuleManager moduleManager;
    public FileManager fileManager;
    public CommandManager commandManager;
    public DiscordRP discordRP;

    public HUD hud;


    public void Start() {
        commandManager = new CommandManager();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        fileManager = new FileManager();
        hud = new HUD();
        discordRP = new DiscordRP();

        discordRP.init();

        Display.setTitle(clientName + " b" + version);
        fileManager.loadOld();
        reloadLogger.info("[Reload] Boot up complete");
        reloadLogger.info("[Reload] HWID: " + HWID.getHWID());

        eventManager.register(this);
    }
    public void stop() {
        reloadLogger.info("Shutting down...");
        fileManager.save();

        EventManager.unregister(this);
        discordRP.stop();
    }

    @EventTarget
    public void onKey(EventKey event) {
        moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(m -> m.toggle(true));
        if (event.getKey() == Minecraft.getMinecraft().gameSettings.HUD_CONFIG.getKeyCode()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiHUDConfig());
        }
    }

}
