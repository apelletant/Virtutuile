package com.virtutuile.domaine.entities;

import com.virtutuile.afficheur.Constants;

import java.awt.*;
import java.io.Serializable;

public class Grout implements Serializable {

    private Color color;
    private double thickness;

    public Grout() {
        color = Constants.INPUT_COLOR_INVALID;
        this.thickness = 1;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getThickness() {
        return this.thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public Grout(Grout grout) {
        this.color = new Color(grout.getColor().getRGB());
        this.thickness = grout.thickness;
    }

    public Grout copy() {
        return new Grout(this);
    }
}
