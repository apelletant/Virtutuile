package com.virtutuile.domaine.entities;

import com.virtutuile.domaine.entities.patterns.Classic;
import com.virtutuile.domaine.entities.patterns.Pattern;
import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.domaine.entities.surfaces.Tile;
import com.virtutuile.domaine.entities.tools.Intersection;
import com.virtutuile.shared.CustomPoint;
import com.virtutuile.shared.Vecteur;

import java.awt.*;
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
        if (this.pattern != null) {
            this.buildPattern(surface);
        }
    }

    public PatternGroup(PatternGroup patternGroup) {
    }

    private void buildPattern(Surface surface) {
        double[] adjust = pattern.getAdjust();
        double[] origin = new double[]{surface.getPolygon().getBounds().getX(), surface.getPolygon().getBounds2D().getY()};
        double[] tileSize = {pattern.getTiles().get(0).getPolygon().getBounds().width, pattern.getTiles().get(0).getPolygon().getBounds().height};
        double y = 0;
        double x = 0;

        while(y < surface.getPolygon().getBounds().getHeight() + surface.getGrout().getThickness()) {
            y = y + surface.getGrout().getThickness();
            while (x < surface.getPolygon().getBounds().getWidth() + surface.getGrout().getThickness()) {
                int i = 0;
                x = x + surface.getGrout().getThickness();
                while (i != pattern.getTiles().size()) {
                    Tile tile = pattern.getTiles().get(i).copy();
                    double tileX = x + origin[0];
                    double tileY = y + origin[1];

                    if ((tileX + surface.getGrout().getThickness() + tileSize[0]) > origin[0] + surface.getPolygon().getBounds().getWidth()) {
                        double size = (origin[0] + surface.getPolygon().getBounds().getWidth()) - (x + origin[0] + surface.getGrout().getThickness());
                        tile.setWidthForRectTile(size);
                    }

                    if ((tileY + surface.getGrout().getThickness() + tileSize[1]) > origin[1] + surface.getPolygon().getBounds().getHeight()) {
                        double size = (origin[1] + surface.getPolygon().getBounds().getHeight()) - (y + origin[1] + surface.getGrout().getThickness());
                        tile.setHeightForRectTile(size);
                    }
                    tile.setOrigin(new Point2D.Double(tileX, tileY));
                    tiles.add(tile);
                    i++;
                }
                x = x + tileSize[0] + adjust[0];
            }
            y = y + tileSize[1] + adjust[1];
            x = adjust[0];
        }
        removeTileOutOfSurface(surface);
    }

    private void removeTileOutOfSurface(Surface surface) {
        Vector<Tile> tilesToRemove = new Vector<>();
        tiles.forEach((tile) -> {
            if (!surface.getPolygon().contains(tile.getPolygon().getBounds2D())) {
                if (!adjustTileIfIntersect(surface, tile)) {
                    tilesToRemove.add(tile);
                }
            }
        });
        tilesToRemove.forEach((tileToRemove) -> {
            tiles.remove(tileToRemove);
        });
    }

    private boolean adjustTileIfIntersect(Surface surface, Tile tile) {
        Point2D[] tileVertices = tile.getVertices();
        Point2D[] surfaceVertices = surface.getVertices();

        Point2D ATile;
        Point2D BTile;

        Point2D ASurface;
        Point2D BSurface;

        boolean returnedValue = false;

        int verticesSurfaceIterator = 0;
        int verticesTileIterator = 0;

        while (verticesSurfaceIterator < surfaceVertices.length - 1) {
            ASurface = surfaceVertices[verticesSurfaceIterator];
            if (verticesSurfaceIterator == surface.getVertices().length) {
                BSurface = surfaceVertices[0];
            } else {
                BSurface = surfaceVertices[verticesSurfaceIterator + 1];
            }
            while (verticesTileIterator < tile.getVertices().length) {
                ATile = tileVertices[verticesTileIterator];
                if (verticesTileIterator == tile.getVertices().length - 1) {
                    BTile = tileVertices[0];

                } else {
                    BTile = tileVertices[verticesTileIterator + 1];
                }
                CustomPoint intersection = Intersection.intersectionPoint(
                        new Vecteur(
                                new CustomPoint(ATile.getX(), ATile.getY()),
                                new CustomPoint(BTile.getX(), BTile.getY())),
                        new Vecteur(
                                new CustomPoint(ASurface.getX(), ASurface.getY()),
                                new CustomPoint(BSurface.getX(), BSurface.getY())));
                if (intersection != null) {
                    tile.setFillColor(Color.RED);
                    tile.setBorderColor(Color.RED);
                    returnedValue = true;
                }
                verticesTileIterator++;
            }
            verticesSurfaceIterator++;
            verticesTileIterator = 0;
        }
        return returnedValue;
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
