package com.virtutuile.domaine.entities.tools;

import com.virtutuile.shared.CustomPoint;
import com.virtutuile.shared.Vecteur;

public class Intersection {

    private static boolean onSegment(CustomPoint p, CustomPoint q, CustomPoint r)
    {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;
        return false;
    }

    private static double orientation(CustomPoint p, CustomPoint q, CustomPoint r)
    {
        double val = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;

        return (val > 0)? 1: 2;
    }

    private static boolean DoesIntersect(Vecteur vec1, Vecteur vec2) {
        double o1 = orientation(vec1.a, vec1.b, vec2.a);
        double o2 = orientation(vec1.a, vec1.b, vec2.b);
        double o3 = orientation(vec2.a, vec2.b, vec1.a);
        double o4 = orientation(vec2.a, vec2.b, vec1.b);

        if (o1 != o2 && o3 != o4)
            return true;

        if (o1 == 0 && onSegment(vec1.a, vec2.a, vec1.b)) return true;

        if (o2 == 0 && onSegment(vec1.a, vec2.b, vec1.b)) return true;

        if (o3 == 0 && onSegment(vec2.a, vec1.a, vec2.b)) return true;

        if (o4 == 0 && onSegment(vec2.a, vec1.b, vec2.b)) return true;

        return false;
    }

    public static CustomPoint intersectionPoint(Vecteur vec1, Vecteur vec2) {
        if (!DoesIntersect(vec1, vec2)) {
            return null;
        }
        double a = vec1.Vecto(new Vecteur(vec1.a, vec2.a)) / vec1.Norme();
        double b = vec1.Vecto(new Vecteur(vec1.a, vec2.b)) / vec1.Norme();

        double newB = vec2.Norme() + (vec2.Norme() * b) / (a - b);
        double resX, resY;
        double Rapport = newB / vec2.Norme();

        resX = vec2.a.x + vec2.x * Rapport;
        resY = vec2.a.y + vec2.y * Rapport;
        return new CustomPoint(resX, resY);
    }
}
