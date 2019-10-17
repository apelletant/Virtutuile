package com.virtutuile.systeme.components;

import java.awt.*;

public class VDrawableShape {
    private Polygon _polygon;
    private Color _fillColor;
    private Color _borderColor;

    public VDrawableShape(Polygon polygon, Color fillColor, Color borderColor) {
        _polygon = polygon;
        _fillColor = fillColor;
        _borderColor = borderColor;
    }

    public Polygon polygon() {
        return this._polygon;
    }

    public void polygon(Polygon polygon) {
        this._polygon = polygon;
    }

    public Color fillColor() {
        return this._fillColor;
    }

    public void fillColor(Color fillColor) {
        this._fillColor = fillColor;
    }

    public Color borderColor() {
        return this._borderColor;
    }

    public void borderColor(Color borderColor) {
        this._borderColor = borderColor;
    }
}
