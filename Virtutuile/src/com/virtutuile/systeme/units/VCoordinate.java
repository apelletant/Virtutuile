package com.virtutuile.systeme.units;

public class VCoordinate {
    public double longitude = 0;
    public double latitude = 0;

    public VCoordinate() {
    }

    public VCoordinate(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public VCoordinate sub(VCoordinate point) {
        return new VCoordinate(longitude - point.longitude, latitude - point.latitude);
    }

    public VCoordinate add(VCoordinate point) {
        return new VCoordinate(longitude + point.longitude, latitude + point.latitude);
    }

    public Vector2D toVector2D() {
        return new Vector2D(longitude, latitude);
    }

    @Override
    public String toString() {
        return "VCoordinate{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
