package com.virtutuile.domaine.systeme.components;

import com.virtutuile.domaine.systeme.units.VCoordinate;

import java.awt.*;

public class VRectShape extends VShape {
    private VCoordinate _origin;
    private VCoordinate _opposite;

    public VRectShape(Rectangle.Double rect, boolean isHole) {
        super(isHole);
        _polygon.moveTo(rect.x, rect.y);
        _polygon.lineTo(rect.x + rect.width, rect.y);
        _polygon.lineTo(rect.x + rect.width, rect.y + rect.height);
        _polygon.lineTo(rect.x, rect.y + rect.height);
        _polygon.closePath();
        _origin = new VCoordinate(rect.x, rect.y);
        _opposite = new VCoordinate(rect.x + rect.width, rect.y + rect.height);
    }

    public VCoordinate origin() {
        return this._origin;
    }

    public void origin(VCoordinate origin) {
        this._origin = origin;
    }

    public VCoordinate opposite() {
        return this._opposite;
    }

    public void opposite(VCoordinate opposite) {
        this._opposite = opposite;
    }
}
