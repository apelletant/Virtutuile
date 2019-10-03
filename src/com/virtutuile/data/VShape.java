package com.virtutuile.data;

import javafx.geometry.Bounds;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.Vector;

public abstract class VShape {
    private Vector<Point> _points = new Vector<>();

    abstract public Rectangle getBounds();

    abstract public boolean canRotate(Point origin, float angle);

    abstract public boolean rotate(Point origin, float angle);

    public Vector<Point> GetCoordinates() {
        return _points;
    }
}
