package com.virtutuile.systeme.components;

import com.virtutuile.systeme.units.VDimensions;

import java.awt.*;

public class VTile extends VTileSpec {
    private VDimensions _dimensions;
    private Color _color;

    public VDimensions dimensions() {
        return this._dimensions;
    }

    public void dimensions(VDimensions dimensions) {
        this._dimensions = dimensions;
    }

    public Color color() {
        return this._color;
    }

    public void color(Color color) {
        this._color = color;
    }
}