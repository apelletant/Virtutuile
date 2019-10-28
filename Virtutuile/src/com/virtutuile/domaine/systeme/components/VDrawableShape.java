package com.virtutuile.domaine.systeme.components;

import com.virtutuile.domaine.systeme.constants.UIConstants;

import java.awt.*;

public class VDrawableShape {
    private Polygon _polygon = new Polygon();
    private Color _fillColor = UIConstants.DEFAULT_SHAPE_FILL_COLOR;
    private Color _borderColor = new Color(0, 0, 0);
    private int _borderThickness = UIConstants.DEFAULT_SHAPE_BORDER_THICKNESS;
    private boolean _isActive;
    private boolean _isMouseHovered;

    public VDrawableShape() {
        _borderColor = new Color(~_fillColor.getRGB());
    }

    public VDrawableShape(Point[] vertices) {
        for (Point vertice : vertices) {
            _polygon.addPoint(vertice.x, vertice.y);
        }
        _borderColor = new Color(~_fillColor.getRGB());
    }

    public VDrawableShape(Polygon polygon, Color fillColor, Color borderColor) {
        _polygon = polygon;
        _fillColor = fillColor;
        _borderColor = new Color(~_fillColor.getRGB());
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
        _borderColor = new Color(~_fillColor.getRGB());
    }

    public Color borderColor() {
        return this._borderColor;
    }

    public void borderColor(Color borderColor) {
        this._borderColor = borderColor;
    }

    public int getBorderThickness() {
        return this._borderThickness;
    }

    public void setBorderThickness(int borderThickness) {
        this._borderThickness = borderThickness;
    }

    public void setMouseHovered(boolean isMouseHovered) {
        _isMouseHovered = isMouseHovered;
    }

    public boolean isMouseHovered() {
        return _isMouseHovered;
    }

    public void setActive(boolean isActive) {
        _isActive = isActive;
    }

    public boolean isActive() {
        return _isActive;
    }
}
