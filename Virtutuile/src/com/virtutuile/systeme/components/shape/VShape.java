package com.virtutuile.systeme.components.shape;

import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.components.VGrout;
import com.virtutuile.systeme.components.pattern.VPatternSpec;
import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.constants.VPhysicsConstants;
import com.virtutuile.systeme.shared.PatternType;
import com.virtutuile.systeme.units.VCoordinate;
import com.virtutuile.systeme.units.Vector2D;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class VShape extends VPrimaryShape {
    protected boolean isHole = false;
    protected boolean isSelected = false;
    protected boolean isMouseHover = false;
    protected VPatternSpec patternSpec = null;
    protected VGrout grout = new VGrout();

    protected VShape(boolean isHole) {
        super();
        this.isHole = isHole;
    }

    public VShape(double[] pointsX, double[] pointsY, int nPoints, boolean isHole) {
        super(pointsX, pointsY, nPoints);
        this.isHole = isHole;
    }

    public VShape(VCoordinate[] coordinates, int nPoints, boolean isHole) {
        super(coordinates, nPoints);
        this.isHole = isHole;
    }

    public VShape(Path2D.Double polygon, boolean isHole) {
        super(polygon);
        this.isHole = isHole;
    }

    public VShape(VShape shape) {
        super(shape);
        this.isHole = shape.isHole;
        this.isSelected = shape.isSelected;
        this.isMouseHover = shape.isMouseHover;
        this.patternSpec = shape.patternSpec;
        this.grout = shape.grout;
    }

    public VShape() {
        super();
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
        return this.patternSpec;
    }

    public void setPatternSpec(PatternType pattern) {
        this.patternSpec = new VPatternSpec(pattern, this);
    }

    public Rectangle2D getBounds() {
        return this.polygon.getBounds2D();
    }

    public void setMouseHover(boolean isMouseHover) {
        this.isMouseHover = isMouseHover;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public VGrout getGrout() {
        return  this.grout;
    }

    public VDrawableShape getDrawable() {
        VCoordinate[] coords = getVertices();
        Point[] points = VPhysicsConstants.coordinatesToPoints(coords);
        VDrawableShape drawable = new VDrawableShape(points);
        if (this.patternSpec != null) {
            Vector<VDrawableShape> drawableTiles = new Vector<>(this.patternSpec.getTiles().size());
            this.patternSpec.getTiles().forEach((tile) -> {
                VCoordinate[] coordsTile = tile.getVertices();
                Point[] pointsTile = VPhysicsConstants.coordinatesToPoints(coordsTile);
                /*coordsTile[0] = new VCoordinate(value.getOrigin());*/
                /*System.out.println("1: {" + coordsTile[0].longitude + ", " + coordsTile[0].latitude + "}");*/
                /*coordsTile[1] = new VCoordinate(value.getOrigin().longitude + value.getDimensions().width, value.getOrigin().latitude);*/
                /*System.out.println("2: {" + coordsTile[1].longitude + ", " + coordsTile[1].latitude + "}");*/
                /*coordsTile[2] = new VCoordinate(value.getOrigin().longitude + value.getDimensions().width, value.getOrigin().latitude + value.getDimensions().height);*/
                /*System.out.println("3: {" + coordsTile[2].longitude + ", " + coordsTile[2].latitude + "}");*/
                /*coordsTile[3] = new VCoordinate(value.getOrigin().longitude, value.getOrigin().latitude + value.getDimensions().height);*/
                /*System.out.println("4: {" + coordsTile[3].longitude + ", " + coordsTile[3].latitude + "}");*/
                /*System.out.println("  ");*/
                VDrawableShape newTile = new VDrawableShape(pointsTile);
                drawableTiles.add(newTile);
            });
            drawable.setSubShapes(drawableTiles);
        }
        drawable.setActive(isSelected);
        drawable.setMouseHovered(isMouseHover);
        drawable.fillColor(fillColor);
        return drawable;
    }
}
