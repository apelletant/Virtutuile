package com.virtutuile.domaine.entities.tools;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.Iterator;

public class PolygonTransformer {

    static Path awtPathToJavafx(java.awt.geom.Path2D path) {
        double[][] vertices = Vertices.getPath2DVertices(path);
        PathElement[] elements = new PathElement[vertices.length + 1];
        int i = 1;

        // Convert all vertices into PathElement
        elements[0] = new MoveTo(vertices[0][0], vertices[0][1]);
        for (; i < vertices.length; ++i) {
            elements[i] = new LineTo(vertices[i][0], vertices[i][1]);
        }
        elements[i] = new ClosePath();

        // Making a path with all PathElement
        Path ret = new Path();
        ret.setStrokeWidth(0.001);
        ret.setStrokeType(StrokeType.INSIDE);
        ret.setFill(Color.RED);
        ret.getElements().addAll(elements);

        return ret;
    }

    static java.awt.geom.Path2D.Double javafxPathToAwt(Path path) {
        java.awt.geom.Path2D.Double ret = new java.awt.geom.Path2D.Double();

        Iterator<PathElement> it = path.getElements().iterator();
        PathElement pe = it.next();
        ret.moveTo(((MoveTo) pe).getX(), ((MoveTo) pe).getY());
        for (; it.hasNext(); pe = it.next()) {
            if (pe instanceof LineTo)
                ret.lineTo(((LineTo) pe).getX(), ((LineTo) pe).getY());
            if (pe instanceof ClosePath)
                break;
        }
        ret.closePath();
        return ret;
    }

    static public java.awt.geom.Path2D.Double subtract(java.awt.geom.Path2D polygon, java.awt.geom.Path2D cuttingPattern, boolean keepRemove) {
        Path poly = awtPathToJavafx(polygon);
        Path cut = awtPathToJavafx(cuttingPattern);

        Shape shape = Shape.intersect(poly, cut);
        return javafxPathToAwt((Path) shape);
    }

    static public java.awt.geom.Path2D.Double merge(java.awt.geom.Path2D polygon1, java.awt.geom.Path2D polygon2) {
        Path poly1 = awtPathToJavafx(polygon1);
        Path poly2 = awtPathToJavafx(polygon2);

        return javafxPathToAwt((Path) Shape.union(poly1, poly2));
    }

    /**
     * This method slice iteratively a shape like following
     *   x----------------------------------|---x
     *   |                                  /   |
     *   |                                 /    |
     *   |                        x-------x     |
     *   |                        |             |
     *   x------------------------|-------------x
     *                            ^
     *               This is the cutting pattern
     *       This should be a Path2D but not necessary closed
     */
//    public Path2D[] cut(Path2D polygon, Path2D cuttingPattern) {
//        double[][] tileVertices = Vertices.getPath2DVertices(polygon);
//        double[][] pathVertices = Vertices.getPath2DVertices(cuttingPattern);
//        Vector<Vector<double[]>> polygons = new Vector<>(2);
//        CustomPoint p = null;
//        boolean lock = true;
//        int currentTile = 0;
//        polygons.add(new Vector<>(4));
//
//        javafx.scene.shape.Shape sape = new javafx.scene.shape.Path();
//
//        while (lock) {
//            lock = false;
//            // Iterate over tile vertices
//            for (int i = 0; i < tileVertices.length - 1; ++i) {
//                // tX represent the segment AB of a tile
//                double[] tA = tileVertices[i];
//                double[] tB = (tileVertices.length + 1 < i) ? tileVertices[i + 1] : tileVertices[0];
//
//                double[] pA;
//                double[] pB;
//
//                // Adding first point to te current looking tile
//                polygons.get(currentTile).add(tA);
//
//                // Iterate over cutting path vertices
//                for (int j = 0; j < pathVertices.length - 1; ++j) {
//                    // pX represent the segment AB of the cutting Path2D
//                    pA = pathVertices[j];
//                    pB = (pathVertices.length + 1 < j) ? pathVertices[j + 1] : pathVertices[0];
//
//                    // Looking for an intersection
//                    p = Intersection.intersectionPoint(new Vecteur(
//                                    new CustomPoint(pA[0], pA[1]),
//                                    new CustomPoint(pB[0], pB[1])),
//                            new Vecteur(
//                                    new CustomPoint(tA[0], tA[1]),
//                                    new CustomPoint(tB[0], tB[1])));
//
//                    if (p != null) {
//                        break;
//                    }
//                }
//
//                // If there is an intersection
//                if (p != null && !lock) {
//                    // TODO: Looking if this is a new tile or not
//                    polygons.get(currentTile).add(new double[]{p.x, p.y});
//                    lock = true;
//
//                    // Add if we're slicing another shape
//                    if (currentTile != 0) {
//                        currentTile = 0;
//                        polygons.get(currentTile).add(new double[]{p.x, p.y});
//                    } else {
//                        currentTile = polygons.size() - 1;
//                        polygons.add(currentTile, new Vector<>(4));
//                        polygons.get(currentTile).add(new double[]{p.x, p.y});
//                    }
//                }
//
//                // If the tile segment is the last add B to the current editing tile
//                if (i + 1 == tileVertices.length)
//                    polygons.get(currentTile).add(tB);
//            }
//        }
//
//
//        return null;
//    }

}
