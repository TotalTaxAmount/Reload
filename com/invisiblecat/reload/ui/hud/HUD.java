package com.invisiblecat.reload.ui.hud;

import com.invisiblecat.reload.ui.hud.elements.*;

import java.util.ArrayList;

public class HUD {
    private ArrayList<Element> elements = new ArrayList<Element>();
    public HUD() {
        elements.add(new Watermark());
        elements.add(new ArrayListModules());
    }

    public void render() {
        for (Element element : elements) {
            element.render();
        }

    }
}
