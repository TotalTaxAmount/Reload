package com.invisiblecat.reload.module.modules.other;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventJoinWorld;
import com.invisiblecat.reload.event.events.EventRecivePacket;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.setting.settings.StringSetting;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;

public class AutoLogin extends Module {
    private boolean login = false;

    private StringSetting regCmd = new StringSetting("Register", "/register {p} {p}");
    private StringSetting loginCmd = new StringSetting("Login", "/login {p}");
    private StringSetting password = new StringSetting("Password", "yes12345");
    private NumberSetting delay = new NumberSetting("delay", 1000, 10, 2000, 10);



    public AutoLogin() {
        super("AutoLogin", 0, Category.OTHER, AutoDisable.NONE);
        this.addSettings(regCmd, loginCmd);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        login = false;
    }
    @EventTarget
    public void  onJoinWorld(EventJoinWorld event) {
        login = false;
    }

    @EventTarget
    public void onRecivePacket(EventRecivePacket event) {
        if(login)
            return;
        Packet packet = event.getPacket();

        if(packet instanceof S45PacketTitle) {
            if(((S45PacketTitle) packet).getMessage() == null)
                return;
            processMsg(((S45PacketTitle) packet).getMessage().getUnformattedText());
        }
        if(packet instanceof S02PacketChat) {
            processMsg(((S02PacketChat) packet).getChatComponent().getUnformattedText());
        }
    }

    private void processMsg(String msg) {
        if(!regCmd.getValue().isEmpty()) {
            String cmd = regCmd.getValue().split(" ")[0];
            if(!cmd.isEmpty() && msg.toLowerCase().contains(cmd.toLowerCase())) {
                ChatUtils.sendChatMessageServerWithDelay(regCmd.getValue().toLowerCase().replace("{p}", password.getValue()), delay.getValueInt());
            }
        }
        if(!loginCmd.getValue().isEmpty()) {
            String cmd = loginCmd.getValue().split(" ")[0];
            if(!cmd.isEmpty() && msg.toLowerCase().contains(cmd.toLowerCase())) {
                ChatUtils.sendChatMessageServerWithDelay(loginCmd.getValue().toLowerCase().replace("{p}", password.getValue()), delay.getValueInt());
            }
        }
//        if(msg.contains("/register")) {
//            delatedMessage("/register yes12345 yes12345");
//        }
//        if(msg.contains("/login")) {
//            delatedMessage("/login yes12345");
//        }
    }

//       private void delatedMessage(String msg) {
//        login = true;
//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        executorService.schedule(() -> ChatUtils.sendChatMessageServer(msg), delay.getValueInt(), TimeUnit.MILLISECONDS);
//    }

}
