package com.virtutuile.domaine.entities.patterns;

import com.virtutuile.domaine.entities.surfaces.Tile;

import java.awt.geom.Rectangle2D;

public class Offset extends Pattern {
/*
    public Offset(Tile tile) {
        adjust = new double[]{0, 0};
        offsetX = new double[]{1};
        offsetY = new double[]{1};
        if (tile != null) {
            tiles.add(new Tile(new Rectangle2D.Double(0,0, tile.getBounds().getWidth(), tile.getBounds().getHeight())));
            tiles.get(0).setFillColor(tile.getFillColor());
            tiles.add(new Tile(new Rectangle2D.Double(tile.getBounds().getWidth() / 2,tile.getBounds().getHeight(), tile.getBounds().getWidth(), tile.getBounds().getHeight())));
            tiles.get(1).setFillColor(tile.getFillColor());
        } else {
            tiles.add(new Tile(new Rectangle2D.Double(0,0, defaultDimensions.getX(), defaultDimensions.getY())));
            tiles.add(new Tile(new Rectangle2D.Double(defaultDimensions.getX() / 2,defaultDimensions.getY(), defaultDimensions.getX(), defaultDimensions.getY())));
        }
    }

    @Override
    public void setTileType(Tile tile) {
        tiles.clear();
        tiles.add(new Tile(new Rectangle2D.Double(0,0, tile.getBounds().getWidth(), tile.getBounds().getHeight())));
        tiles.get(0).setFillColor(tile.getFillColor());
        tiles.add(new Tile(new Rectangle2D.Double(tile.getBounds().getWidth() / 2,tile.getBounds().getHeight(), tile.getBounds().getWidth(), tile.getBounds().getHeight())));
        tiles.get(1).setFillColor(tile.getFillColor());
    }*/
public Offset(Tile tile) {
    adjust = new double[]{0, 0};
    offsetX = new double[]{1};
    offsetY = new double[]{1};
    if (tile != null) {
        tiles.add(new Tile(new Rectangle2D.Double(0,0, tile.getBounds().getWidth(), tile.getBounds().getHeight())));
        tiles.get(0).setFillColor(tile.getFillColor());
    } else {
        tiles.add(new Tile(new Rectangle2D.Double(0,0, defaultDimensions.getX(), defaultDimensions.getY())));
    }
}

    @Override
    public void setTileType(Tile tile) {
        tiles.clear();
        tiles.add(new Tile(new Rectangle2D.Double(0,0, tile.getBounds().getWidth(), tile.getBounds().getHeight())));
        tiles.get(0).setFillColor(tile.getFillColor());
    }
}

