package com.virtutuile.domaine.entities.patterns;

import com.virtutuile.domaine.entities.surfaces.Tile;

import java.awt.geom.Point2D;
import java.util.Vector;

public abstract class Pattern {
    protected double[] offsetX;
    protected double[] offsetY;
    protected double[] adjust;
    protected Vector<Tile> tiles = new Vector<>();
    protected String name;

    Pattern(String name) {
        this.name = name;
    }

    protected Point2D defaultDimensions = new Point2D.Double(100, 70);

    public Vector<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(Vector<Tile> tiles) {
        this.tiles = tiles;
    }
    public double[] getOffsetX() {
        return offsetX;
    }

    public double[] getOffsetY() {
        return offsetY;
    }

    public double[] getAdjust() {
        return adjust;
    }

    public abstract void setTileType(Tile tile);

    public String getName() {
        return name;
    }
}
