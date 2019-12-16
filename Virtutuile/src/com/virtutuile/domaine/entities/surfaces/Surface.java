package com.virtutuile.domaine.entities.surfaces;

import com.virtutuile.domaine.entities.Grout;
import com.virtutuile.domaine.entities.PatternGroup;
import com.virtutuile.shared.Vector2D;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class Surface extends PrimarySurface {

    protected boolean isHole;
    protected PatternGroup patternGroup;
    protected Vector<Surface> subSurfaces;
    protected Tile typeOfTile;
    protected Grout grout;
    protected Surface next = null;
    protected Surface previous = null;

    public Surface(Point[] points) {
        super(points);
        isHole = false;
        patternGroup = null;
        subSurfaces = new Vector<>();
        grout = new Grout();
    }

    public Surface(Path2D.Double polygon, boolean isHole) {
        super(polygon);
        this.isHole = isHole;
        patternGroup = null;
        subSurfaces = new Vector<>();
        grout = new Grout();
    }


    public Surface(Surface surface) {
        super(surface);
        isHole = surface.isHole;
        isSelected = surface.isSelected;
        isMouseHover = surface.isMouseHover;
        if (surface.patternGroup != null) {
            patternGroup = surface.patternGroup.copy();
        } else {
            patternGroup = null;
        }
        subSurfaces = new Vector<>();
        //je s'appelle
        grout = surface.grout.copy();
        next = surface.next;
        previous = surface.previous;
    }

    protected Surface(boolean isHole) {
        super();
        this.isHole = isHole;
        isSelected = false;
        isMouseHover = false;
        patternGroup = null;
        subSurfaces = new Vector<>();
        grout = new Grout();
    }

    public Surface() {
        super();
        isHole = false;
        isSelected = false;
        isMouseHover = false;
        patternGroup = null;
        subSurfaces = new Vector<>();
        grout = new Grout();
    }

    public Surface(Point[] points, boolean isHole) {
        super(points);
        this.isHole = isHole;
        isSelected = false;
        isMouseHover = false;
        patternGroup = null;
        subSurfaces = new Vector<>();
        grout = new Grout();
    }

    public double circleIntersect(Circle circle) {
        Point2D[] vertices = getVertices();
        Vector2D location = new Vector2D(circle.getCenterX(), circle.getCenterY());

        for (int i = 0; i < vertices.length; ++i) {
            Vector2D from = Vector2D.from(vertices[i]);
            Vector2D to = Vector2D.from(vertices[(i + 1) % vertices.length]);

            double ab2, acab, h2;
            Vector2D ac = location.copy().subtract(from);
            Vector2D ab = to.copy().subtract(from);

            ab2 = ab.setProduct(ab);
            acab = ac.setProduct(ab);
            double t = acab / ab2;

            if (t < 0)
                t = 0;
            else if (t > 1)
                t = 1;

            Vector2D h = ab.copy().multiply(t).add(from).subtract(location);
            h2 = h.getProduct();

            if (h2 <= (circle.getRadius() * circle.getRadius()))
                return Math.sqrt(h2);
        }
        return Double.NaN;
    }

    public Point2D getVerticeNear(Point2D coordinates, double limit) {
        Vector2D coord = Vector2D.from(coordinates);
        Point2D[] vertices = getVertices();
        Point2D nearest = null;
        double lowest = 0;

        for (int i = 0; i < vertices.length; ++i) {
            double distance = Math.abs(Vector2D.from(vertices[i]).distance(coord));
            if (distance <= limit) {
                if (distance < lowest || nearest == null) {
                    nearest = vertices[i];
                    lowest = distance;
                }
            }
        }

        return nearest;
    }

    public PatternGroup getPatternGroup() {
        return patternGroup;
    }

    public void setPatternGroup(PatternGroup patternGroup) {
        this.patternGroup = patternGroup;
    }

    public void applyPattern(String patterName, Tile tile) {
        patternGroup = new PatternGroup(patterName, this, tile);
    }

    public Vector<Surface> getSubSurfaces() {
        return subSurfaces;
    }

    public Grout getGrout() {
        return grout;
    }

    public void setTypeOfTile(Tile typeOfTile) {
        this.typeOfTile = typeOfTile;
    }

    public Tile getTypeOfTile() {
        return typeOfTile;
    }

    public void setHole(boolean hole) {
        this.isHole = hole;
    }

    public boolean isHole() {
        return isHole;
    }

    public Surface getNext() {
        return next;
    }

    public void setNext(Surface next) {
        this.next = next;
    }

    public Surface getPrevious() {
        return previous;
    }

    public void setPrevious(Surface previous) {
        this.previous = previous;
    }

    /*@Override
    public void move(Point2D from, Point2D to) {
        move(from, to, null);
    }*/

    public void move(Point2D from, Point2D to) {
        move(from,to,this);
    }

    public void move(Point2D from, Point2D to, Surface stop) {
        /*AffineTransform at = new AffineTransform();

        at.setToTranslation(to.getX() - from.getX(), to.getY() - from.getY());
        polygon.transform(at);*/

        moveOf((to.getX() - from.getX()),(to.getY() - from.getY()));

        if (stop != null && next != null && next != stop) {
            next.move(from, to, stop);
        }
    }

    @Override
    public void moveOf(double x, double y) {
        AffineTransform at = new AffineTransform();

        at.setToTranslation(x, y);
        polygon.transform(at);

        if (patternGroup != null) {
            for (Tile tile : patternGroup.getTiles()) {
                tile.moveOf(x, y);
            }
        }
    }

    private Rectangle2D.Double getGlobalBounds() {
        Surface iterator = next;
        Rectangle2D.Double bounds = new Rectangle2D.Double();
        Point2D.Double max = new Point2D.Double();

        bounds.x = getBounds().x;
        bounds.y = getBounds().y;
        max.x = bounds.x + getBounds().width;
        max.y = bounds.y + getBounds().height;

        while (iterator != this) {
            Rectangle2D.Double boundsIterator = iterator.getBounds();
            if (boundsIterator.x < bounds.x) {
                bounds.x = boundsIterator.x;
            }
            if (boundsIterator.y < bounds.y) {
                bounds.y = boundsIterator.y;
            }
            if (boundsIterator.x + boundsIterator.width > max.x) {
                max.x = boundsIterator.x + boundsIterator.width;
            }
            if (boundsIterator.y + boundsIterator.height > max.y) {
                max.y = boundsIterator.y + boundsIterator.height;
            }
            iterator = iterator.next;
        }

        bounds.width = max.x - bounds.x;
        bounds.height = max.y - bounds.y;

        return bounds;
    }

    private void rotateDeg(Rectangle2D.Double bounds, double degrees, Surface surface) {
        AffineTransform at = new AffineTransform();
        double wantToRotateTo = degrees;
        degrees -= surface.getRotationDeg();
        at.setToRotation(degrees * Math.PI / 180);

        surface.moveOf((-bounds.x - (bounds.getWidth() / 2)),((-bounds.y - (bounds.getHeight() / 2))));
        surface.getPolygon().transform(at);
        if (surface.getPatternGroup() != null) {
            for (Tile tile : surface.getPatternGroup().getTiles()) {
                tile.getPolygon().transform(at);
            }
        }
        surface.moveOf((bounds.x + (bounds.getWidth() / 2)),((bounds.y + (bounds.getHeight() / 2))));


        surface.setRotationRadian(wantToRotateTo * Math.PI / 180);
    }

    @Override
    public void rotateDeg(double degrees) {
        Surface it = null;
        Rectangle2D.Double bounds;

        if (next != null) {
            it = next;
            bounds = getGlobalBounds();
        } else {
            bounds = getBounds();
        }

        rotateDeg(bounds, degrees,this);
        if (next != null && next != this) {
            while (it != this) {
                if (it != null) {
                    rotateDeg(bounds, degrees, it);
                    it = it.next;
                }
            }
        }
    }

    public void setRotationRadian(double radian) {
        rotationRadian = radian;
    }
}
