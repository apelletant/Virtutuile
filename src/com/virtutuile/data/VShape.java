package com.virtutuile.data;

import javafx.geometry.Bounds;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.Vector;

public abstract class VShape {
    private Vector<Point> _points = new Vector<>();

    abstract public Rectangle getBounds();

    abstract public boolean canRotate(Point origin, float angle);

    abstract public void rotate(Point origin, float angle);

    abstract public void setPoint(Point point);

    abstract public void viewPoint(Point point);

    abstract public void draw(Graphics gfx);

    abstract public Rectangle updateBounds();

    public Vector<Point> GetCoordinates() {
        return _points;
    }

    public abstract boolean isDone();
}
