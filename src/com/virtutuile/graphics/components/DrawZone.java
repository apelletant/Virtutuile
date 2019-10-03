package com.virtutuile.graphics.components;

import com.virtutuile.constants.UIConstants;
import com.virtutuile.graphics.engine.PaintMachine;
import com.virtutuile.graphics.wrap.JPanelEventListened;
import com.virtutuile.graphics.wrap.MouseEventKind;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class DrawZone extends JPanelEventListened implements MouseListener,
        MouseMotionListener {
    Point p;
    PaintMachine pm;

    public DrawZone(PaintMachine pm) {
        this.setBackground(UIConstants.DRAW_BACKGROUND);
        this.setName("Toolbar");
        this.setBorder(null);
        this.pm = pm;

        addEventListener(MouseEventKind.MousePress, (me) -> {
            pm.addPoint(me.getPoint());
            this.revalidate();
        });

        addEventListener(MouseEventKind.MouseMove, (me) -> {
            pm.setMouse(me.getPoint());
            revalidate();
            repaint();
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        pm.paint(g);
    }
}
