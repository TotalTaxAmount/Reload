package com.invisiblecat.reload.client.ui.clickgui.reload;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.utils.font.CustomFontUtil;
import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen {

    private TTFFontRenderer fontRenderer = CustomFontUtil.FONT_MANAGER.getFont("idk 18");

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

    }
}
