package com.virtutuile.systeme.components.shape;

import com.virtutuile.systeme.units.VCoordinate;
import com.virtutuile.systeme.units.VDimensions;

import java.awt.*;
import java.awt.geom.Path2D;

public class VTile extends VPrimaryShape {

    private VCoordinate origin;
    private VCoordinate opposite;
    /*private VDimensions _dimensions = new VDimensions();
    private Color _color;
    private VCoordinate _position = new VCoordinate();;
    private float _rotation;
    */

    public VTile(double[] pointsX, double[] pointsY, int nPoints) {
      super(pointsX, pointsY, nPoints);
    }

    public VTile(VCoordinate[] coordinates, int nPoints) {
        super(coordinates, nPoints);

    }

    public VTile(Path2D.Double polygon) {
        super(polygon);
    }

    public VTile(VTile tile) {
        super(tile);
        this.origin = tile.origin;
    }

    public VTile(Rectangle.Double rect) {
        super();
        this.origin = new VCoordinate(rect.x, rect.y);
        this.opposite = new VCoordinate(rect.x + rect.width, rect.y + rect.height);
        regenerate();
    }

    public VTile() {

    }

    public VTile copy() {
        return new VTile(this);
    }

    public VCoordinate getOrigin() {
        return this.origin;
    }

    public void setOrigin(VCoordinate origin) {
        this.moveOf(origin);
        this.origin = origin;
    }

    private void regenerate() {
        this.polygon = new Path2D.Double();
        this.polygon.moveTo(this.origin.longitude, this.origin.latitude);
        this.polygon.lineTo(this.origin.longitude, this.opposite.latitude);
        this.polygon.lineTo(this.opposite.longitude, this.opposite.latitude);
        this.polygon.lineTo(this.opposite.longitude, this.origin.latitude);
        this.polygon.closePath();
    }

    public void setWidthForRectTile(double width) {
        VCoordinate[] vertices =  getVertices();
        this.polygon = new Path2D.Double();

        this.polygon.moveTo(vertices[0].longitude, vertices[0].latitude);
        this.polygon.lineTo(vertices[0].longitude, vertices[2].latitude);
        this.polygon.lineTo(width, vertices[2].latitude);
        this.polygon.lineTo(width, vertices[0].latitude);
        this.polygon.closePath();

    }

    public void setHeightForRectTile(double height) {
        VCoordinate[] vertices =  getVertices();
        this.polygon = new Path2D.Double();

        this.polygon.moveTo(vertices[0].longitude, vertices[0].latitude);
        this.polygon.lineTo(vertices[0].longitude, height);
        this.polygon.lineTo(vertices[2].longitude, height);
        this.polygon.lineTo(vertices[2].longitude, vertices[0].latitude);
        this.polygon.closePath();
    }
}