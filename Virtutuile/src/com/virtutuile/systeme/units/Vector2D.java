package com.virtutuile.systeme.units;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class Vector2D {
    public double x = 0D;
    public double y = 0D;

    public Vector2D() {
    }

    public Vector2D(Vector2D cpy) {
        this.x = cpy.x;
        this.y = cpy.y;
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

    public Vector2D(VCoordinate a, VCoordinate b) {
        x = b.longitude - a.longitude;
        y = b.latitude - a.latitude;
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
        return x * y;
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
    public Vector2D add(@NotNull VCoordinate add) {
        x += add.longitude;
        y += add.latitude;
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
    public Vector2D subtract(@NotNull VCoordinate subtract) {
        x -= subtract.longitude;
        y -= subtract.latitude;
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

    public double product() {
        return this.x * this.x + this.y * this.y;
    }
    /**
     * Return the product between two vectors
     *
     * @param vector
     * @return
     */
    public double product(Vector2D vector) {
        return this.x * vector.x + this.y * vector.y;
    }

    public double product(VCoordinate coordinate) {
        return this.x * coordinate.longitude + this.y * coordinate.latitude;
    }

    public double product(double x, double y) {
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

    public Vector2D lerp(VCoordinate coordinate, double amount) {
        return lerp(coordinate.longitude, coordinate.latitude, amount);
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

    public VCoordinate toVCoordinate() {
        return new VCoordinate(x, y);
    }

    public Point toPoint() {
        return new Point((int) x, (int) y);
    }

    public static Vector2D from(Point point) {
        return new Vector2D(point.x, point.y);
    }

    public static Vector2D from(VCoordinate point) {
        return new Vector2D(point.longitude, point.latitude);
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
}
