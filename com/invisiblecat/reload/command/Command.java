package com.invisiblecat.reload.command;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command {
    private String name, description, syntax;
    private ArrayList<String> aliases;

    public Command(String name, String description, String syntax) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = null;
    }

    public abstract void onCommand(String[] args, String command);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public ArrayList<String> getAliases() {
        return aliases;
    }
    public String getDisplayName() {
        return this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
    }

    public void setAliases(ArrayList<String> aliases) {
        this.aliases = aliases;
    }
    public void addAlias(String alias) {
        this.aliases.add(alias);
    }
}
