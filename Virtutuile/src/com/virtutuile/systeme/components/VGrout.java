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

    public Color color() {
        return this._color;
    }

    public void color(Color color) {
        this._color = color;
    }

    public float thickness() {
        return this._thickness;
    }

    public void thickness(float thickness) {
        this._thickness = thickness;
    }

    public float height() {
        return this._height;
    }

    public void height(float height) {
        this._height = height;
    }
}