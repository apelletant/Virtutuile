package com.virtutuile.domaine.entities;

import com.virtutuile.domaine.entities.patterns.Classic;
import com.virtutuile.domaine.entities.patterns.Pattern;
import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.domaine.entities.surfaces.Tile;
import com.virtutuile.domaine.entities.tools.Intersection;
import com.virtutuile.shared.CustomPoint;
import com.virtutuile.shared.Vecteur;

import java.awt.*;
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
        switch (patternName) {
            case "Classic":
                pattern = new Classic();
            default:
                break;
        }
        if (this.pattern != null) {
//            this.buildPattern(surface);
            Rectangle2D.Double bounds = surface.getBounds();
            double groutThickness = surface.getGrout().getThickness();
            Surface groutedSurface = new Surface(surface);
            /*System.out.println("Current bounds " + bounds.toString());*/
            groutedSurface.resize(bounds.width - groutThickness, bounds.height - groutThickness);
            /*System.out.println("New bounds " + groutedSurface.getBounds().toString());*/
            this.buildPattern(surface, groutedSurface);
        }
    }

    public PatternGroup(PatternGroup patternGroup) {
    }

    private void buildPattern(Surface surface, Surface groutedSurface) {
        double[] adjust = pattern.getAdjust();
        double[] origin = new double[]{surface.getPolygon().getBounds().getX(), surface.getPolygon().getBounds2D().getY()};
        double[] tileSize = {pattern.getTiles().get(0).getPolygon().getBounds().width, pattern.getTiles().get(0).getPolygon().getBounds().height};
        double y = 0;
        double x = 0;

        while (y < surface.getPolygon().getBounds().getHeight() + surface.getGrout().getThickness()) {
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
        removeTileOutOfSurface(surface, groutedSurface);
    }

    private void removeTileOutOfSurface(Surface surface, Surface groutedSurface) {
        Vector<Tile> tilesToRemove = new Vector<>();
        tiles.forEach((tile) -> {
            if (!surface.getPolygon().contains(tile.getPolygon().getBounds2D())) {
                if (!adjustTileIfIntersect(surface, tile, groutedSurface)) {
                    tilesToRemove.add(tile);
                }
            }
        });
        tilesToRemove.forEach((tileToRemove) -> {
            tiles.remove(tileToRemove);
        });
    }

    private boolean adjustTileIfIntersect(Surface surface, Tile tile, Surface groutedSurface) {
        Point2D[] tileVertices = tile.getVertices();
        Point2D[] groutedSurfaceVertices = groutedSurface.getVertices();

        Point2D aTile;
        Point2D bTile;

        Point2D aSurface;
        Point2D bSurface;

        boolean returnedValue = false;
        boolean restart = false;

        int verticesSurfaceIterator = 0;
        int verticesTileIterator = 0;

        /*System.out.println("   ");
        System.out.println("New tile");
*/
        while (verticesSurfaceIterator < groutedSurfaceVertices.length) {
            aSurface = groutedSurfaceVertices[verticesSurfaceIterator];
            if (verticesSurfaceIterator == groutedSurface.getVertices().length - 1) {
                bSurface = groutedSurfaceVertices[0];
            } else {
                bSurface = groutedSurfaceVertices[verticesSurfaceIterator + 1];
            }
            /*System.out.println("Surface Line: " + aSurface.getX() + ", " + aSurface.getY() + " - " + bSurface.getX() + ", " + bSurface.getY());*/
            while (verticesTileIterator < tile.getVertices().length) {
                aTile = tileVertices[verticesTileIterator];
                if (verticesTileIterator == tile.getVertices().length - 1) {
                    bTile = tileVertices[0];
                } else {
                    bTile = tileVertices[verticesTileIterator + 1];
                }
                /*System.out.println("Tile Line: " + aTile.getX() + ", " + aTile.getY() + " - " + bTile.getX() + ", " + bTile.getY());*/
                CustomPoint intersection = Intersection.intersectionPoint(
                        new Vecteur(
                                new CustomPoint(aTile.getX(), aTile.getY()),
                                new CustomPoint(bTile.getX(), bTile.getY())),
                        new Vecteur(
                                new CustomPoint(aSurface.getX(), aSurface.getY()),
                                new CustomPoint(bSurface.getX(), bSurface.getY())));
                if (intersection != null) {
                    /*System.out.println("Intersection: " + intersection.x + ", " + intersection.y);
                    System.out.println("if ((intersection.x " + intersection.x + " != aTile.x " + aTile.getX() + " || intersection.y " + intersection.y + " != aTile.y " + aTile.getY()
                    + ") && ( intersection.x " + intersection.x + " != bTile.x " + bTile.getX() + " || intersection.y " + intersection.y + " != bTile.y " + bTile.getY() + ")");
                    System.out.println(" ");*/
                }
                if (intersection != null
                        && (intersection.x != aTile.getX() || intersection.y != aTile.getY())
                        && (intersection.x != bTile.getX() || intersection.y != bTile.getY())) {

                    tile.setFillColor(Color.RED);
                    addNewVertexOnTile(tile, aTile, bTile, intersection);
                    tileVertices = tile.getVertices();
                    returnedValue = true;
                    restart = true;
                    break;
                }
                verticesTileIterator++;
            }
            if (restart) {
                verticesSurfaceIterator = -1;
                restart = false;
            }
            verticesSurfaceIterator++;
            verticesTileIterator = 0;
        }
        if (returnedValue) {
            removeUselessPoints(tile, groutedSurface);
        }
        return returnedValue;
    }

    private void addNewVertexOnTile(Tile tile, Point2D aTile, Point2D bTile, CustomPoint newVertex) {
        Point2D[] vertices = tile.getVertices();
        Path2D.Double newTile = new Path2D.Double();
        int i = 0;

        newTile.moveTo(vertices[0].getX(), vertices[0].getY());

        if (!(vertices[i].getX() == aTile.getX() && vertices[i].getY() == aTile.getY())) {
            i++;
        }
        while (!(vertices[i].getX() == aTile.getX() && vertices[i].getY() == aTile.getY())) {
            newTile.lineTo(vertices[i].getX(), vertices[i].getY());
            i++;
        }
        if (!(vertices[0].getX() == aTile.getX() && vertices[0].getY() == aTile.getY())) {
            newTile.lineTo(aTile.getX(), aTile.getY());
        }
        newTile.lineTo(newVertex.x, newVertex.y);
        newTile.lineTo(bTile.getX(), bTile.getY());
        ++i;
        if (i == vertices.length - 1) {
            newTile.closePath();
            tile.setPolygon(newTile);
            return;
        } else {
            ++i;
        }
        while (i < vertices.length) {
            newTile.lineTo(vertices[i].getX(), vertices[i].getY());
            i++;
        }
        newTile.closePath();
        tile.setPolygon(newTile);
    }

    private void removeUselessPoints(Tile tile, Surface groutedSurface) {
        Point2D[] tileVertices = tile.getVertices();
        Vector<Point2D> vertices = new Vector<>();
        Path2D.Double cuttedTile = new Path2D.Double();


        for (int i = 0; i < tileVertices.length; i++) {
            if (groutedSurface.containsOrIntersect(tileVertices[i])) {
                vertices.add(tileVertices[i]);
            }
        }

        cuttedTile.moveTo(vertices.get(0).getX(), vertices.get(0).getY());
        for (int i = 1; i < vertices.size(); i++) {
            cuttedTile.lineTo(vertices.get(i).getX(), vertices.get(i).getY());
        }
        cuttedTile.closePath();
        tile.setPolygon(cuttedTile);
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
