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
import java.util.Iterator;
import java.util.Vector;

public class PatternGroup {

    private Pattern pattern = null;
    private Vector<Tile> tiles = new Vector<>();
    private float rotation;
    private boolean centered = false;

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
        buildPattern(surface, groutedSurface, (Vector2D) null);
    }

    private void buildPattern(@NotNull Surface surface, @NotNull Surface groutedSurface, Point2D origin) {
        buildPattern(surface, groutedSurface, new Vector2D(origin));
    }

    private void buildPattern(@NotNull Surface surface, @NotNull Surface groutedSurface, Vector2D origin) {
        final Vector<Tile> tiles = pattern.getTiles();
        final double grout = surface.getGrout().getThickness();

        final double tileH = tiles.get(0).getBounds().height;
        final double tileW = tiles.get(0).getBounds().width;

        // Pattern min - max
        final double patMinX = surface.getBounds().x;
        final double patMaxX = patMinX + surface.getBounds().width + tileW;
        final double patMinY = surface.getBounds().y;
        final double patMaxY = patMinY + surface.getBounds().height + tileH;

        if (origin == null)
            origin = new Vector2D(surface.getBounds().x, surface.getBounds().y);
        else
            origin = transformOrigin(origin, surface);

        System.out.println(origin);

        double x = origin.x;
        double y = origin.y;
        while (y <= patMaxY) {

            while (x <= patMaxX) {

                double tempX = x;
                double tempY = y;
                for (int i = 0; i < tiles.size(); ++i) {
                    Tile tile = tiles.get(i);
                    Tile newTile = tile.copy();
                    Rectangle2D.Double pos = newTile.getBounds();

                    newTile.moveOf(-pos.x, -pos.y);
                    newTile.moveOf(tempX + (pos.x * pos.width), tempY + (pos.y * pos.height));
                    Path2D.Double[] cutedSurface = PolygonTransformer.subtract(newTile.getPolygon(), surface.getPolygon(), grout);
                    if (cutedSurface != null && cutedSurface.length != 0) {
                        if (cutedSurface.length == 1) {
                            newTile.setPolygon(cutedSurface[0]);
                            this.tiles.add(newTile);
                        } else {
                            for (Path2D.Double cut : cutedSurface) {
                                newTile = tile.copy();
                                newTile.setPolygon(cut);
                                this.tiles.add(newTile);
                            }
                        }
                    }
                    // Calculate if grout should be applied here or outside the loop
                    tempX += (pattern.getGroutXRules()[i] * grout);
                    tempY += (pattern.getGroutYRules()[i] * grout);
                }
                x += (tileW * pattern.getOffsetX());
                x += (grout * pattern.getOffsetX());
            }

            x = origin.x;
            y += (tileH * pattern.getOffsetY());
            y += (grout * pattern.getOffsetY());
        }
    }

    private Vector2D transformOrigin(Vector2D origin, Surface surface) {
        Rectangle2D.Double patBounds = getPatternBounds();
        Rectangle2D.Double surBounds = surface.getBounds();
        final double grout = surface.getGrout().getThickness();

        origin.x -= patBounds.width / 2;
        origin.y -= patBounds.height / 2;
        while (origin.x > surBounds.x) {
            origin.x -= grout + patBounds.width;
        }
        while (origin.y > surBounds.y) {
            origin.y -= grout + patBounds.height;
        }

        return origin;
    }

    private Rectangle2D.Double getPatternBounds() {
        Rectangle2D.Double ret = new Rectangle2D.Double();
        Iterator<Tile> it = pattern.getTiles().iterator();

        if (!it.hasNext()) {
            return ret;
        }

        Tile tile = it.next();
        ret = tile.getBounds();

        while (it.hasNext()) {
            tile = it.next();
            Rectangle2D.Double bounds = tile.getBounds();
            final double tx = bounds.x * bounds.width;
            final double ty = bounds.y * bounds.height;
            final double tw = bounds.x * bounds.width + bounds.width;
            final double th = bounds.y * bounds.height + bounds.height;

            if (ret.x > bounds.x)
                ret.x = bounds.x;
            if (ret.y > bounds.y)
                ret.y = bounds.y;
            if (ret.x + ret.width > bounds.x + bounds.width)
                ret.width += (bounds.x + bounds.width) - (ret.x + ret.width);
            if (ret.y + ret.height > bounds.y + bounds.height)
                ret.height += (bounds.y + bounds.height) - (ret.y + ret.height);
        }

        return ret;
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

    public void setCentered(boolean centered) {
        this.centered = centered;
    }
}
