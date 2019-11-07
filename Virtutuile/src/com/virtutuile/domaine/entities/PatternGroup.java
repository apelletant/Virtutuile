package com.virtutuile.domaine.entities;

import com.virtutuile.domaine.entities.patterns.Classic;
import com.virtutuile.domaine.entities.patterns.Pattern;
import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.domaine.entities.surfaces.Tile;

import java.awt.geom.Point2D;
import java.util.Vector;

public class PatternGroup {

    private Pattern pattern = null;
    private Vector<Tile> tiles = new Vector<>();
    private float rotation;
    private Point2D point2D;

    public PatternGroup(String patternName, Surface surface) {
        switch (patternName) {
            case "Classic":
                pattern = new Classic();
            default:
                break;
        }
        /*if (this.pattern != null) {
            this.buildPattern(surface);
        }*/
    }

    public PatternGroup() {

    }

    public PatternGroup(PatternGroup patternGroup) {

    }

    public PatternGroup copy() {
        return new PatternGroup(this);
    }

    public Vector<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(Vector<Tile> tiles) {
        this.tiles = tiles;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Point2D getPoint2D() {
        return point2D;
    }

    public void setPoint2D(Point2D point2D) {
        this.point2D = point2D;
    }
}
