package com.virtutuile.domaine.systeme.components;

import com.virtutuile.domaine.systeme.units.VCoordinate;

public class VTileSpec {
    private VCoordinate _position;
    private float _rotation;
    private VCoordinate _origin;

    public VCoordinate position() {
        return this._position;
    }

    public void position(VCoordinate position) {
        this._position = position;
    }

    public float rotation() {
        return this._rotation;
    }

    public void rotation(float rotation) {
        this._rotation = rotation;
    }

    public VCoordinate origin() {
        return this._origin;
    }

    public void origin(VCoordinate origin) {
        this._origin = origin;
    }
}
