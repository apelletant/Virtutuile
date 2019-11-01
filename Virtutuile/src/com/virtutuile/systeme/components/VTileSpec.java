package com.virtutuile.systeme.components;

import com.virtutuile.systeme.units.VCoordinate;

public class VTileSpec {
    private VCoordinate _position;
    private float _rotation;
    private VCoordinate _origin;

    public VCoordinate getPosition() {
        return this._position;
    }

    public void setPosition(VCoordinate position) {
        this._position = position;
    }

    public float getRotation() {
        return this._rotation;
    }

    public void setRotation(float rotation) {
        this._rotation = rotation;
    }

    public VCoordinate getOrigin() {
        return this._origin;
    }

    public void setOrigin(VCoordinate origin) {
        this._origin = origin;
    }
}
