package com.virtutuile.systeme.components;

import com.virtutuile.systeme.units.VCoordinates;

import java.awt.*;

public class VRectShape extends VShape {
    private VCoordinates _origin;
    private VCoordinates _opposite;

    public VRectShape(Rectangle bounds, boolean hole, Polygon polygon) {
        super(bounds, hole, polygon);
    }

    public VCoordinates origin() {
        return this._origin;
    }

    public void origin(VCoordinates origin) {
        this._origin = origin;
    }

    public VCoordinates opposite() {
        return this._opposite;
    }

    public void opposite(VCoordinates opposite) {
        this._opposite = opposite;
    }
}
