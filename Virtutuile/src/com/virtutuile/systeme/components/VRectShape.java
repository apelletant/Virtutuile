package com.virtutuile.systeme.components;

import com.virtutuile.systeme.units.VCoordinate;

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

    public VCoordinate getOrigin() {
        return this._origin;
    }

    public void setOrigin(VCoordinate origin) {
        this._origin = origin;
    }

    public VCoordinate getOpposite() {
        return this._opposite;
    }

    public void setOpposite(VCoordinate opposite) {
        this._opposite = opposite;
    }
}
