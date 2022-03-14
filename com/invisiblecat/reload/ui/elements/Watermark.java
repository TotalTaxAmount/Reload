package com.invisiblecat.reload.ui.elements;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.ui.Element;

public class Watermark extends Element {
    public Watermark() {
        super("watermark", 4, 4);
    }

    @Override
    public void render() {
        mc.fontRendererObj.drawString(Reload.instance.clientName + " b" + Reload.instance.version, this.getX(), this.getY(), -1);
    }

}
