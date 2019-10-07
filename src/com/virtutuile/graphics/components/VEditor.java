package com.virtutuile.graphics.components;

import com.virtutuile.constants.UIConstants;
import com.virtutuile.engine.VEditorEngine;
import com.virtutuile.graphics.wrap.MouseEventKind;
import com.virtutuile.graphics.wrap.VPanelEvents;

import java.awt.*;

class VEditor extends VPanelEvents {
    Point p;
    VEditorEngine ee;

    VEditor(VEditorEngine ee) {
        this.setBackground(UIConstants.DRAW_BACKGROUND);
        this.setName("Toolbar");
        this.setBorder(null);
        this.ee = ee;

        addEventListener(MouseEventKind.MousePress, (me) -> {
            ee.mouseLClick(me.getPoint());
            repaint();
        });
        ;

        addEventListener(MouseEventKind.MouseMove, (me) -> {
            ee.setMouse(me.getPoint());
            repaint();
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ee.paint(g);
    }
}
