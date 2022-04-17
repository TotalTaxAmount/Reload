package com.invisiblecat.reload.module.modules.test;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import org.lwjgl.input.Keyboard;

public class Test extends Module {

    public Test() {
        super("Test", Keyboard.KEY_NONE, Category.NONE, AutoDisable.WORLD);
    }
    @Override
    public void onEnable() {
        ChatUtils.sendChatMessageServer("test");
        ChatUtils.sendChatMessageClient("Send msg lol idk sajid", ChatUtils.Type.INFO);
    }


}
