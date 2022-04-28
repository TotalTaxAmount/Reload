package com.invisiblecat.reload.client.ui.hud.element.elements;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.element.Element;
import com.invisiblecat.reload.module.modules.combat.KillAura;
import com.invisiblecat.reload.utils.font.CustomFontUtil;
import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;

import java.awt.*;

public class TargetHUD extends Element {
    private TTFFontRenderer font = CustomFontUtil.FONT_MANAGER.getFont("idk 18");

    public TargetHUD() {
        super("TargetHUD", 0, 0, 150, 75);
    }

    @Override
    public void render() {
        KillAura kaInstance = (KillAura) Reload.instance.moduleManager.getModuleByClass(KillAura.class);
        if (kaInstance.getTarget() != null) {
            drawTargetHUD(kaInstance.getTarget(), kaInstance.getTarget().getName(), kaInstance.getTarget().getHealth(), kaInstance.getTarget().getMaxHealth());
        }
    }


    @Override
    public void renderDrag(int mouseX, int mouseY) {
        super.renderDrag(mouseX, mouseY);
        drawTargetHUD(new EntitySheep(mc.theWorld),"Player", 20, 20);
    }

    private void drawTargetHUD(EntityLivingBase e, String name, float health, float maxHealth) {
        // draw a mostly transparent rectangle
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), new Color(0, 0, 0, 89).getRGB());

        // draw the name
        font.drawString(name, this.getX() + this.getWidth() / 2f - font.getWidth(name) / 2, this.getY() + this.getHeight() / 2f - font.getHeight(name) / 2, new Color(255, 255, 255, 255).getRGB());

    }


}
