package com.virtutuile.systeme.components;

import com.virtutuile.systeme.units.VCoordinates;

public class VTileSpec {
    private VCoordinates _position;
    private float _rotation;
    private VCoordinates _origin;

    public VCoordinates position() {
        return this._position;
    }

    public void position(VCoordinates position) {
        this._position = position;
    }

    public float rotation() {
        return this._rotation;
    }

    public void rotation(float rotation) {
        this._rotation = rotation;
    }

    public VCoordinates origin() {
        return this._origin;
    }

    public void origin(VCoordinates origin) {
        this._origin = origin;
    }
}
