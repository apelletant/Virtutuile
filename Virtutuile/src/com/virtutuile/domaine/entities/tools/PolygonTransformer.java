package com.virtutuile.domaine.entities.tools;

import com.virtutuile.shared.NotNull;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.awt.geom.Path2D;
import java.util.Iterator;
import java.util.Vector;

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
        if (!it.hasNext())
            return null;
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

    static @NotNull Path2D.Double[] javafxPathsToAwt(Path path) {
        Vector<Path2D.Double> ret = new Vector<>(2);

        Iterator<PathElement> it = path.getElements().iterator();
        if (!it.hasNext())
            return null;
        Path2D.Double p = new Path2D.Double();
        for (PathElement pe = null; it.hasNext();) {
            pe = it.next();
            if (pe instanceof MoveTo)
                p.moveTo(((MoveTo) pe).getX(), ((MoveTo) pe).getY());
            else if (pe instanceof LineTo)
                p.lineTo(((LineTo) pe).getX(), ((LineTo) pe).getY());
            else if (pe instanceof ClosePath) {
                p.closePath();
                ret.add(p);
                p = new Path2D.Double();
            }
        }
        return ret.toArray(new Path2D.Double[0]);
    }

    static public Path2D.Double[] subtract(java.awt.geom.Path2D polygon, java.awt.geom.Path2D cuttingPattern, double cuttingInline) {
        Path poly = awtPathToJavafx(polygon);
        Path cut = awtPathToJavafx(cuttingPattern);

        Shape shape = Shape.intersect(poly, cut);
        return javafxPathsToAwt((Path) shape);
    }

    static public java.awt.geom.Path2D.Double merge(java.awt.geom.Path2D polygon1, java.awt.geom.Path2D polygon2) {
        Path poly1 = awtPathToJavafx(polygon1);
        Path poly2 = awtPathToJavafx(polygon2);

        return javafxPathToAwt((Path) Shape.union(poly1, poly2));
    }
}
