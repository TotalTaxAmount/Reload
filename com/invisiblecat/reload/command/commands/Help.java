
package com.invisiblecat.reload.command.commands;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.command.Command;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;

public class Help extends Command {
    private final ArrayList<String> aliases = new ArrayList<>(Arrays.asList("h"));

    public Help() {
        super("help", "Displays this page", ".help");
        this.setAliases(aliases);
    }

    @Override
    public void onCommand(String[] args, String command) {
        StringBuilder text = new StringBuilder("Help Menu\n");
        //int lines = 0;
        for(Command c : Reload.instance.commandManager.getCommands()) {
            text.append(c.getDisplayName())
                    .append(": ")
                    .append(c.getDescription())
                    .append(", ")
                    .append(ChatFormatting.GRAY)
                    .append("(Usage: ")
                    .append(c.getSyntax())
                    .append(")\n");
        }
       // lines = text.toString().split("\r\n|\r|\n").length;
//        if (lines - 1 > 6) {
//            StringBuilder page1 = new StringBuilder(text);
//            if (page1.length() > 0) {
//                int last, prev = page1.length() - 1;
//                while ((last = page1.lastIndexOf("\n", prev)) == prev) {
//                    prev = last - 1;
//                }
//                if (last >= 0) {
//                    page1.delete(last, page1.length());
//                }
//                System.out.println(page1);
//            }
//        }
        ChatUtils.sendChatMessageClient(text.toString(), ChatUtils.Type.INFO);
    }
}
