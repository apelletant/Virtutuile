package com.virtutuile.engine;

import javax.swing.text.Position;
import java.awt.*;
import java.util.Vector;

public class VPhysicalEngine {

    public boolean canAddPoint(Vector<Point> points, Point point) {
        return true;
    }

    /**
     * Convert a position into pixel coordinates
     * @return
     */
    public Point fromGeoToPixel(Point point) {
        return point;
    }

    public Point fromPixelToGeo(Point point) {
        return point;
    }
}
