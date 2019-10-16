package com.virtutuile.moteur;

import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.interfaces.IVGraphics;

import java.awt.*;
import java.util.List;

public class VPainter {

    private IVGraphics _graphics;

    public VPainter(IVGraphics graphics) {
        this._graphics = graphics;
    }

    public VPainter getFillColor() {
        return null;
    }

    public VPainter setFillColor(Color color) {
        return null;
    }

    public VPainter getBorderColor() {
        return null;
    }

    public VPainter setBorderColor(Color color) {
        return null;
    }

    public VPainter paintBorder(List<Point> points) {
        return null;
    }

    public VPainter paintShape(List<Point> points) {
        return null;
    }

    public VPainter paintBorderedShape(List<Point> points) {
        return null;
    }

    public void paint(VDrawableShape drawableShape) {

    }
}
