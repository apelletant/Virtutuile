package com.virtutuile.systeme.components;

import com.virtutuile.systeme.units.VCoordinate;

import java.util.Vector;

public class VPatternSpec {
    private VPattern _pattern;
    private Vector<VTileSpec> _tiles;
    private float _rotation;
    private VCoordinate _coordinate;

    public VPattern getPattern() {
        return this._pattern;
    }

    public void setPattern(VPattern pattern) {
        this._pattern = pattern;
    }

    public Vector<VTileSpec> getTiles() {
        return this._tiles;
    }

    public void setTiles(Vector<VTileSpec> tiles) {
        this._tiles = tiles;
    }

    public float getRotation() {
        return this._rotation;
    }

    public void setRotation(float rotation) {
        this._rotation = rotation;
    }

    public VCoordinate getCoordinate() {
        return this._coordinate;
    }

    public void setCoordinate(VCoordinate coordinate) {
        this._coordinate = coordinate;
    }
}
