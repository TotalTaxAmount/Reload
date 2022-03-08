package com.invisiblecat.reload.module;

import java.util.ArrayList;

public class ModuleManager {
    private ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        //Combat

        //Movement

        //Player

        //World

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
