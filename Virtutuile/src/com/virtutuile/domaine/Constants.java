package com.virtutuile.domaine;

import java.awt.*;
import java.util.HashMap;

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

    public static final HashMap<Units, String> UnitNames = new HashMap<Units, String>() {{
        put(Units.Millimeter, "Millimeter");
        put(Units.Centimeter, "Centimeter");
        put(Units.Meter, "Meter");
        put(Units.Inches, "Inches");
        put(Units.Foot, "Foot");
        put(Units.Yard, "Yards");
    }};

    public static final HashMap<Units, String> UnitLabels = new HashMap<Units, String>() {{
        put(Constants.Units.Millimeter, "mm");
        put(Constants.Units.Centimeter, "cm");
        put(Constants.Units.Meter, "m");
        put(Constants.Units.Inches, "in");
        put(Constants.Units.Foot, "ft");
        put(Constants.Units.Yard, "yd");
    }};

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
