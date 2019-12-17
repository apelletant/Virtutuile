package com.virtutuile.domaine.entities.surfaces;

import com.virtutuile.domaine.entities.tools.PolygonTransformer;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class RectangularSurface extends Surface implements Cloneable {

    private Point2D origin;
    private Point2D opposite;

    public RectangularSurface(Point[] points) {
        super(points);
        origin = new Point2D.Double(points[0].x, points[0].y);
        opposite = new Point2D.Double(points[2].x, points[2].y);
    }

    public RectangularSurface(Point[] points, boolean isHole) {
        super(points, isHole);
        origin = new Point2D.Double(points[0].x, points[0].y);
        opposite = new Point2D.Double(points[2].x, points[2].y);
    }

    public RectangularSurface(Rectangle2D rectangle2D, boolean isHole) {
        super(isHole);
        origin = new Point2D.Double(rectangle2D.getX(), rectangle2D.getY());
        opposite = new Point2D.Double(rectangle2D.getX() + rectangle2D.getWidth(), rectangle2D.getY() + rectangle2D.getHeight());
    }

    public RectangularSurface(RectangularSurface surface) {
        super(surface);
        opposite = surface.getOpposite();
        origin = surface.getOrigin();
    }

    public Point2D getOrigin() {
        return origin;
    }

    public void setOrigin(Point2D origin) {
        this.origin = origin;
    }

    public Point2D getOpposite() {
        return opposite;
    }

    public void setOpposite(Point2D opposite) {
        this.opposite = opposite;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    private void regenerate() {
        polygon = new Path2D.Double();
        polygon.moveTo(origin.getX(), origin.getY());
        polygon.lineTo(origin.getX(), opposite.getY());
        polygon.lineTo(opposite.getX(), opposite.getY());
        polygon.lineTo(opposite.getX(), origin.getY());
        polygon.closePath();
    }

    public static class Builder extends SurfaceBuilder {
        private RectangularSurface surface;
        private Point2D firstPoint;

        Builder() {
            surface = new RectangularSurface(new Rectangle2D.Double(0, 0, 0, 0), false);
            surface.fillColor = new Color((int) (Math.random() * Integer.MAX_VALUE));
        }

        @Override
        public void placePoint(Point2D point2D) {
            if (firstPoint == null) {
                surface.origin = point2D;
                firstPoint = point2D;
            } else {
                surface.opposite = point2D;
            }
            surface.opposite = point2D;
            surface.regenerate();
        }

        @Override
        public void movePoint(Point2D point2D) {
            surface.opposite = point2D;
            surface.regenerate();
        }

        @Override
        public void makeHole(boolean isHole) {
            surface.isHole = isHole;
        }

        @Override
        public Surface getSurface() {
            return new RectangularSurface(surface);
        }
    }

}
