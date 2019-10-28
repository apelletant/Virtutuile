package com.virtutuile.domaine.moteur.managers;

import com.virtutuile.domaine.moteur.VPainter;

import java.awt.*;

public class VPainterManager {
    public static final int GIZ_BOUNDS = 0x0001;

    private VPainterManager() {
    }

    private int _gizmos;

    public VPainter getPainter(Graphics graphics) {
        return new VPainter(graphics);
    }

    public boolean isGizmoActive(int gizmo) {
        return (this._gizmos & gizmo) != 0;
    }

    public int activeGizmos(int active) {
        this._gizmos |= active;
        return this._gizmos;
    }

    public int deactiveGizmos(int deactive) {
        this._gizmos &= (this._gizmos ^ deactive);
        return this._gizmos;
    }

    public int getGizmos() {
        return _gizmos;
    }

    // region Static management

    private static VPainterManager _instance = null;

    public static VPainterManager getInstance() {
        if (_instance == null) {
            _instance = new VPainterManager();
        }
        return _instance;
    }
    // endregion
}
