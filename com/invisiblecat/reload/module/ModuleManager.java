package com.invisiblecat.reload.module;

import com.invisiblecat.reload.module.modules.combat.AntiBot;
import com.invisiblecat.reload.module.modules.combat.KillAura;
import com.invisiblecat.reload.module.modules.combat.Velocity;
import com.invisiblecat.reload.module.modules.movement.*;
import com.invisiblecat.reload.module.modules.exploit.*;
import com.invisiblecat.reload.module.modules.other.*;
import com.invisiblecat.reload.module.modules.player.InvManager;
import com.invisiblecat.reload.module.modules.player.Scaffold;
import com.invisiblecat.reload.module.modules.player.Stealer;
import com.invisiblecat.reload.module.modules.player.FastPlace;
import com.invisiblecat.reload.module.modules.render.*;
import com.invisiblecat.reload.module.modules.test.*;
import com.invisiblecat.reload.module.modules.world.Timer;

import java.util.ArrayList;

public class ModuleManager {
    private final ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        //Combat
        modules.add(new Velocity());
        modules.add(new KillAura());
        modules.add(new AntiBot());

        //Movement
        modules.add(new Sprint());
        modules.add(new Fly());
        modules.add(new InvMove());
        modules.add(new Speed());
        modules.add(new Jesus());

        //Player
        modules.add(new FastPlace());
        modules.add(new Stealer());
        modules.add(new InvManager());
        modules.add(new Scaffold());

        //World
        modules.add(new Timer());

        //Render
        modules.add(new ChestESP());
        modules.add(new Fullbright());
        modules.add(new HUDmodule());
        modules.add(new Animations());
        modules.add(new ClickGUIModule());

        //Exploit
        modules.add(new Disabler());
        modules.add(new Kick());

        //Other
        modules.add(new AutoCommand());
        modules.add(new AutoLogin());
        modules.add(new ClientSpoofer());
        modules.add(new AutoDisable());

        //None
        modules.add(new Test());

    }
    public ArrayList<Module> getModules() {
        return modules;
    }
    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    // create a getModuleByClass method
    public Module getModuleByClass(Class<? extends Module> clazz) {
        return modules.stream().filter(module -> module.getClass().equals(clazz)).findFirst().orElse(null);
    }
}
