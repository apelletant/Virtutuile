package com.virtutuile.domaine.entities.surfaces;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.domaine.entities.tools.Intersection;
import com.virtutuile.domaine.entities.tools.PolygonTransformer;
import com.virtutuile.shared.CustomPoint;
import com.virtutuile.shared.Vecteur;

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
    protected Bounds bounds;
    protected Double rotationRadian = 0.0;

    protected Color borderColor;
    protected Color fillColor;
    protected Color settedColor;
    protected int borderThickness = Constants.DEFAULT_SHAPE_BORDER_THICKNESS;

    public PrimarySurface(Point[] points) {
        id = UUID.randomUUID();
        polygon = new Path2D.Double();
        isSelected = false;
        isMouseHover = false;
        fillColor = Constants.DEFAULT_SHAPE_FILL_COLOR;
        settedColor = fillColor;
        borderColor = new Color(~fillColor.getRGB());

        for (int i = 1; i <= points.length; ++i) {
            if (i == 1) {
                polygon.moveTo(points[i].x, points[i].y);
            } else {
                polygon.lineTo(points[i].x, points[i].y);
            }
        }
        polygon.closePath();
        bounds = new Bounds(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
    }

    public PrimarySurface(double x, double y, double width, double height) {
        polygon = new Path2D.Double();
        polygon.moveTo(x,y);
        polygon.lineTo(x + width, height);
        polygon.lineTo(x + width, y + height);
        polygon.lineTo(x, y + height);
        polygon.closePath();
    }

    public PrimarySurface(PrimarySurface surface) {
        id = UUID.randomUUID();
        polygon = (Path2D.Double) surface.polygon.clone();
        isSelected = surface.isSelected;
        isMouseHover = surface.isMouseHover;
        fillColor = new Color(surface.fillColor.getRGB());
        borderColor = new Color(surface.borderColor.getRGB());
        bounds = new Bounds(getBounds().x, getBounds().y, getBounds().width, getBounds().height);;
    }

    public PrimarySurface() {
        id = UUID.randomUUID();
        polygon = new Path2D.Double();
        isSelected = false;
        isMouseHover = false;
        fillColor = Constants.DEFAULT_SHAPE_FILL_COLOR;
        settedColor = fillColor;
        borderColor = new Color(~fillColor.getRGB());
        bounds = new Bounds(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
    }

    public PrimarySurface(Path2D.Double polygon) {
        id = UUID.randomUUID();
        this.polygon = polygon;
        isSelected = false;
        isMouseHover = false;
        fillColor = Constants.DEFAULT_SHAPE_FILL_COLOR;
        settedColor = fillColor;
        borderColor = new Color(~fillColor.getRGB());
        bounds = new Bounds(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
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
        settedColor = fillColor;
        borderColor = new Color(~fillColor.getRGB());
        bounds = new Bounds(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
    }

    public Point2D getCenter() {
        Rectangle2D bounds2D  = polygon.getBounds2D();
        return new Point2D.Double(bounds2D.getCenterX(), bounds2D.getCenterY());
    }

    public Point2D[] getVertices() {
        return PolygonTransformer.extractVertices(polygon)[0];
    }

    public void move(Point2D from, Point2D to) {
        AffineTransform at = new AffineTransform();

        at.setToTranslation(to.getX() - from.getX(), to.getY() - from.getY());
        polygon.transform(at);
    }

    public void moveOf(Point2D to) {
        moveOf(to.getX(), to.getY());
    }

    public void moveOf(double x, double y) {
        AffineTransform at = new AffineTransform();

        at.setToTranslation(x, y);
        polygon.transform(at);
    }


    protected Rectangle2D.Double getBoundsCopy() {
        Rectangle2D.Double cpy = new Rectangle2D.Double();

        cpy.x = getBounds().x;
        cpy.y = getBounds().y;
        cpy.width = getBounds().width;
        cpy.height = getBounds().height;

        return cpy;
    }

    public void rotateDeg(double degrees) {
        AffineTransform at = new AffineTransform();
        double wantToRotateTo = degrees;

        degrees -= getRotationDeg();
        at.setToRotation(degrees * Math.PI / 180);

        Rectangle2D.Double cpy = getBoundsCopy();
        moveOf((-getBounds().x - (getBounds().getWidth() / 2)),((-getBounds().y - (getBounds().getHeight() / 2))));
        polygon.transform(at);
        moveOf(cpy.x + cpy.getWidth() / 2, cpy.y + cpy.getHeight() / 2);
        rotationRadian = wantToRotateTo * Math.PI / 180;
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

    public boolean containsOrIntersect(double[] point) {
        return containsOrIntersect(point[0], point[1]);
    }

    public boolean containsOrIntersect(Point2D point) {
        return containsOrIntersect(point.getX(), point.getY());
    }

    public boolean containsOrIntersect(double x, double y) {
        if (polygon.contains(x, y)) {
            return true;
        }
        Point2D[] vertices = getVertices();

        for (int i = 0; i < vertices.length; ++i) {
            Vecteur a = new Vecteur(vertices[i]);
            Vecteur b = null;
            if (i != vertices.length - 1) {
                b = new Vecteur(vertices[i + 1]);
            } else {
                b = new Vecteur(vertices[0]);
            }

            Vecteur ab = new Vecteur(new CustomPoint(a.x, a.y), new CustomPoint(b.x, b.y));
            Vecteur pp = new Vecteur(new CustomPoint(x, y), new CustomPoint(x, y));

            CustomPoint intersect = Intersection.intersectionPoint(ab, pp);
            if (intersect != null) {
                return true;
            }
        }
        return false;
    }

    public void rescale(double ratioX, double ratioY) {
        AffineTransform transform = new AffineTransform();
        transform.scale(ratioX, ratioY);
        polygon.transform(transform);
    }

    public void resize(double newWidth, double newHeight) {
        Rectangle2D.Double bounds = (Rectangle2D.Double) polygon.getBounds2D();
        double xRatio = newWidth / bounds.width;  // retour de fonction algo lombard
        double yRatio = newHeight / bounds.height;


        AffineTransform transform = new AffineTransform();
        transform.scale(xRatio, yRatio);
        //transform.translate(); // << == a utiliser pour move
        polygon.transform(transform); // << apply transfo

        //
        Rectangle2D.Double newBounds = (Rectangle2D.Double) polygon.getBounds2D();
        double xDisplacement = (bounds.x - newBounds.x);
        double yDisplacement = (bounds.y - newBounds.y);

        transform = new AffineTransform();
        transform.translate(xDisplacement, yDisplacement);
        polygon.transform(transform);
    }

    public void setWidth(double newWidth) {
        resize(newWidth, polygon.getBounds2D().getHeight());
    }

    public void setHeight(double newHeight) {
        resize(polygon.getBounds2D().getWidth(), newHeight);
    }

    public Rectangle2D.Double getBounds() {
        return (Rectangle2D.Double) polygon.getBounds2D();
    }

    public Bounds getBoundsAsSurface() {
        return bounds;
    }

    public boolean contains(PrimarySurface surfaceContains) {
        boolean contained = true;
        Point2D[] surfaceContainsVertices = surfaceContains.getVertices();

        for (Point2D surfaceContainsVertex : surfaceContainsVertices) {
            if (!polygon.contains(surfaceContainsVertex)) {
                contained = false;
            }
        }

        return contained;
    }

    public void addPath(Point2D[] vertices) {
        int i = 1;

        polygon.closePath();
        polygon.moveTo(vertices[0].getX(), vertices[0].getY());
        while (i != vertices.length) {
            polygon.lineTo(vertices[i].getX(), vertices[i].getY());
            i++;
        }
        polygon.closePath();
    }

    public boolean containsOrIntersect(PrimarySurface testSurface) {

        if (polygon.contains(testSurface.getBounds())
                || testSurface.getPolygon().contains(getBounds())) {
            return true;
        }

        Point2D[][] fullTestVertices = PolygonTransformer.extractVertices(polygon);
        Point2D[][] fullVertices = PolygonTransformer.extractVertices(testSurface.polygon);

        for (Point2D[] vertices : fullVertices) {
            for (int i = 0; i < vertices.length; ++i) {
                Vecteur actualA = new Vecteur(vertices[i]);
                Vecteur actualB;
                if (i != vertices.length - 1) {
                    actualB = new Vecteur(vertices[i + 1]);
                } else {
                    actualB = new Vecteur(vertices[0]);
                }
                for (Point2D[] testVertices : fullTestVertices) {
                    for (int i1 = 0; i1 < testVertices.length; i1++) {
                        Vecteur testA = new Vecteur(testVertices[i1]);
                        Vecteur testB;
                        if (i1 != testVertices.length - 1) {
                            testB = new Vecteur(testVertices[i1 + 1]);
                        } else {
                            testB = new Vecteur(testVertices[0]);
                        }
                        Vecteur actual = new Vecteur(new CustomPoint(actualA.x, actualA.y), new CustomPoint(actualB.x, actualB.y));
                        Vecteur test = new Vecteur(new CustomPoint(testA.x, testA.y), new CustomPoint(testB.x, testB.y));
                        CustomPoint intersect = Intersection.intersectionPoint(actual, test);
                        if (intersect != null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public Color getSettedColor() {
        return settedColor;
    }

    public void setSettedColor(Color settedColor) {
        this.settedColor = settedColor;
    }

    public Double getRotationRadian() {
        return rotationRadian;
    }

    public Double getRotationDeg() {
        return rotationRadian * 180 / Math.PI;
    }
}