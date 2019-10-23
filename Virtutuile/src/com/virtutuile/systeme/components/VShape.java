package com.virtutuile.systeme.components;

import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.units.VCoordinate;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VShape {
    private UUID _id = UUID.randomUUID();
    private boolean _isHole = false;
    private boolean _selected = false;
    private boolean _isMouseHover = false;
    protected Path2D.Double _polygon = new Path2D.Double();
    private Color _borderColor = new Color(0);
    private Color _fillColor = UIConstants.DEFAULT_SHAPE_FILL_COLOR;

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

    public UUID id() {
        return this._id;
    }

    public Rectangle2D bounds() {
        return this._polygon.getBounds2D();
    }

    public boolean isHole() {
        return this._isHole;
    }

    public void hole(boolean hole) {
        this._isHole = hole;
    }

    public boolean isMouseHover() {
        return this._isMouseHover;
    }

    public void setMouseHover(boolean isMouseHover) {
        this._isMouseHover = isMouseHover;
    }

    public boolean isSelected() {
        return this._selected;
    }

    public void selected(boolean selected) {
        this._selected = selected;
    }

    public Path2D polygon() {
        return this._polygon;
    }

    public void polygon(Path2D.Double polygon) {
        this._polygon = polygon;
    }

    public Color borderColor() {
        return this._borderColor;
    }

    public void borderColor(Color borderColor) {
        this._borderColor = borderColor;
    }

    public Color fillColor() {
        return this._fillColor;
    }

    public void fillColor(Color fillColor) {
        this._fillColor = fillColor;
    }

    public VCoordinate[] getVertices() {
        List<VCoordinate> vertices = new ArrayList<VCoordinate>();
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
}
