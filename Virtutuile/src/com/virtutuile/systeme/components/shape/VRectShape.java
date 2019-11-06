package com.virtutuile.systeme.components.shape;

import com.virtutuile.systeme.components.VShapeBuilder;
import com.virtutuile.systeme.units.VCoordinate;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class VRectShape extends VShape implements Cloneable {
    private VCoordinate _origin;
    private VCoordinate _opposite;

    // region rectShape
    public VRectShape(Rectangle.Double rect, boolean isHole) {
        super(isHole);
        _origin = new VCoordinate(rect.x, rect.y);
        _opposite = new VCoordinate(rect.x + rect.width, rect.y + rect.height);
        regenerate();
    }

    public VRectShape(VRectShape shape) {
        super(shape);
        _opposite = new VCoordinate(shape._opposite);
        _origin = new VCoordinate(shape._origin);
    }

    public VCoordinate getOrigin() {
        return this._origin;
    }

    public void setOrigin(VCoordinate origin) {
        this._origin = origin;
    }

    public VCoordinate getOpposite() {
        return this._opposite;
    }

    public void setOpposite(VCoordinate opposite) {
        this._opposite = opposite;
    }

    private void regenerate() {
        polygon = new Path2D.Double();
        polygon.moveTo(_origin.longitude, _origin.latitude);
        polygon.lineTo(_origin.longitude, _opposite.latitude);
        polygon.lineTo(_opposite.longitude, _opposite.latitude);
        polygon.lineTo(_opposite.longitude, _origin.latitude);
        polygon.closePath();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    // endregion

    public static class Builder extends VShapeBuilder {
        private VRectShape _shape;
        private VCoordinate _firstPoint;

        Builder() {
            _shape = new VRectShape(new Rectangle2D.Double(0, 0, 0, 0), false);
            _shape.fillColor = new Color((int) (Math.random() * Integer.MAX_VALUE));
        }

        @Override
        public void placePoint(VCoordinate coordinate) {
            if (_firstPoint == null) {
                _shape._origin = coordinate;
                _firstPoint = coordinate;
            } else {
                _shape._opposite = coordinate;
            }
            _shape._opposite = coordinate;
            _shape.regenerate();
        }

        public void movePoint(VCoordinate coordinate) {
            _shape._opposite = coordinate;
            _shape.regenerate();
        }

        @Override
        public void makeHole(boolean isHole) {
            _shape.isHole = isHole;
        }

        @Override
        public VShape getShape() {
            return new VRectShape(_shape);
        }
    }
}
