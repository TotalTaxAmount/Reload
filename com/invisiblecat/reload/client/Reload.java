package com.invisiblecat.reload.client;

import com.invisiblecat.reload.client.mainmenu.alt.DevAltManager;
import com.invisiblecat.reload.client.ui.clickgui.csgo.CSGOGui;
import com.invisiblecat.reload.client.ui.hud.element.GuiHUDConfig;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;





public class Reload {
    public String clientName = "Reload", creates = "InvisibleCat#0001 and Cosmics#0001";
    public double version = 0.1;

    public static Reload instance = new Reload();
    public Logger reloadLogger = LogManager.getLogger("Reload");
    public EventManager eventManager;
    public ModuleManager moduleManager;
    public FileManager fileManager;
    public CommandManager commandManager;
    public DiscordRP discordRP;
    public DevAltManager devAltManager;

    public HUD hud;
    public CSGOGui csgogui;


    public void Start() {
        commandManager = new CommandManager();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        fileManager = new FileManager();
        devAltManager = new DevAltManager();
        hud = new HUD();
        discordRP = new DiscordRP();
        csgogui = new CSGOGui();

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
