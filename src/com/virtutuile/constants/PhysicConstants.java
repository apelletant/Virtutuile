package com.virtutuile.constants;

import java.util.Map;

public final class PhysicConstants {

    public static final int METRIC = 0;
    public static final int IMPERIAL = 1;

    public static final Map<Units, String> UnitNames = Map.of(
            Units.Millimeter, "Millimeter",
            Units.Centimeter, "Centimeter",
            Units.Meter, "Meter",
            Units.Inches, "Inches",
            Units.Foot, "Foot",
            Units.Yard, "Yards"
    );

    public static final Map<Units, String> UnitLabels = Map.of(
            Units.Millimeter, "mm",
            Units.Centimeter, "cm",
            Units.Meter, "m",
            Units.Inches, "in",
            Units.Foot, "ft",
            Units.Yard, "yd"
    );

    // Converting table from https://metricunitconversion.globefeed.com/length_conversion_table.asp
    public static final double MILLIMETER_RATIO = 10D;
    public static final double CENTIMETER_RATIO = 1D;
    public static final double METER_RATIO = 0.01D;
    public static final double INCHES_RATIO = 0.39370078740157D;
    public static final double FOOT_RATIO = 0.032808398950131D;
    public static final double YARD_RATIO = 0.010936132983377D;

    public enum Units {
        Millimeter,
        Centimeter,
        Meter,
        Inches,
        Foot,
        Yard,
    }

    public static final String[] UNIT_GROUP_STRING = {"Metric", "Imperial"};

    public static final double Convert(double value, Units from, Units to) {
        switch (from) {
            case Millimeter:
                return to == Units.Millimeter ? value : Convert(value / MILLIMETER_RATIO, Units.Centimeter, to);
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
                return to == Units.Meter ? value : Convert(value / METER_RATIO, Units.Centimeter, to);
            case Inches:
                return to == Units.Inches ? value : Convert(value / INCHES_RATIO, Units.Centimeter, to);
            case Foot:
                return to == Units.Foot ? value : Convert(value / FOOT_RATIO, Units.Centimeter, to);
            case Yard:
                return to == Units.Yard ? value : Convert(value / YARD_RATIO, Units.Centimeter, to);
            default:
                throw new AssertionError("Use of unknown unit");
        }
   }

    private PhysicConstants() {
        throw new AssertionError();
    }
}
