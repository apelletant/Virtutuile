package com.virtutuile.systeme.components;

import java.awt.*;

public class VGrout {
    private Color _color;
    private double _thickness;

    //TODO: déplacer la défiition du thickness #edition du coulis
    public VGrout(/*Color color, float thickness, float height*/) {
        /*_color = color;
        _thickness = thickness;
        _height = height;*/
        this._thickness = 2.5;
    }

    public Color getColor() {
        return this._color;
    }

    public void setColor(Color color) {
        this._color = color;
    }

    public double getThickness() {
        return this._thickness;
    }

    public void setThickness(double thickness) {
        this._thickness = thickness;
    }
}
