package com.virtutuile.shared;

import java.awt.geom.Point2D;

public class Vecteur {
    public double x, y;
    public CustomPoint a;
    public CustomPoint b;

    public Vecteur(CustomPoint newA, CustomPoint newB) {
        a = newA;
        b = newB;
        x = b.x - a.x;
        y = b.y - a.y;
    }

    public Vecteur(Point2D vertex) {
        x = vertex.getX();
        y = vertex.getY();
        a = new CustomPoint(0, 0);
        b = new CustomPoint(x, y);
    }

    public double Vecto(Vecteur other) {
        return (x * other.y - y * other.x);
    }
    public double Norme() {
        return Math.sqrt(x * x + y * y);
    }
}
