package com.virtutuile.domaine.entities.patterns;

import com.virtutuile.domaine.entities.surfaces.Tile;

import java.awt.geom.Rectangle2D;

public class Classic extends Pattern {
    public Classic(Tile tile) {
        adjust = new double[]{0, 0};
        if (tile != null) {
            tiles.add(new Tile(new Rectangle2D.Double(0,0, tile.getBounds().getWidth(), tile.getBounds().getHeight())));
            tiles.get(0).setFillColor(tile.getFillColor());
        } else {
            tiles.add(new Tile(new Rectangle2D.Double(0,0, defaultDimensions.getX(), defaultDimensions.getY())));
            tiles.get(0).setFillColor(tile.getFillColor());
        }
    }

    @Override
    public void setTileType(Tile tile) {
        tiles.clear();
        tiles.add(new Tile(new Rectangle2D.Double(0,0, tile.getBounds().getWidth(), tile.getBounds().getHeight())));
        tiles.get(0).setFillColor(tile.getFillColor());
    }
}
