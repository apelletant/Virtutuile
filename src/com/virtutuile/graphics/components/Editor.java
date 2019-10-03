package com.virtutuile.graphics.components;

import com.virtutuile.constants.UIConstants;
import com.virtutuile.engine.EditorEngine;
import com.virtutuile.graphics.wrap.JPanelEventListened;
import com.virtutuile.graphics.wrap.MouseEventKind;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class Editor extends JPanelEventListened implements MouseListener,
        MouseMotionListener {
    Point p;
    EditorEngine ee;

    public Editor(EditorEngine ee) {
        this.setBackground(UIConstants.DRAW_BACKGROUND);
        this.setName("Toolbar");
        this.setBorder(null);
        this.ee = ee;

        addEventListener(MouseEventKind.MousePress, (me) -> {
            ee.addPoint(me.getPoint());
            this.revalidate();
        });

        addEventListener(MouseEventKind.MouseMove, (me) -> {
            ee.setMouse(me.getPoint());
            revalidate();
            repaint();
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ee.paint(g);
    }
}
