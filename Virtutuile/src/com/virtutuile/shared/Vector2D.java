package com.virtutuile.shared;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Objects;

public class Vector2D {
    public double x = 0D;
    public double y = 0D;

    public Vector2D() {
    }

    public Vector2D(Vector2D cpy) {
        x = cpy.x;
        y = cpy.y;
    }

    public Vector2D(Point2D point) {
        x = point.getX();
        y = point.getY();
    }

    public Vector2D(Point a, Point b) {
        x = b.x - a.x;
        y = b.y - a.y;
    }

    public Vector2D(Vector2D a, Vector2D b) {
        x = b.x - a.x;
        y = b.y - a.y;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Point2D a, Point2D b) {
        x = b.getX() - a.getX();
        y = b.getY() - a.getY();
    }

    /**
     * @return Return the square length (magnitude) of the vector
     */
    public double magnitudeSquare() {
        return x * x + y * y;
    }

    /**
     * @return Return the length (magnitude) of the vector
     */
    public double magnitude() { 
        return Math.sqrt(magnitudeSquare());
    }

    /**
     * @param add The value of add to the vector
     * @return The added vector
     */
    public Vector2D add(@NotNull Vector2D add) {
        x += add.x;
        y += add.y;
        return this;
    }

    /**
     * @param add The value of add to the vector
     * @return The added vector
     */
    public Vector2D add(@NotNull Point add) {
        x += add.x;
        y += add.y;
        return this;
    }

    /**
     * @param add The value of add to the vector
     * @return The added vector
     */
    public Vector2D add(@NotNull Point2D add) {
        x += add.getX();
        y += add.getY();
        return this;
    }

    /**
     * @param add The value of add to the vector
     * @return The added vector
     */
    public Vector2D add(double add) {
        x += add;
        y += add;
        return this;
    }

    /**
     * @param subtract The value of subtract to the vector
     * @return The subtracted vector
     */
    public Vector2D subtract(@NotNull Vector2D subtract) {
        x -= subtract.x;
        y -= subtract.y;
        return this;
    }

    /**
     * @param subtract The value of subtract to the vector
     * @return The subtracted vector
     */
    public Vector2D subtract(@NotNull Point subtract) {
        x -= subtract.x;
        y -= subtract.y;
        return this;
    }

    /**
     * @param subtract The value of subtract to the vector
     * @return The subtracted vector
     */
    public Vector2D subtract(@NotNull Point2D subtract) {
        x -= subtract.getX();
        y -= subtract.getY();
        return this;
    }

    /**
     * @param subtract The value of subtract to the vector
     * @return The subtracted vector
     */
    public Vector2D subtract(double subtract) {
        x -= subtract;
        y -= subtract;
        return this;
    }

    /**
     * @param div The value by how much divide the length (magnitude) of the vector
     * @return The divided vector
     */
    public Vector2D divide(double div) {
        x /= div;
        y /= div;
        return this;
    }

    /**
     * @param mul The value by how much multiply the length (magnitude) of the vector
     * @return The multiplied vector
     */
    public Vector2D multiply(double mul) {
        x *= mul;
        y *= mul;
        return this;
    }

    /**
     * @return Return the normalized vector. (It's length (magnitude) became equal to 1)
     */
    public Vector2D normalize() {
        return this.divide(this.magnitude());
    }

    /**
     * Set the magnitude of the vector to the given magnitude.
     *
     * @param magnitude The given magnitude
     * @return Return the vector
     */
    public Vector2D setMagnitude(int magnitude) {
        this.normalize().multiply(magnitude);
        return this;
    }

    // region product (type Vector2D, Point, VCoordinate, int, float, double)

    public double getProduct() {
        return this.x * this.x + this.y * this.y;
    }
    /**
     * Return the product between two vectors
     *
     * @param vector
     * @return
     */
    public double setProduct(Vector2D vector) {
        return this.x * vector.x + this.y * vector.y;
    }

    public double setProduct(Point2D coordinate) {
        return this.x * coordinate.getX() + this.y * coordinate.getY();
    }

    public double setProduct(double x, double y) {
        return this.x * x + this.y * y;
    }

    // endregion

    /**
     * Return the distance between two points as vector
     *
     * @param second
     * @return
     */
    public double distance(Vector2D second) {
        Vector2D dist = new Vector2D(second).subtract(this);
        return dist.magnitude();
    }

    /**
     * Limit the length (magnitude) of the vector to the value
     *
     * @param limit
     * @return
     */
    public Vector2D limit(double limit) {
        double magSquare = this.magnitudeSquare();
        if (magSquare > limit * limit) {
            this.divide(Math.sqrt(magSquare)).multiply(limit);
        }
        return this;
    }

    /**
     * Return the slope of the vector in radians
     *
     * @return
     */
    public double getAngleRad() {
        return Math.atan2(y, x);
    }

    /**
     * Return the slope of the vector in degrees
     *
     * @return
     */
    public double getAngleDeg() {
        return (Math.atan2(y, x) * 180.0D) / Math.PI;
    }

    /**
     * Rotate the vector by the given angle in radians
     *
     * @return
     */
    public Vector2D rotateRad(double angle) {
        double newSlope = getAngleRad() + angle;
        double magnitude = magnitude();
        x = Math.cos(newSlope) * magnitude;
        y = Math.sin(newSlope) * magnitude;
        return this;
    }

    /**
     * Rotate the vector by the given angle in degrees
     *
     * @return
     */
    public Vector2D rotateDeg(double angle) {
        return rotateRad((angle * Math.PI) / 180.0D);
    }

    /**
     * Return the angle between two vectors in radians
     *
     * @param vector
     * @return
     */
    public double angleBetweenRad(Vector2D vector) {
        return angleBetweenRad(vector.x, vector.y);
    }

    /**
     * Return the angle between two vectors in radians
     *
     * @param x
     * @param y
     * @return
     */
    public double angleBetweenRad(double x, double y) {
        return Math.atan2(y - this.y, x - this.x);
    }

    /**
     * Return the angle between two vectors in radians
     *
     * @param vector
     * @return
     */
    public double angleBetweenDeg(Vector2D vector) {
        return (angleBetweenRad(vector.x, vector.y) * 180.0D) / Math.PI;
    }

    /**
     * Return the angle between two vectors in radians
     *
     * @param x
     * @param y
     * @return
     */
    public double angleBetweenDeg(double x, double y) {
        return (angleBetweenRad(x, y) * 180.0D) / Math.PI;
    }

    public Vector2D lerp(Point2D coordinate, double amount) {
        return lerp(coordinate.getX(), coordinate.getY(), amount);
    }

    public Vector2D lerp(double x, double y, double amount) {
        this.x += (x - this.x) * amount;
        this.y += (y - this.y) * amount;
        return this;
    }

    public Vector2D tarnslateRad(double radians, double translate) {
        x += translate * Math.cos(radians);
        y += translate * Math.sin(radians);
        return this;
    }

    public Vector2D tarnslateDeg(double degrees, double translate) {
        return tarnslateRad(degrees / 180.D * Math.PI, translate);
    }


    public Point toPoint() {
        return new Point((int) x, (int) y);
    }

    public static Vector2D from(Point2D point) {
        return new Vector2D(point.getX(), point.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return Double.compare(vector2D.x, x) == 0 &&
                Double.compare(vector2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Vector2D copy() {
        return new Vector2D(this);
    }

    public Point2D.Double toPoint2D() {
        return new Point2D.Double(x, y);
    }
}

