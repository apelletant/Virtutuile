package com.virtutuile.domaine.entities.patterns;

import com.virtutuile.domaine.entities.surfaces.Tile;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Vector;

/**
 * Pattern configuration rules :
 *  - Tile positioning rules :
 *    A tile should be positioned relatively to an hypothetic point -> 0;0 (the cursor in fact).
 *    The positions should ignore what size is the tile. The positions will be multiplied by the tile size later.
 *        x-----------x   x-----------x
 *        |   Tile1   |   |   Tile2   |
 *        x___________x   x___________x
 *
 *        x-----------x
 *        |   Tile3   |
 *        x___________x
 *
 *   In this example tiles are positioned like following :
 *   Tile1 : (0;0)
 *   Tile2 : (1;0)
 *   Tile3 : (0;1)
 *
 *  - Offset(X|Y) determine of how the cursor should move after a pattern application
 *              * x-----------x
 *     X cursor ^ |   Tile1   |
 *                x___________x
 *
 *   In this case the pattern is composed of only one tile. (consider the "*" is on the tile top-left corner)
 *   Then the cursor should increment of size of 1 tile after the pattern application is done.
 *   On the next step we should have (consider the "*" is on the tile top-right corner):
 *        x-----------x *
 *        |   Tile1   | ^ X cursor
 *        x___________x
 *
 *
 *  - grout(X|Y)Rules determine if and where the grout shall be applied.
 *        x-----------x g x-----------x
 *        |   Tile1   | g |   Tile2   |
 *        x___________x g x___________x
 *        ggggggggggggggg
 *        x-----------x
 *        |   Tile3   |
 *        x___________x
 *
 *   Tile1 should apply a grout around it then it's grout rule will be (1;1)
 *   But both Tile2 and Tile3 has no needs to apply a grout because the next cursor position will take it in consideration.
 */
public abstract class Pattern implements Serializable {
    protected double offsetX;
    protected double offsetY;
    protected double[] adjustXRules;
    protected double[] adjustYRules;
    protected double[] groutXRules;
    protected double[] groutYRules;
    protected Vector<Tile> tiles = new Vector<>();
    protected String name;
    protected Point2D defaultDimensions = new Point2D.Double(100, 70);

    Pattern(String name) {
        this.name = name;
    }


    public Vector<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(Vector<Tile> tiles) {
        this.tiles = tiles;
    }
    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double[] getAdjustXRules() {
        return adjustXRules;
    }

    public double[] getAdjustYRules() {
        return adjustYRules;
    }

    public double[] getGroutXRules() {
        return groutXRules;
    }

    public double[] getGroutYRules() {
        return groutYRules;
    }

    public abstract void setTileType(Tile tile);

    public String getName() {
        return name;
    }

    public Rectangle2D.Double getCritBounds() {
        Tile first = tiles.get(0);
        if (first == null)
            return null;

        return first.getBounds();
    }
}
