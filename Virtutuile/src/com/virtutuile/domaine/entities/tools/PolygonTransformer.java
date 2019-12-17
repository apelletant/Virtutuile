package com.virtutuile.domaine.entities.tools;

import com.virtutuile.shared.NotNull;
import com.virtutuile.shared.Vecteur;
import com.virtutuile.shared.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Vector;

public class PolygonTransformer {

    static Path awtPathToJavafx(java.awt.geom.Path2D path) {
        // Making a path with all PathElement
        Path ret = new Path();
        ret.setStrokeWidth(0.001);
        ret.setStrokeType(StrokeType.INSIDE);
        ret.setFill(Color.RED);

        double[] coords = new double[6];
        for (PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_CUBICTO:
                    ret.getElements().add(new CubicCurveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]));
                    break;
                case PathIterator.SEG_QUADTO:
                    ret.getElements().add(new QuadCurveTo(coords[0], coords[1], coords[2], coords[3]));
                    break;
                case PathIterator.SEG_MOVETO:
                    ret.getElements().add(new MoveTo(coords[0], coords[1]));
                    break;
                case PathIterator.SEG_LINETO:
                    ret.getElements().add(new LineTo(coords[0], coords[1]));
                    break;
                case PathIterator.SEG_CLOSE:
                    ret.getElements().add(new ClosePath());
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return ret;
    }

    static public Path2D.Double[] explodePaths(Path2D.Double path) {
        Vector<Path2D.Double> ret = new Vector<>();
        Path2D.Double cur = new Path2D.Double();

        if (path == null)
            return null;
        double[] coords = new double[6];
        for (PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_CUBICTO:
                    cur.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
                    break;
                case PathIterator.SEG_QUADTO:
                    cur.quadTo(coords[0], coords[1], coords[2], coords[3]);
                    break;
                case PathIterator.SEG_MOVETO:
                    cur.moveTo(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    cur.lineTo(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_CLOSE:
                    cur.closePath();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return ret.toArray(new Path2D.Double[0]);
    }

    static Path2D.Double javafxPathToAwtSingle(Path path) {
        Path2D.Double ret = new Path2D.Double();

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

    static Path2D.Double javafxPathToAwt(Path path) {
        Path2D.Double ret = new Path2D.Double();

        Iterator<PathElement> it = path.getElements().iterator();
        if (!it.hasNext())
            return null;
        PathElement pe = it.next();
        for (; it.hasNext(); pe = it.next()) {
            if (pe instanceof CubicCurveTo) {
                CubicCurveTo cct = (CubicCurveTo) pe;
                ret.curveTo(cct.getControlX1(), cct.getControlY1(), cct.getControlX2(), cct.getControlY2(), cct.getX(), cct.getY());
            } else if (pe instanceof QuadCurveTo) {
                QuadCurveTo qct = (QuadCurveTo) pe;
                ret.quadTo(qct.getControlX(), qct.getControlY(), qct.getX(), qct.getY());
            } else if (pe instanceof LineTo)
                ret.lineTo(((LineTo) pe).getX(), ((LineTo) pe).getY());
            else if (pe instanceof MoveTo)
                ret.moveTo(((MoveTo) pe).getX(), ((MoveTo) pe).getY());
            else if (pe instanceof ClosePath)
                ret.closePath();
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
        for (PathElement pe = null; it.hasNext(); ) {
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

    static public Path2D.Double[] poopSubtract(Path2D polygon, Path2D cuttingPattern) {
        Path poly = awtPathToJavafx(polygon);
        Path cut = awtPathToJavafx(cuttingPattern);

        Shape shape = Shape.intersect(poly, cut);
        return javafxPathsToAwt((Path) shape);
    }

    static public Path2D.Double subtract(Path2D polygon, Path2D cuttingPattern) {
        Path poly = awtPathToJavafx(polygon);
        Path cut = awtPathToJavafx(cuttingPattern);

        Shape shape = Shape.intersect(poly, cut);
        return javafxPathToAwt((Path) shape);
    }

    static public Path2D.Double hardSubtract(Path2D polygon, Path2D cuttingPattern) {
        Path poly = awtPathToJavafx(polygon);
        Path cut = awtPathToJavafx(cuttingPattern);

        Shape shape = Shape.subtract(poly, cut);
        return javafxPathToAwt((Path) shape);
    }

    static public Path2D.Double[] merge(Path2D polygon1, Path2D polygon2) {
        Path poly1 = awtPathToJavafx(polygon1);
        Path poly2 = awtPathToJavafx(polygon2);

        return javafxPathsToAwt((Path) Shape.union(poly1, poly2));
    }

    static public Point2D[][] extractVertices(Path2D polygon) {
        Vector<Point2D[]> ret = new Vector<>(10);
        double[] coords = new double[2];

        Vector<Point2D> v = new Vector<>(10);
        for (PathIterator pi = polygon.getPathIterator(null); !pi.isDone(); pi.next()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO:
                case PathIterator.SEG_LINETO:
                    v.add(new Point2D.Double(coords[0], coords[1]));
                    break;
                case PathIterator.SEG_CLOSE:
                    ret.add(v.toArray(new Point2D[0]));
                    v = new Vector<>(10);
                    break;
                default:
                    throw new IllegalArgumentException("Path contains curves");
            }
        }
        return ret.toArray(new Point2D[0][]);
    }

    static public Path2D.Double flate(Path2D polygon, double offset) {
        Vector<Vector2D[]> vertices = new Vector<>(10);
        double[] pts = new double[6];
        Vector2D rA = new Vector2D();
        Vector2D rB = new Vector2D();
        Vector2D rF = new Vector2D();
        Vector2D vector, a1, a2, b1, b2;

        PathIterator it = polygon.getPathIterator(null);
        while (!it.isDone()) {
            switch (it.currentSegment(pts)) {
                case PathIterator.SEG_MOVETO:
                    rB.x = pts[0];
                    rB.y = pts[1];
                    rF = rB.copy();
                    break;
                case PathIterator.SEG_LINETO:
                    rB.x = pts[0];
                    rB.y = pts[1];

                    vector = rB.copy().subtract(rA);
                    // Rotate of 90°
                    vector.normalize().rotateRad(Math.PI / 2).multiply(offset);
                    b1 = rB.copy().add(vector);
                    b2 = rB.copy().subtract(vector);
                    a1 = rA.copy().add(vector);
                    a2 = rA.copy().subtract(vector);

                    vertices.add(new Vector2D[]{a1, a2});
                    vertices.add(new Vector2D[]{b1, b2});
                    break;
                case PathIterator.SEG_CLOSE:
                    rA = rF.copy();

                    vector = rA.copy().subtract(rB);
                    // Rotate of 90°
                    vector.normalize().rotateRad(Math.PI / 2).multiply(offset);
                    b1 = rB.copy().add(vector);
                    b2 = rB.copy().subtract(vector);
                    a1 = rA.copy().add(vector);
                    a2 = rA.copy().subtract(vector);

                    vertices.add(new Vector2D[]{b1, b2});
                    vertices.add(new Vector2D[]{a1, a2});
                    vertices.add(new Vector2D[]{});
                    break;
                default:
                    break;
            }
            rA = rB.copy();
            it.next();
        }
        Path2D.Double ret = new Path2D.Double();

        Rectangle2D.Double bound = (Rectangle2D.Double) polygon.getBounds2D();
        bound.x -= offset * 2;
        bound.y -= offset * 2;
        bound.width += offset * 4;
        bound.height += offset * 4;

        ret.moveTo(bound.x, bound.y);
        ret.lineTo(bound.x + bound.width, bound.y);
        ret.lineTo(bound.x + bound.width, bound.y + bound.height);
        ret.lineTo(bound.x, bound.y + bound.height);
        ret.closePath();

        boolean closed = true;
        for (Vector2D[] vertex : vertices) {
            if (closed) {
                ret.moveTo(vertex[0].x, vertex[0].y);
                closed = false;
            } else if (vertex.length == 0) {
                closed = true;
                ret.closePath();
            } else
                ret.lineTo(vertex[0].x, vertex[0].y);
        }
        ret.closePath();

        for (Vector2D[] vertex : vertices) {
            if (closed) {
                ret.moveTo(vertex[1].x, vertex[1].y);
                closed = false;
            } else if (vertex.length == 0) {
                ret.closePath();
                closed = true;
            } else
                ret.lineTo(vertex[1].x, vertex[1].y);
        }
        ret.closePath();

        return ret;
    }

    public static boolean isContaining(Path2D.Double polygon, Path2D.Double cut) {
        Rectangle2D.Double p = (Rectangle2D.Double) polygon.getBounds2D();
        Rectangle2D.Double c = (Rectangle2D.Double) cut.getBounds2D();
        for (Point2D[] vertices : PolygonTransformer.extractVertices(cut)) {
            for (Point2D vertex : vertices) {
                if (!polygon.contains(vertex.getX(), vertex.getY()))
                    return false;
                else if (c.x == p.x || c.y == p.y || c.x + c.width == p.x + p.width || c.y + c.height == p.y + p.height)
                    return false;
            }
        }
        return true;
    }
}
