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

    public PatternGroup(String patternName, Surface surface, Tile tile) {
        if (surface.getTypeOfTile() == null) {
            surface.setTypeOfTile(tile);
        }
        switch (patternName) {
            case "Classic":
                pattern = new Classic(surface.getTypeOfTile());
                break;
            case "Offset":
                pattern = new Offset(surface.getTypeOfTile());
                break;
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
        /*System.out.println("rebuild");*/
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

                    double tempX = x;
                    double tempY = y;
                    for (int i = 0; i < tiles.size(); ++i) {
                        Tile tile = tiles.get(i);
                        Tile newTile = tile.copy();
                        Rectangle2D.Double pos = newTile.getBounds();

                        newTile.moveOf(tempX + (pos.x * pos.width), tempY + (pos.y * pos.height));
                        Path2D.Double[] cutedSurface = PolygonTransformer.subtract(newTile.getPolygon(), surface.getPolygon(), grout);
                        if (cutedSurface != null && cutedSurface.length != 0) {
                            if (cutedSurface.length == 1) {
                                newTile.setPolygon(cutedSurface[0]);
                            } else {
                                for (Path2D.Double cut : cutedSurface) {
                                    newTile = tile.copy();
                                    newTile.setPolygon(cut);
                                    this.tiles.add(newTile);
                                }
                            }
                            this.tiles.add(newTile);
                        }
                        // Calculate if grout should be applied here or outside the loop
                        tempX += (pattern.getGroutXRules()[i] * grout) * increments[step][0];
                        tempY += (pattern.getGroutYRules()[i] * grout) * increments[step][1];
                    }
                    x += (tileW * pattern.getOffsetX()) * increments[step][0];
                    x += (grout * pattern.getOffsetX()) * increments[step][0];
                }

                x = origin.x;
                y += (tileH * pattern.getOffsetY()) * increments[step][1];
                y += (grout * pattern.getOffsetY()) * increments[step][1];
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

    public Pattern getPattern() {
        return pattern;
    }
}
