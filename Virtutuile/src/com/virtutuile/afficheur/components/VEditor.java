package com.virtutuile.afficheur.components;

import com.virtutuile.system.constants.UIConstants;
import com.virtutuile.moteur.VEditorEngine;
import com.virtutuile.afficheur.wrap.MouseEventKind;
import com.virtutuile.afficheur.wrap.panels.VPanelEvents;

import java.awt.*;

public class VEditor extends VPanelEvents {
    Point p;
    VEditorEngine ee;

    public VEditor(VEditorEngine ee) {
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
