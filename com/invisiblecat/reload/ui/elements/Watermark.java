package com.invisiblecat.reload.ui.elements;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.ui.Element;
import com.invisiblecat.reload.utils.ColorUtils;

public class Watermark extends Element {
    public Watermark() {
        super("watermark", 4, 4);
    }

    @Override
    public void render() {
        mc.fontRendererObj.drawString(String.valueOf(Reload.instance.clientName.charAt(0)), this.getX(), this.getY(), ColorUtils.Rainbow());
        mc.fontRendererObj.drawString(Reload.instance.clientName.substring(1) + " b" + Reload.instance.version, this.getX() + mc.fontRendererObj.getCharWidth(Reload.instance.clientName.charAt(0)), this.getY(), -1);
    }

}
