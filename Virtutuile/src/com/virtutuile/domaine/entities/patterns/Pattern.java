package com.virtutuile.domaine.entities.patterns;

import com.virtutuile.domaine.entities.surfaces.Tile;

import java.awt.geom.Point2D;
import java.util.Vector;

public abstract class Pattern {
    protected int[] offsetX;
    protected int[] offsetY;
    protected double[] adjust;
    protected Vector<Tile> tiles = new Vector<>();

    //TODO: Définir les dimensions de la tuile ailleurs #edtion type de materiau
    protected Point2D defaultDimensions = new Point2D.Double(100, 70);

    public Vector<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(Vector<Tile> tiles) {
        this.tiles = tiles;
    }
    public int[] getOffsetX() {
        return offsetX;
    }

    public int[] getOffsetY() {
        return offsetY;
    }

    public double[] getAdjust() {
        return adjust;
    }
}
