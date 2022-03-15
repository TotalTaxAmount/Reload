package com.invisiblecat.reload.module;

import com.invisiblecat.reload.module.modules.movement.*;
import com.invisiblecat.reload.module.modules.other.Kick;
import com.invisiblecat.reload.module.modules.render.*;
import com.invisiblecat.reload.module.modules.test.Test;
//import com.invisiblecat.reload.module.modules.player.*;

import java.util.ArrayList;

public class ModuleManager {
    private final ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        //Combat

        //Movement

        modules.add(new Sprint());
        //Player

        //World

        //Render
        modules.add(new ChestESP());
        modules.add(new Fullbright());

        //Exploit

        //Other
        modules.add(new Kick());

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
