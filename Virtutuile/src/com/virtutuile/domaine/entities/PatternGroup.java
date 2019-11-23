package com.virtutuile.domaine.entities;

import com.virtutuile.domaine.entities.patterns.Classic;
import com.virtutuile.domaine.entities.patterns.Offset;
import com.virtutuile.domaine.entities.patterns.Pattern;
import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.domaine.entities.surfaces.Tile;
import com.virtutuile.domaine.entities.tools.PolygonTransformer;
import com.virtutuile.shared.NotNull;
import com.virtutuile.shared.Vector2D;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class PatternGroup {

    private Pattern pattern = null;
    private Vector<Tile> tiles = new Vector<>();
    private float rotation;
    private Point2D point2D;

    public PatternGroup(String patternName, Surface surface) {
        System.out.println("name : " + patternName);
        switch (patternName) {
            case "Classic":
                pattern = new Classic(surface.getTypeOfTile());
            case "Offset":
                pattern = new Offset(surface.getTypeOfTile());
            default:
                break;
        }
        if (this.pattern != null) {
            this.buildPattern(surface, transformToSurfaceWithoutSideGrout(surface));
            surface.setFillColor(surface.getGrout().getColor());
        }
    }

    public PatternGroup(PatternGroup patternGroup) {
    }

    public void recalcPattern(Surface surface) {
        tiles = new Vector<>();
        pattern.setTileType(surface.getTypeOfTile());
        buildPattern(surface, transformToSurfaceWithoutSideGrout(surface));
    }

    public void changeTileType(Surface surface, Tile tile) {
        pattern.setTileType(tile);
        recalcPattern(surface);
    }

    private Surface transformToSurfaceWithoutSideGrout(Surface surface) {
        if (this.pattern != null) {
            Rectangle2D.Double bounds = surface.getBounds();
            double groutThickness = surface.getGrout().getThickness();
            Surface groutedSurface = new Surface(surface);

            AffineTransform af = new AffineTransform();

            af.translate((bounds.x + bounds.width / 2) * -1, (bounds.y + bounds.height / 2) * -1);


//            groutedSurface.resize(bounds.width - groutThickness, bounds.height - groutThickness);
            return groutedSurface;
        }
        return null;
    }

    private void buildPattern(@NotNull Surface surface, @NotNull Surface groutedSurface) {
        buildPattern(surface, groutedSurface, new Vector2D(surface.getBounds().x, surface.getBounds().y));
    }

    private void buildPattern(@NotNull Surface surface, @NotNull Surface groutedSurface, Vector2D origin) {
        System.out.println("rebuild");
        final Vector<Tile> tiles = pattern.getTiles();
        final double grout = surface.getGrout().getThickness();

        final double tileH = tiles.get(0).getBounds().height;
        final double tileW = tiles.get(0).getBounds().width;

        // Pattern min - max
        final double patMinX = surface.getBounds().x;
        final double patMaxX = patMinX + surface.getBounds().width;
        final double patMinY = surface.getBounds().y;
        final double patMaxY = patMinY + surface.getBounds().height;

        // To increment in the 4 directions without code duplicates (1;1), (1;-1), (-1;-1) and (-1;1)
        final int[][] increments = new int[][]{{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        int step = 0;


        double x = origin.x;
        double y = origin.y;

        if (origin == null) {
            origin = new Vector2D(surface.getBounds().x, surface.getBounds().y);
        }

        while (step < 4) {

            while (y >= patMinY && y <= patMaxY) {

                while (x >= patMinX && x <= patMaxX) {

                    for (Tile tile : tiles) {
                        Tile newTile = tile.copy();
                        newTile.moveOf(x, y);
                        x += tileW * increments[step][0];
                        Path2D.Double cutedSurface = PolygonTransformer.subtract(newTile.getPolygon(), surface.getPolygon(), grout);
                        if (cutedSurface == null)
                            continue;
                        newTile.setPolygon(cutedSurface);
                        this.tiles.add(newTile);
                    }
                    x += grout * increments[step][0];
                }

                x = origin.x;
                y += tileH * increments[step][1];
                y += grout * increments[step][1];
            }

            y = origin.y;
            ++step;
        }
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
