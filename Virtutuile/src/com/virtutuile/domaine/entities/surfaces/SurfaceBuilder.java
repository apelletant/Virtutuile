package com.virtutuile.domaine.entities.surfaces;

import java.awt.geom.Point2D;

public abstract class SurfaceBuilder {
    public abstract void placePoint(Point2D point2D);
    public abstract void movePoint(Point2D point2D);
    public abstract void makeHole(boolean isHole);

    public abstract Surface getSurface();
}
