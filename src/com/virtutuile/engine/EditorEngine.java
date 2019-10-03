package com.virtutuile.engine;

import java.awt.*;
import java.util.Vector;

public class EditorEngine {

    Vector<Point> _points = new Vector<>();
    Point _mouse = new Point();
    PhysicalEngine _pe = null;

    public EditorEngine(PhysicalEngine _pe) {
        this._pe = _pe;
    }

    public void paint(Graphics gfx) {
        Point last = null;
        for (Point p: _points) {
            p = _pe.fromGeoToPixel(p);
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
        if (_pe.canAddPoint(_points, _pe.fromPixelToGeo(point)))
            this._points.add(point);
    }
}
