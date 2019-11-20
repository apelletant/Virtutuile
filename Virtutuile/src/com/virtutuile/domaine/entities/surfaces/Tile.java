package com.virtutuile.domaine.entities.surfaces;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Tile extends PrimarySurface {

    private Point2D origin;
    private Point2D opposite;
    private Color color;
    private String name;
    private boolean deletable;

    public Tile(double[] pointsX, double[] pointsY) {
        super(pointsX, pointsY);
    }

    public Tile(Point2D[] points2D) {
        super((Point[]) points2D);

    }

    public Tile(Path2D.Double polygon) {
        super(polygon);
    }

    public Tile(Tile tile) {
        super(tile);
        origin = tile.origin;
    }

    public Tile(Rectangle.Double rect) {
        super();
        origin = new Point2D.Double(rect.x, rect.y);
        opposite = new Point2D.Double(rect.x + rect.width, rect.y + rect.height);
        regenerate();
    }

    public Tile(double width, double height, Color color, String name, boolean deletable) {
        origin = new Point2D.Double(0, 0);
        opposite = new Point2D.Double(width, height);
        setFillColor(color);
        setName(name);
        this.deletable = deletable;
    }

    public Tile() {
        super();
    }

    public Tile copy() {
        return new Tile(this);
    }

    public Point2D getOrigin() {
        return origin;
    }

    public void setOrigin(Point2D origin) {
        this.moveOf(origin);
        this.origin = origin;
    }

    public void setOriginAndAdjustOpposite(Point2D.Double origin) {
        double width =  opposite.getX() - this.origin.getX();
        double height = opposite.getY() - this.origin.getY();
        this.origin = origin;
        this.opposite = new Point2D.Double(origin.getX() + width, origin.getY() + height);
    }

    private void regenerate() {
        polygon = new Path2D.Double();
        polygon.moveTo(origin.getX(), origin.getY());
        polygon.lineTo(opposite.getX(), origin.getY());
        polygon.lineTo(opposite.getX(), opposite.getY());
        polygon.lineTo(origin.getX(), opposite.getY());
        polygon.closePath();
    }

    public void setWidthForRectTile(double width) {
        Point2D[] vertices =  getVertices();
        polygon = new Path2D.Double();

        polygon.moveTo(vertices[0].getX(), vertices[0].getY());
        polygon.lineTo(vertices[0].getX() + width, vertices[0].getY()); // Vertice[3]
        polygon.lineTo(vertices[0].getX() + width, vertices[2].getY()); // Vertice[2]
        polygon.lineTo(vertices[0].getX(), vertices[2].getY()); // Vertice[1]
        polygon.closePath();

    }

    public void setHeightForRectTile(double height) {
        Point2D[] vertices =  getVertices();
        polygon = new Path2D.Double();

        polygon.moveTo(vertices[0].getX(), vertices[0].getY());
        polygon.lineTo(vertices[2].getX(), vertices[0].getY());
        polygon.lineTo(vertices[2].getX(), vertices[0].getY() + height);
        polygon.lineTo(vertices[0].getX(), vertices[0].getY() + height);
        polygon.closePath();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeletable() {
        return deletable;
    }
}
