package com.virtutuile.domaine;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Map;

public class Constants {

    public static final class Gizmos {

        public static final class BoundingBoxes {

            public static final int STROKE = 1;
            public static final Color BOX_COLOR = new Color(122, 161, 235);
            public static final int EXPANSION_LENGTH = 12;
        }

        public static final class Handles {
            public static final Dimension SIZE = new Dimension(8, 8);
            public static final Color BACKGROUND_COLOR = Color.WHITE;
            public static final Color BORDER_COLOR = Color.BLACK;
            public static final int BORDER_STROKE = 2;

            private Handles() {
                throw new AssertionError();
            }
        }

        private Gizmos() {
            throw new AssertionError();
        }
    }


    public static final int METRIC = 0;
    public static final int IMPERIAL = 1;

    public static final Map<Constants.Units, String> UnitNames = Map.of(
            Constants.Units.Millimeter, "Millimeter",
            Constants.Units.Centimeter, "Centimeter",
            Constants.Units.Meter, "Meter",
            Constants.Units.Inches, "Inches",
            Constants.Units.Foot, "Foot",
            Constants.Units.Yard, "Yards"
    );

    public static final Map<Units, String> UnitLabels = Map.of(
            Constants.Units.Millimeter, "mm",
            Constants.Units.Centimeter, "cm",
            Constants.Units.Meter, "m",
            Constants.Units.Inches, "in",
            Constants.Units.Foot, "ft",
            Constants.Units.Yard, "yd"
    );

    // Converting table from https://metricunitconversion.globefeed.com/length_conversion_table.asp
    public static final double MILLIMETER_RATIO = 10D;
    public static final double CENTIMETER_RATIO = 1D;
    public static final double METER_RATIO = 0.01D;
    public static final double INCHES_RATIO = 0.39370078740157D;
    public static final double FOOT_RATIO = 0.032808398950131D;
    public static final double YARD_RATIO = 0.010936132983377D;
    public static final String[] UNIT_GROUP_STRING = {"Metric", "Imperial"};

    private Constants() {
        throw new AssertionError();
    }

    public static double Convert(double value, Constants.Units from, Constants.Units to) {
        switch (from) {
            case Millimeter:
                return to == Constants.Units.Millimeter ? value : Convert(value / MILLIMETER_RATIO, Constants.Units.Centimeter, to);
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
                return to == Constants.Units.Meter ? value : Convert(value / METER_RATIO, Constants.Units.Centimeter, to);
            case Inches:
                return to == Constants.Units.Inches ? value : Convert(value / INCHES_RATIO, Constants.Units.Centimeter, to);
            case Foot:
                return to == Constants.Units.Foot ? value : Convert(value / FOOT_RATIO, Constants.Units.Centimeter, to);
            case Yard:
                return to == Constants.Units.Yard ? value : Convert(value / YARD_RATIO, Constants.Units.Centimeter, to);
            default:
                throw new AssertionError("Use of unknown unit");
        }
    }

    //TODO: Développer la méthode
    public static Point2D pointToPoints2D(Point point) {
        return new Point2D.Double(point.x, point.y);
    }

    //TODO: Développer la méthode
    public static Point[] points2DToPoints(Point2D[] point2D) {
        Point[] points = new Point[point2D.length];

        for (int i = 0; i < point2D.length; ++i) {
            Point point = new Point((int) point2D[i].getX(), (int) point2D[i].getY());
            points[i] = point2DToPoint(point);
        }
        return points;
    }

    //TODO: Développer la méthode
    public static Point point2DToPoint(Point2D coordinates) {
        return new Point() {{
            x = (int) coordinates.getX();
            y = (int) coordinates.getY();
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

    public enum SystemUnit {
        Metric,
        Imperial,
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
