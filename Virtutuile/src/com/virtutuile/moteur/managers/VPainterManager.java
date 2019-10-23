package com.virtutuile.moteur.managers;

import com.virtutuile.moteur.VPainter;

import java.awt.*;

public class VPainterManager {
    private VPainterManager() {
    }

    private static VPainterManager _instance = null;

    public VPainter getPainter(Graphics graphics) {
        return new VPainter(graphics);
    }

    public static VPainterManager getInstance() {
        if (_instance == null) {
            _instance = new VPainterManager();
        }
        return _instance;
    }
}
