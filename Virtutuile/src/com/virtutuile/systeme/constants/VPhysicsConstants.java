package com.virtutuile.systeme.constants;

import com.virtutuile.systeme.units.VCoordinate;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public final class VPhysicsConstants {

    public static final int METRIC = 0;
    public static final int IMPERIAL = 1;

    public static final Map<VPhysicsConstants.Units, String> UnitNames = Map.of(
            VPhysicsConstants.Units.Millimeter, "Millimeter",
            VPhysicsConstants.Units.Centimeter, "Centimeter",
            VPhysicsConstants.Units.Meter, "Meter",
            VPhysicsConstants.Units.Inches, "Inches",
            VPhysicsConstants.Units.Foot, "Foot",
            VPhysicsConstants.Units.Yard, "Yards"
    );

    public static final Map<VPhysicsConstants.Units, String> UnitLabels = Map.of(
            VPhysicsConstants.Units.Millimeter, "mm",
            VPhysicsConstants.Units.Centimeter, "cm",
            VPhysicsConstants.Units.Meter, "m",
            VPhysicsConstants.Units.Inches, "in",
            VPhysicsConstants.Units.Foot, "ft",
            VPhysicsConstants.Units.Yard, "yd"
    );

    // Converting table from https://metricunitconversion.globefeed.com/length_conversion_table.asp
    public static final double MILLIMETER_RATIO = 10D;
    public static final double CENTIMETER_RATIO = 1D;
    public static final double METER_RATIO = 0.01D;
    public static final double INCHES_RATIO = 0.39370078740157D;
    public static final double FOOT_RATIO = 0.032808398950131D;
    public static final double YARD_RATIO = 0.010936132983377D;
    public static final String[] UNIT_GROUP_STRING = {"Metric", "Imperial"};

    private VPhysicsConstants() {
        throw new AssertionError();
    }

    public static double Convert(double value, VPhysicsConstants.Units from, VPhysicsConstants.Units to) {
        switch (from) {
            case Millimeter:
                return to == VPhysicsConstants.Units.Millimeter ? value : Convert(value / MILLIMETER_RATIO, VPhysicsConstants.Units.Centimeter, to);
            case Centimeter:
                switch (to) {
                    case Millimeter:
                        return value * MILLIMETER_RATIO;
                    case Centimeter:
                        return value;
                    case Meter:
                        return value * METER_RATIO;
                    case Inches:
                        return value * INCHES_RATIO;
                    case Foot:
                        return value * FOOT_RATIO;
                    case Yard:
                        return value * YARD_RATIO;
                    default:
                        throw new AssertionError("Use of unknown unit");
                }

            case Meter:
                return to == VPhysicsConstants.Units.Meter ? value : Convert(value / METER_RATIO, VPhysicsConstants.Units.Centimeter, to);
            case Inches:
                return to == VPhysicsConstants.Units.Inches ? value : Convert(value / INCHES_RATIO, VPhysicsConstants.Units.Centimeter, to);
            case Foot:
                return to == VPhysicsConstants.Units.Foot ? value : Convert(value / FOOT_RATIO, VPhysicsConstants.Units.Centimeter, to);
            case Yard:
                return to == VPhysicsConstants.Units.Yard ? value : Convert(value / YARD_RATIO, VPhysicsConstants.Units.Centimeter, to);
            default:
                throw new AssertionError("Use of unknown unit");
        }
    }

    //TODO: Développer la méthode pointToCoordinates
    public static VCoordinate pointToCoordinates(Point point) {
        return new VCoordinate() {{
            longitude = point.x;
            latitude = point.y;
        }};
    }

    //TODO: Développer la méthode coordinatesToPoints
    public static Point[] coordinatesToPoints(VCoordinate[] coordinates) {
        Point[] points = new Point[coordinates.length];

        for (int i = 0; i < coordinates.length; ++i) {
            VCoordinate coord = coordinates[i];

            points[i] = coordinateToPoint(coord);
        }
        return points;
    }

    //TODO: Développer la méthode coordinateToPoint
    public static Point coordinateToPoint(VCoordinate coordinates) {
        return new Point() {{
            x = (int) coordinates.longitude;
            y = (int) coordinates.latitude;
        }};
    }

    public static double pixelsToCentimeters(int pixels) {
        return pixels;
    }

    public static int centimetersToPixels(double centimeters) {
        return (int) centimeters;
    }

    public static final class Mouse {

        // Determine the max distance from the mouse to detect and interact with a shape.
        public static final int DEFAULT_PRECISION = 12;

        private Mouse() {
            throw new AssertionError();
        }
    }

    public enum Units {
        Millimeter,
        Centimeter,
        Meter,
        Inches,
        Foot,
        Yard,
    }
}
