package com.invisiblecat.reload.client.ui.hud.element.elements;

import com.invisiblecat.reload.client.ui.hud.HUD;
import com.invisiblecat.reload.client.ui.hud.element.Element;
import com.invisiblecat.reload.utils.font.CustomFontUtil;
import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;

import java.awt.*;

public class User extends Element {
    private TTFFontRenderer font = CustomFontUtil.FONT_MANAGER.getFont("idk 18");

    public User() {
        super("IGN", 0, 590, 0, 0);
    }

    @Override
    public void render() {
//        font.drawString("User: " + mc.getSession().getUsername(), this.getX(), this.getY(), Color.WHITE.getRGB());
        font.drawString("User: ", this.getX(), this.getY(), HUD.getClientColor().getRGB());
        font.drawString(mc.getSession().getUsername(), this.getX() + font.getWidth("User: "), this.getY(), Color.WHITE.getRGB());

        this.draggable.setWidth((int) (font.getWidth("User: ") + font.getWidth(mc.getSession().getUsername())));
        this.draggable.setHeight((int) font.getHeight());
    }
}
