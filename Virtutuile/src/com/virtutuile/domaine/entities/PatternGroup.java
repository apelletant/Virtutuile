package com.virtutuile.domaine.entities;

import com.virtutuile.domaine.entities.patterns.Classic;
import com.virtutuile.domaine.entities.patterns.Pattern;
import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.domaine.entities.surfaces.Tile;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Optional;
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
                System.out.println(" ");
                /*System.out.println(Arrays.toString(tile.getVertices()));*/
                System.out.println(" ");
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
        System.out.println("new Tile check");

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
                System.out.println(verticesTileIterator);
                System.out.println(tile.getVertices().length - 1);

                if (verticesTileIterator == tile.getVertices().length - 1) {
                    BTile = tileVertices[0];

                } else {
                    BTile = tileVertices[verticesTileIterator + 1];
                }


                Point2D intersection = lineIntersection(ATile, BTile, ASurface, BSurface);
                if (intersection != null) {
                    //ajouter le point à la tuile
                    //update tileVertices

                    /*tileVertices = tile.getVertices();*/
                    tile.setFillColor(Color.RED);
                    returnedValue = true;
                    verticesTileIterator++;
                } else  {
                    verticesTileIterator++;
                }
            }

            verticesSurfaceIterator++;
        }
        System.out.print(returnedValue);
        return returnedValue;
    }

    //https://www.geeksforgeeks.org/program-for-point-of-intersection-of-two-lines/
    private Point2D lineIntersection(Point2D A, Point2D B, Point2D C, Point2D D)
    {
        // Line AB represented as a1x + b1y = c1
        double a1 = B.getY() - A.getY();
        double b1 = A.getX() - B.getX();
        double c1 = a1*(A.getX()) + b1*(A.getY());

        // Line CD represented as a2x + b2y = c2
        double a2 = D.getY() - C.getY();
        double b2 = C.getX() - D.getX();
        double c2 = a2*(C.getX())+ b2*(C.getY());

        double determinant = a1*b2 - a2*b1;

        if (determinant == 0) {
            System.out.println("No Intersect");
            return null;
        } else {
            double x = (b2*c1 - b1*c2)/determinant;
            double y = (a1*c2 - a2*c1)/determinant;
            System.out.println("Intersect here: " + x + " " + y);
            return new Point2D.Double(x, y);
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
