package com.virtutuile.systeme.units;

public class VDimensions {
    public double width = 0;
    public double height = 0;

    public VDimensions() {
        this(0,0 );
    }

    public VDimensions(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public VDimensions(VDimensions copy) {
        width = copy.width;
        height = copy.height;
    }
}
