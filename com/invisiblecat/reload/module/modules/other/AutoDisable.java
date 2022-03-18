package com.invisiblecat.reload.module.modules.other;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventJoinWorld;
import com.invisiblecat.reload.event.events.EventRecivePacket;
import com.invisiblecat.reload.event.events.EventRespawn;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class AutoDisable extends Module {

    private final BooleanSetting world = new BooleanSetting("World", true);
    private final BooleanSetting respawn = new BooleanSetting("Respawn", true);
    private final BooleanSetting flag = new BooleanSetting("Flag", true);



    public AutoDisable() {
        super("AutoDisable", 0, Category.OTHER, AutoDisable.NONE);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventTarget
    public void onJoinWorld(EventJoinWorld event) {
        Reload.instance.moduleManager.getModules().forEach(m -> {
            if(m.getAutoDisable() == AutoDisable.WORLD && m.isToggled() && world.isEnabled()) {
                m.setToggled(false);
                ChatUtils.sendChatMessageClientWithDelay(m.getName() + " was disabled because of world change", ChatUtils.Type.INFO, 1000);
            }
        });
    }

    @EventTarget
    public void onRespawn(EventRespawn event) {
        Reload.instance.moduleManager.getModules().forEach(m -> {
            if(m.getAutoDisable() == AutoDisable.RESPAWN && m.isToggled() && respawn.isEnabled()) {
                m.setToggled(false);
                ChatUtils.sendChatMessageClient(m.getName() + " was disabled because of respawn", ChatUtils.Type.INFO);
            }
        });
    }

    @EventTarget
    public void onRecivePacket(EventRecivePacket packet) {
        if(packet.getPacket() instanceof S08PacketPlayerPosLook) {
            Reload.instance.moduleManager.getModules().forEach(m -> {
                if(m.getAutoDisable() == AutoDisable.FLAG && m.isToggled() && flag.isEnabled()) {
                    m.setToggled(false);
                    ChatUtils.sendChatMessageServer(m.getName() + " was disabled because of flag");
                }
            });
        }
    }
    //tsdgjvm,sjdmfgjsg8dig9dsg,gggi,.0-9ggk,9kghk90kdf90rsysuxr5 - Bordie 2022
}
