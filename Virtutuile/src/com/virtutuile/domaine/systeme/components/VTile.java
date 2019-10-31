package com.virtutuile.domaine.systeme.components;

import com.virtutuile.domaine.systeme.units.VDimensions;

import java.awt.*;

public class VTile extends VTileSpec {
    private VDimensions _dimensions;
    private Color _color;

    public VDimensions getDimensions() {
        return this._dimensions;
    }

    public void setDimensions(VDimensions dimensions) {
        this._dimensions = dimensions;
    }

    public Color getColor() {
        return this._color;
    }

    public void setColor(Color color) {
        this._color = color;
    }
}