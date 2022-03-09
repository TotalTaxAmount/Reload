package com.invisiblecat.reload.module;

import com.invisiblecat.reload.module.modules.movement.*;
import com.invisiblecat.reload.module.modules.render.*;
import com.invisiblecat.reload.module.modules.player.*;

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
        modules.add(new Fullbright());

        //Exploit

        //Other

        //None

    }
    public ArrayList<Module> getModules() {
        return modules;
    }
    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
