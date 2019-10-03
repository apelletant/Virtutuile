package com.virtutuile.graphics.components;

import com.virtutuile.constants.UIConstants;
import com.virtutuile.engine.VEditorEngine;
import com.virtutuile.graphics.components.panels.VPanel;
import com.virtutuile.graphics.wrap.MouseEventKind;

import java.awt.*;

class VEditor extends VPanel {
    Point p;
    VEditorEngine ee;

    VEditor(VEditorEngine ee) {
        this.setBackground(UIConstants.DRAW_BACKGROUND);
        this.setName("Toolbar");
        this.setBorder(null);
        this.ee = ee;

        addEventListener(MouseEventKind.MouseDbClick, (me) -> {
            ee.mouseDbClick(me.getPoint());
            repaint();
        });

        addEventListener(MouseEventKind.MouseClick, (me) -> {
            ee.mouseClick(me.getPoint());
            repaint();
        });

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
