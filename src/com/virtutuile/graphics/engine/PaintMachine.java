package com.virtutuile.graphics.engine;

import java.awt.*;
import java.util.Vector;

public class PaintMachine {

    Vector<Point> _points = new Vector<>();
    Point _mouse = new Point();

    public void paint(Graphics gfx) {
        Point last = null;
        for (Point p: _points) {
            if (last != null) {
                gfx.drawLine(last.x, last.y, p.x, p.y);
            }
            last = p;
        }
        if (last != null && _mouse != null) {
            gfx.drawLine(last.x, last.y, _mouse.x, _mouse.y);
        }
    }

    public void setMouse(Point point) {
        this._mouse = point;
    }

    public void addPoint(Point point) {
        this._points.add(point);
    }
}
