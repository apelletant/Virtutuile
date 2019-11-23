package com.virtutuile.shared;

public class Line {
    public double[] a = new double[]{0, 0};
    public double[] b = new double[]{0, 0};

    public Line() {
    }

    public Line(double[] a, double[] b) {
        this.a = a;
        this.b = b;
    }

    public Vector2D vectorize() {
        return new Vector2D(b[0] - a[0], b[0] - a[0]);
    }
}
