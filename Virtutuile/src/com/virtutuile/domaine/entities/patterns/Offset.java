package com.virtutuile.domaine.entities.patterns;

import com.virtutuile.domaine.entities.surfaces.Tile;

import java.awt.geom.Rectangle2D;

/**
 * @see Pattern for more informations about pattern configuration
 */
public class Offset extends Pattern {
    public Offset(Tile tile) {
        super("Offset");
        adjustXRules = new double[]{0, -0.5};
        adjustYRules = new double[]{0, 0};
        groutXRules = new double[]{0, 0};
        groutYRules = new double[]{1, 0};
        offsetX = 1;
        offsetY = 2;
        if (tile != null) {
            tiles.add(new Tile(new Rectangle2D.Double(0,0, tile.getBounds().getWidth(), tile.getBounds().getHeight())));
            tiles.get(0).setFillColor(tile.getFillColor());
            tiles.add(new Tile(new Rectangle2D.Double(-0.5d, 1d, tile.getBounds().getWidth(), tile.getBounds().getHeight())));
            tiles.get(1).setFillColor(tile.getFillColor());
        } else {
            tiles.add(new Tile(new Rectangle2D.Double(0,0, defaultDimensions.getX(), defaultDimensions.getY())));
            tiles.add(new Tile(new Rectangle2D.Double(-0.5d, 1d, defaultDimensions.getX(), defaultDimensions.getY())));
        }
    }

    @Override
    public void setTileType(Tile tile) {
        tiles.clear();
        tiles.add(new Tile(new Rectangle2D.Double(0,0, tile.getBounds().getWidth(), tile.getBounds().getHeight())));
        tiles.get(0).setFillColor(tile.getFillColor());
        tiles.add(new Tile(new Rectangle2D.Double(-0.5d, 1d, tile.getBounds().getWidth(), tile.getBounds().getHeight())));
        tiles.get(1).setFillColor(tile.getFillColor());
    }
}

//public Offset(Tile tile) {
//    super("Offset");
//    adjust = new double[]{0, 0};
//    offsetX = new double[]{1};
//    offsetY = new double[]{1};
//    if (tile != null) {
//        tiles.add(new Tile(new Rectangle2D.Double(0,0, tile.getBounds().getWidth(), tile.getBounds().getHeight())));
//        tiles.get(0).setFillColor(tile.getFillColor());
//    } else {
//        tiles.add(new Tile(new Rectangle2D.Double(0,0, defaultDimensions.getX(), defaultDimensions.getY())));
//    }
//}
//
//    @Override
//    public void setTileType(Tile tile) {
//        tiles.clear();
//        tiles.add(new Tile(new Rectangle2D.Double(0,0, tile.getBounds().getWidth(), tile.getBounds().getHeight())));
//        tiles.get(0).setFillColor(tile.getFillColor());
//    }
//}
//
