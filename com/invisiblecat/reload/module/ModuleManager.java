package com.invisiblecat.reload.module;

import com.invisiblecat.reload.module.modules.combat.Velocity;
import com.invisiblecat.reload.module.modules.movement.*;
import com.invisiblecat.reload.module.modules.exploit.*;
import com.invisiblecat.reload.module.modules.other.*;
import com.invisiblecat.reload.module.modules.player.FastPlace;
import com.invisiblecat.reload.module.modules.render.*;
import com.invisiblecat.reload.module.modules.test.*;

import java.util.ArrayList;

public class ModuleManager {
    private final ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        //Combat
        modules.add(new Velocity());

        //Movement
        modules.add(new Sprint());
        modules.add(new Fly());
        modules.add(new InvMove());

        //Player
        modules.add(new FastPlace());

        //World

        //Render
        modules.add(new ChestESP());
        modules.add(new Fullbright());
        modules.add(new Chams());

        //Exploit
        modules.add(new Kick());

        //Other
        modules.add(new AutoLogin());
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
}
