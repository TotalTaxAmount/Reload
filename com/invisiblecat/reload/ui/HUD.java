package com.invisiblecat.reload.ui;

import com.invisiblecat.reload.ui.elements.Watermark;

import java.util.ArrayList;

public class HUD {
    private ArrayList<Element> elements = new ArrayList<Element>();
    public HUD() {
        elements.add(new Watermark());
    }

    public void render() {
        for (Element element : elements) {
            element.render();
        }

    }
}
