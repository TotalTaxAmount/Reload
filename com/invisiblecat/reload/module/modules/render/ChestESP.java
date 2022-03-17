package com.invisiblecat.reload.module.modules.render;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.Event3D;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.utils.render.RenderUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

public class ChestESP extends Module {

    public ChestESP() {
        super("ChestEsp", 0, Category.RENDER, AutoDisable.NONE);
    }

    @EventTarget
    public void onEvent3D(Event3D event) {
        for (TileEntity o : mc.theWorld.loadedTileEntityList) {
            if(o instanceof TileEntityChest) {
//                RenderUtils.blockESPBox(o.getPos());
                RenderUtils.blockESPBox(o.getPos());
            }
        }
    }

}
