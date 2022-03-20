package com.invisiblecat.reload;

import com.invisiblecat.reload.command.CommandManager;
import com.invisiblecat.reload.discord.DiscordRP;
import com.invisiblecat.reload.event.EventManager;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.file.FileManager;
import com.invisiblecat.reload.module.ModuleManager;
import com.invisiblecat.reload.event.events.EventKey;
import com.invisiblecat.reload.ui.hud.HUD;
import org.lwjgl.opengl.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class Reload {
    public String clientName = "Reload", version = "0.1", creates = "InvisibleCat#0001 and Cosmics#0001";

    public static Reload instance = new Reload();
    public Logger reloadLogger = LoggerFactory.getLogger("[" + clientName + "]");
    public EventManager eventManager;
    public ModuleManager moduleManager;
    public HUD hud;
    public CommandManager commandManager;
    public DiscordRP discordRP;
    public FileManager fileManager;

    public void Start() {
        commandManager = new CommandManager();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        hud = new HUD();
        discordRP = new DiscordRP();
        fileManager = new FileManager();

        discordRP.init();

        Display.setTitle(clientName + " b" + version);
        reloadLogger.info("Boot up complete");

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
    }

}
