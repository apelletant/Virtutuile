package com.virtutuile.systeme.components;

import com.virtutuile.systeme.components.pattern.VPatternSpec;
import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.constants.VPhysicsConstants;
import com.virtutuile.systeme.shared.PatternType;
import com.virtutuile.systeme.units.VCoordinate;
import com.virtutuile.systeme.units.Vector2D;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class VShape implements Serializable {
    protected UUID _id = UUID.randomUUID();
    protected boolean _isHole = false;
    protected boolean _isSelected = false;
    protected boolean _isMouseHover = false;
    protected Path2D.Double _polygon = new Path2D.Double();
    protected Color _borderColor = new Color(0);
    protected Color _fillColor = UIConstants.DEFAULT_SHAPE_FILL_COLOR;
    protected VPatternSpec _patternSpec = null;
    protected VGrout _grout = new VGrout();

    protected VShape(boolean isHole) {
        this._isHole = isHole;
    }

    public VShape(double[] pointsX, double[] pointsY, int nPoints, boolean isHole) {
        this(isHole);
        this._polygon = new Path2D.Double();
        for (int i = 1; i <= nPoints; ++i) {
            if (i == 1) {
                this._polygon.moveTo(pointsX[i], pointsY[i]);
            } else {
                this._polygon.lineTo(pointsX[i], pointsY[i]);
            }
        }
        this._polygon.closePath();
    }

    public VShape(VCoordinate[] coordinates, int nPoints, boolean isHole) {
        this(isHole);
        this._polygon = new Path2D.Double();
        for (int i = 1; i <= nPoints; ++i) {
            if (i == 1) {
                this._polygon.moveTo(coordinates[i].longitude, coordinates[i].latitude);
            } else {
                this._polygon.lineTo(coordinates[i].longitude, coordinates[i].latitude);
            }
        }
        this._polygon.closePath();
    }

    public VShape(Path2D.Double polygon, boolean isHole) {
        this._isHole = isHole;
        this._polygon = polygon;
    }

    public VShape(VShape shape) {
        this._id = UUID.randomUUID();
        this._isHole = shape._isHole;
        this._isSelected = shape._isSelected;
        this._isMouseHover = shape._isMouseHover;
        this._polygon = (Path2D.Double) shape._polygon.clone();
        this._borderColor = new Color(shape._borderColor.getRGB());
        this._fillColor = new Color(shape._fillColor.getRGB());
    }

    public double circleIntersect(Circle circle) {
        VCoordinate[] vertices = getVertices();
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

    public VCoordinate getVerticeNear(VCoordinate coordinates, double limit) {
        Vector2D coord = Vector2D.from(coordinates);
        VCoordinate[] vertices = getVertices();
        VCoordinate nearest = null;
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

    public VPatternSpec getPatternSpec() {
        return this._patternSpec;
    }

    public void setPatternSpec(PatternType pattern) {
        this._patternSpec = new VPatternSpec(pattern, this);
    }

    public UUID getId() {
        return this._id;
    }

    public Rectangle2D getBounds() {
        return this._polygon.getBounds2D();
    }

    public boolean getIsHole() {
        return this._isHole;
    }

    public void setHole(boolean hole) {
        this._isHole = hole;
    }

    public boolean getIsMouseHover() {
        return this._isMouseHover;
    }

    public void setMouseHover(boolean isMouseHover) {
        this._isMouseHover = isMouseHover;
    }

    public boolean isSelected() {
        return this._isSelected;
    }

    public void setSelected(boolean selected) {
        this._isSelected = selected;
    }

    public Path2D getPolygon() {
        return this._polygon;
    }

    public void setPolygon(Path2D.Double polygon) {
        this._polygon = polygon;
    }

    public Color getBorderColor() {
        return this._borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this._borderColor = borderColor;
    }

    public Color getFillColor() {
        return this._fillColor;
    }

    public void setFillColor(Color fillColor) {
        this._fillColor = fillColor;
    }

    public VGrout getGrout() {
        return  this._grout;
    }

    public VCoordinate getCenter() {
        Rectangle bounds = _polygon.getBounds();

        return new VCoordinate(bounds.x + bounds.width / 2D, bounds.y + bounds.height / 2D);
    }

    public VCoordinate[] getVertices() {
        List<VCoordinate> vertices = new ArrayList<>();
        boolean isFirst = true;
        double[] firstCoords = new double[2];
        double[] coords = new double[2];

        for (PathIterator pi = _polygon.getPathIterator(null); !pi.isDone(); pi.next()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO:
                case PathIterator.SEG_LINETO:
                    if (isFirst) {
                        firstCoords[0] = coords[0];
                        firstCoords[1] = coords[1];
                        isFirst = false;
                    }
                    vertices.add(new VCoordinate(coords[0], coords[1]));
                    break;
                case PathIterator.SEG_CLOSE:
                    return vertices.toArray(new VCoordinate[vertices.size()]);
                default:
                    throw new IllegalArgumentException("Path contains curves");
            }
        }
        throw new IllegalArgumentException("Unclosed path");
    }

    public void move(VCoordinate from, VCoordinate to) {
        AffineTransform at = new AffineTransform();

        at.setToTranslation(to.longitude - from.longitude, to.latitude - from.latitude);
        _polygon.transform(at);
    }

    public void rotateDeg(double degrees) {
        AffineTransform at = new AffineTransform();

        at.setToRotation(degrees / 180 * Math.PI);
        _polygon.transform(at);
    }

    public void rotateRad(double radians) {
        AffineTransform at = new AffineTransform();
        VCoordinate center = getCenter();

        at.setToRotation(radians, center.longitude, center.latitude);
        _polygon.transform(at);
    }

    public VDrawableShape getDrawable() {
        VCoordinate[] coords = getVertices();
        Point[] points = VPhysicsConstants.coordinatesToPoints(coords);
        VDrawableShape drawable = new VDrawableShape(points);
        if (this._patternSpec != null) {
            Vector<VDrawableShape> drawableTiles = new Vector<>(this._patternSpec.getTiles().size());
            this._patternSpec.getTiles().forEach((value) -> {
                VCoordinate[] coordsTile = new VCoordinate[4];
                coordsTile[0] = new VCoordinate(value.getOrigin());
                /*System.out.println("1: {" + coordsTile[0].longitude + ", " + coordsTile[0].latitude + "}");*/
                coordsTile[1] = new VCoordinate(value.getOrigin().longitude + value.getDimensions().width, value.getOrigin().latitude);
                /*System.out.println("2: {" + coordsTile[1].longitude + ", " + coordsTile[1].latitude + "}");*/
                coordsTile[2] = new VCoordinate(value.getOrigin().longitude + value.getDimensions().width, value.getOrigin().latitude + value.getDimensions().height);
                /*System.out.println("3: {" + coordsTile[2].longitude + ", " + coordsTile[2].latitude + "}");*/
                coordsTile[3] = new VCoordinate(value.getOrigin().longitude, value.getOrigin().latitude + value.getDimensions().height);
                /*System.out.println("4: {" + coordsTile[3].longitude + ", " + coordsTile[3].latitude + "}");*/
                /*System.out.println("  ");*/
                Point[] pointsTile = VPhysicsConstants.coordinatesToPoints(coordsTile);
                VDrawableShape tile = new VDrawableShape(pointsTile);
                drawableTiles.add(tile);
            });
            drawable.setSubShapes(drawableTiles);
        }
        drawable.setActive(_isSelected);
        drawable.setMouseHovered(_isMouseHover);
        drawable.fillColor(_fillColor);
        return drawable;
    }
}
