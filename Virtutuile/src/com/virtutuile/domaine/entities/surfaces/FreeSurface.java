package com.virtutuile.domaine.entities.surfaces;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class FreeSurface extends Surface {

    private FreeSurface() {
        super();
    }

    public FreeSurface(boolean hole, Polygon polygon) {
        super(hole);
    }

    public FreeSurface(Path2D.Double polygon) {
        super(polygon, false);
    }

    public FreeSurface(FreeSurface surface) {
        super(surface);
    }

    public static SurfaceBuilder getBuilder() {
        return new Builder();
    }

    public static class Builder extends SurfaceBuilder {

        private FreeSurface surface;
        private Point2D firstPoint = null;
        private Point2D currentPoint = null;

        Builder() {
            surface = new FreeSurface();
        }

        @Override
        public void placePoint(Point2D point) {
            if (firstPoint == null) {
                surface.polygon.moveTo(point.getX(), point.getY());
                firstPoint = point;
            } else {
                surface.polygon.lineTo(point.getX(), point.getY());
            }
            currentPoint = point;
        }

        @Override
        public void movePoint(Point2D point2D) {
            currentPoint = point2D;
        }

        @Override
        public void makeHole(boolean isHole) {
            surface.isHole = true;
        }

        @Override
        public Surface getSurface() {
            FreeSurface ret = null;
            if (surface != null) {
                ret = new FreeSurface(surface);
                if (currentPoint != null)
                    ret.polygon.lineTo(currentPoint.getX(), currentPoint.getY());
                ret.polygon.closePath();
            }
            return ret;
        }
    }
}
