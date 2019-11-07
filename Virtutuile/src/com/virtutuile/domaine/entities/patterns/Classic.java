package com.virtutuile.domaine.entities.patterns;

import com.virtutuile.domaine.entities.surfaces.Tile;

import java.awt.geom.Rectangle2D;

public class Classic extends Pattern {
    public Classic() {
        adjust = new double[]{0, 0};
        tiles.add(new Tile(new Rectangle2D.Double(0,0, defaultDimensions.getX(), defaultDimensions.getY())));
    }
}
