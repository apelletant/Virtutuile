package com.virtutuile.systeme.components.shape;

import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.units.VCoordinate;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class VPrimaryShape implements Serializable {
    protected UUID id = UUID.randomUUID();
    protected Path2D.Double polygon = new Path2D.Double();
    protected Color borderColor = new Color(0);
    protected Color fillColor = UIConstants.DEFAULT_SHAPE_FILL_COLOR;

    VPrimaryShape(double[] pointsX, double[] pointsY, int nPoints) {
        this.polygon = new Path2D.Double();
        for (int i = 1; i <= nPoints; ++i) {
            if (i == 1) {
                polygon.moveTo(pointsX[i], pointsY[i]);
            } else {
                polygon.lineTo(pointsX[i], pointsY[i]);
            }
        }
        polygon.closePath();
    }

    VPrimaryShape(VCoordinate[] coordinates, int nPoints) {
        this.polygon = new Path2D.Double();
        for (int i = 1; i <= nPoints; ++i) {
            if (i == 1) {
                this.polygon.moveTo(coordinates[i].longitude, coordinates[i].latitude);
            } else {
                this.polygon.lineTo(coordinates[i].longitude, coordinates[i].latitude);
            }
        }
        this.polygon.closePath();
    }

    VPrimaryShape(Path2D.Double polygon) {
        this.polygon = polygon;
    }

    VPrimaryShape(VPrimaryShape primaryShape) {
        this.id = UUID.randomUUID();
        this.polygon = (Path2D.Double) primaryShape.polygon.clone();
        this.borderColor = new Color(primaryShape.borderColor.getRGB());
        this.fillColor = new Color(primaryShape.fillColor.getRGB());
    }

    VPrimaryShape() {
    }

    public VCoordinate getCenter() {
        Rectangle bounds = polygon.getBounds();

        return new VCoordinate(bounds.x + bounds.width / 2D, bounds.y + bounds.height / 2D);
    }

    public VCoordinate[] getVertices() {
        List<VCoordinate> vertices = new ArrayList<>();
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
        polygon.transform(at);
    }

    public void moveOf(VCoordinate to) {
        AffineTransform at = new AffineTransform();

        at.setToTranslation(to.longitude - this.polygon.getBounds2D().getX(), to.latitude - this.polygon.getBounds2D().getY());
        polygon.transform(at);
    }


    public void rotateDeg(double degrees) {
        AffineTransform at = new AffineTransform();

        at.setToRotation(degrees / 180 * Math.PI);
        polygon.transform(at);
    }

    public void rotateRad(double radians) {
        AffineTransform at = new AffineTransform();
        VCoordinate center = getCenter();

        at.setToRotation(radians, center.longitude, center.latitude);
        polygon.transform(at);
    }

    public UUID getId() {
        return id;
    }

    public Path2D.Double getPolygon() {
        return polygon;
    }
}
