package com.virtutuile.domaine.entities.surfaces;

import com.virtutuile.afficheur.Constants;

import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;
import java.util.UUID;
import java.util.Vector;

public abstract class PrimarySurface implements Serializable {
    protected UUID id;
    protected Path2D.Double polygon;
    protected boolean isSelected;
    protected boolean isMouseHover;

    protected Color borderColor;
    protected Color fillColor;
    protected int borderThickness = Constants.DEFAULT_SHAPE_BORDER_THICKNESS;

    public PrimarySurface(Point[] points) {
        id = UUID.randomUUID();
        polygon = new Path2D.Double();
        isSelected = false;
        isMouseHover = false;
        fillColor = Constants.DEFAULT_SHAPE_FILL_COLOR;
        borderColor = new Color(~fillColor.getRGB());

        for (int i = 1; i <= points.length; ++i) {
            if (i == 1) {
                polygon.moveTo(points[i].x, points[i].y);
            } else {
                polygon.lineTo(points[i].x, points[i].y);
            }
        }
        polygon.closePath();
    }

    public PrimarySurface(PrimarySurface surface) {
        id = UUID.randomUUID();
        polygon = (Path2D.Double) surface.polygon.clone();
        isSelected = surface.isSelected;
        isMouseHover = surface.isMouseHover;
        fillColor = new Color(surface.fillColor.getRGB());
        borderColor = new Color(surface.borderColor.getRGB());
    }

    public PrimarySurface() {
        id = UUID.randomUUID();
        polygon = new Path2D.Double();
        isSelected = false;
        isMouseHover = false;
        fillColor = Constants.DEFAULT_SHAPE_FILL_COLOR;
        borderColor = new Color(~fillColor.getRGB());
    }

    public PrimarySurface(Path2D.Double polygon) {
        id = UUID.randomUUID();
        this.polygon = polygon;
        isSelected = false;
        isMouseHover = false;
        fillColor = Constants.DEFAULT_SHAPE_FILL_COLOR;
        borderColor = new Color(~fillColor.getRGB());
    }

    public PrimarySurface(double[] pointsX, double[] pointsY) {
        polygon = new Path2D.Double();
        for (int i = 1; i <= pointsX.length; ++i) {
            if (i == 1) {
                polygon.moveTo(pointsX[i], pointsY[i]);
            } else {
                polygon.lineTo(pointsX[i], pointsY[i]);
            }
        }
        polygon.closePath();
        isSelected = false;
        isMouseHover = false;
        id = UUID.randomUUID();
        fillColor = Constants.DEFAULT_SHAPE_FILL_COLOR;
        borderColor = new Color(~fillColor.getRGB());
    }

    public Point2D getCenter() {
        Rectangle2D bounds2D  = polygon.getBounds2D();
        return new Point2D.Double(bounds2D.getCenterX(), bounds2D.getCenterY());
    }

    public Point2D[] getVertices() {
        Vector<Point2D> vertices = new Vector<>();
        boolean isFirst = true;
        double[] firstCoords = new double[2];
        double[] coords = new double[2];
        int i = 0;

        for (PathIterator pi = polygon.getPathIterator(null); !pi.isDone(); pi.next()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO:
                case PathIterator.SEG_LINETO:
                    i++;
                    if (isFirst) {
                        firstCoords[0] = coords[0];
                        firstCoords[1] = coords[1];
                        isFirst = false;
                    }
                    vertices.add(new Point2D.Double(coords[0], coords[1]));
                    break;
                case PathIterator.SEG_CLOSE:
                    return vertices.toArray(new Point2D[vertices.size()]);
                default:
                    throw new IllegalArgumentException("Path contains curves");
            }
        }
        throw new IllegalArgumentException("Unclosed path");
    }

    public void move(Point2D from, Point2D to) {
        AffineTransform at = new AffineTransform();

        at.setToTranslation(to.getX() - from.getX(), to.getY() - from.getY());
        polygon.transform(at);
    }

    public void moveOf(Point2D to) {
        AffineTransform at = new AffineTransform();

        at.setToTranslation(to.getX() - polygon.getBounds2D().getX(), to.getY() - polygon.getBounds2D().getY());
        polygon.transform(at);
    }

    public void rotateDeg(double degrees) {
        AffineTransform at = new AffineTransform();

        at.setToRotation(degrees / 180 * Math.PI);
        polygon.transform(at);
    }

    public void rotateRad(double radians) {
        AffineTransform at = new AffineTransform();
        Point2D center = getCenter();

        at.setToRotation(radians, center.getX(), center.getY());
        polygon.transform(at);
    }

    public UUID getId() {
        return id;
    }

    public Path2D.Double getPolygon() {
        return polygon;
    }

    public void setPolygon(Path2D.Double polygon) {
        this.polygon = polygon;
    }

    public Color fillColor() {
        return fillColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Polygon getPolygonFromPath2D() {
        Point2D[] vertices = getVertices();
        int[] x = new int[vertices.length];
        int[] y = new int[vertices.length];

        for (int i = 0; i < vertices.length; i++) {
            x[i] = (int) vertices[i].getX();
            y[i] = (int) vertices[i].getY();
        }
        return new Polygon(x, y, vertices.length);
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public int getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isMouseHover() {
        return isMouseHover;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setMouseHover(boolean isMouseHover) {
        this.isMouseHover = isMouseHover;
    }
}