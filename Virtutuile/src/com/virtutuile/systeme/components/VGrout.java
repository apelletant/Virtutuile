package com.virtutuile.systeme.components;

import java.awt.*;

public class VGrout {
    private Color _color;
    private float _thickness;
    private float _height;

    public VGrout(Color color, float thickness, float height) {
        _color = color;
        _thickness = thickness;
        _height = height;
    }

    public Color getColor() {
        return this._color;
    }

    public void setColor(Color color) {
        this._color = color;
    }

    public float getThickness() {
        return this._thickness;
    }

    public void setThickness(float thickness) {
        this._thickness = thickness;
    }

    public float getHeight() {
        return this._height;
    }

    public void setHeight(float height) {
        this._height = height;
    }
}
