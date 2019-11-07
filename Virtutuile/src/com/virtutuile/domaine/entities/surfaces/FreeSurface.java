package com.virtutuile.domaine.entities.surfaces;

import java.awt.*;
import java.awt.geom.Path2D;

public class FreeSurface extends Surface {
    public FreeSurface(boolean hole, Polygon polygon) {
        super(hole);
    }

    public FreeSurface(Path2D.Double polygon) {
        super(polygon, false);
    }
}
