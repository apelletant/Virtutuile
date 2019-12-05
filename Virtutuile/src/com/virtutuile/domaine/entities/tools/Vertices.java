package com.virtutuile.domaine.entities.tools;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Vector;

public class Vertices {
    public static double[][] getPath2DVertices(Path2D path2D) {
        Vector<double[]> ret = new Vector<>();
        boolean isFirst = true;
        double[] firstCoords = new double[2];
        double[] coords = new double[2];

        for (PathIterator pi = path2D.getPathIterator(null); !pi.isDone(); pi.next()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO:
                case PathIterator.SEG_LINETO:
                    if (isFirst) {
                        firstCoords[0] = coords[0];
                        firstCoords[1] = coords[1];
                        isFirst = false;
                    }
                    ret.add(new double[]{coords[0], coords[1]});
                    break;
                case PathIterator.SEG_CLOSE:
                    break;
                default:
                    throw new IllegalArgumentException("Path contains curves");
            }
        }
        return ret.toArray(new double[ret.size()][2]);
    }
}
