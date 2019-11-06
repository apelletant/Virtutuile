package com.virtutuile.systeme.components.shape;

import com.virtutuile.systeme.components.shape.VShape;

import java.awt.*;
import java.awt.geom.Path2D;

public class VFreeShape extends VShape {

    public VFreeShape(boolean hole, Polygon polygon) {
        super(hole);
    }

    public VFreeShape(Path2D.Double polygon) {
        super(polygon, false);
    }
}
