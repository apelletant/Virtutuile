package com.virtutuile.systeme.components.shape;

import com.virtutuile.systeme.units.VCoordinate;

import java.awt.geom.Path2D;

public class VTile extends VPrimaryShape {

    /*private VDimensions _dimensions = new VDimensions();
    private VCoordinate _origin = new VCoordinate();
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
        // + les attributs de VTile a copier
    }

    public VTile() {

    }

    public VTile copy() {
        return new VTile(this);
    }

    

}