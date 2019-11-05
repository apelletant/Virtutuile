package com.virtutuile.systeme.components;

import com.virtutuile.systeme.singletons.VApplicationStatus;
import com.virtutuile.systeme.units.VCoordinate;
import com.virtutuile.systeme.units.VDimensions;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

public class VTile {

    private VDimensions _dimensions = new VDimensions();
    private VCoordinate _origin = new VCoordinate();
    private Color _color;
    private VCoordinate _position = new VCoordinate();;
    private float _rotation;

    public VTile() {
    }

    public VTile(VTile copy) {
        this._dimensions = new VDimensions(copy._dimensions);
        this._color = copy._color;
        this._position = new VCoordinate(copy._position);
        this._rotation = copy._rotation;
        this._origin = new VCoordinate(copy._origin);
    }

    public VTile copy() {
        return new VTile(this);
    }

    public VDimensions getDimensions() {
        return this._dimensions;
    }

    public void setDimensions(VDimensions dimensions) {
        this._dimensions = dimensions;
    }

    public Color getColor() {
        return this._color;
    }

    public void setColor(Color color) {
        this._color = color;
    }

    public VCoordinate getPosition() {
        return this._position;
    }

    public void setPosition(VCoordinate position) {
        this._position = position;
    }

    public float getRotation() {
        return this._rotation;
    }

    public void setRotation(float rotation) {
        this._rotation = rotation;
    }

    public VCoordinate getOrigin() {
        return this._origin;
    }

    public void setOrigin(VCoordinate origin) {
        this._origin = origin;
    }

    public void setWidth(double width) {
        this._dimensions.width = width;
    }

    public void setHeight(double height) {
        this._dimensions.height = height;
    }

}