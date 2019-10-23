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

    @Override
    public String toString() {
        return "VCoordinate{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
