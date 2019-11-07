package com.virtutuile.domaine.entities;

import com.virtutuile.afficheur.Constants;

import java.awt.*;

public class Grout {

    private Color color;
    private double thickness;

    //TODO: déplacer la défiition du thickness #edition du coulis
    public Grout(/*Color color, float thickness, float height*/) {
        /*_color = color;
        _thickness = thickness;
        _height = height;*/
        color = Constants.INPUT_COLOR_INVALID;
        this.thickness = 3;
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
