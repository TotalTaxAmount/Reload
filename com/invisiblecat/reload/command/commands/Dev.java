package com.invisiblecat.reload.command.commands;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.command.Command;

import java.util.ArrayList;
import java.util.Collections;

public class Dev extends Command {
    private final ArrayList<String> aliases = new ArrayList<>(Collections.singletonList("d"));

    public Dev() {
        super("dev", "Dev cmd does random test things", ".dev");
        this.setAliases(aliases);
    }

    @Override
    public void onCommand(String[] args, String command) {
        Reload.instance.fileManager.loadOld();
    }
}
