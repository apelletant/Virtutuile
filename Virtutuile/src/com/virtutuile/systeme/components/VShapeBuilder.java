package com.virtutuile.systeme.components;


import com.virtutuile.systeme.components.VShape;
import com.virtutuile.systeme.units.VCoordinate;

public abstract class VShapeBuilder {

    public abstract void placePoint(VCoordinate coordinate);
    public abstract void movePoint(VCoordinate coordinate);
    public abstract void makeHole(boolean isHole);

    public abstract VShape getShape();
}
