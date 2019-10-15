package com.virtutuile.data;

import com.virtutuile.system.constants.UIConstants;

import java.awt.*;

/**
 * Vrect point list as follow :
 * <p>
 * 0 -------- 1
 * |          |
 * |          |
 * 3 -------- 2
 */
public class VRect extends VShape {
    private Point _first = null;
    private Point _second = null;
    private Rectangle _bounds = null;

    @Override
    public Rectangle getBounds() {
        return _bounds;
    }

    @Override
    public boolean canRotate(Point origin, float angle) {
        return true;
    }

    @Override
    public void rotate(Point origin, float angle) {
    }

    @Override
    public void setPoint(Point point) {
        if (_first == null)
            setFirstPoint(point);
        else
            setSecondPoint(point);
    }

    public void setFirstPoint(Point p) {
        _first = p;
        updateBounds();
    }

    public void setSecondPoint(Point p) {
        _second = p;
        updateBounds();
    }

    @Override
    public void viewPoint(Point point) {
        setSecondPoint(point);
        _second = null;
    }

    @Override
    public void draw(Graphics gfx) {
        if (_bounds == null) return;
        gfx.setColor(UIConstants.NORMAL_SHAPE_BORDER_COLOR);
        gfx.drawRect(_bounds.x, _bounds.y, _bounds.width, _bounds.height);
        gfx.setColor(UIConstants.NORMAL_SHAPE_FILL_COLOR);
        gfx.fillRect(_bounds.x + 1, _bounds.y + 1, _bounds.width - 2, _bounds.height - 2);
    }

    @Override
    public Rectangle updateBounds() {
        if (_first != null && _second != null) {
            int x1 = _first.x;
            int x2 = _second.x;

            if (x1 > x2) {
                x1 ^= x2;
                x2 ^= x1;
                x1 ^= x2;
            }

            int y1 = _first.y;
            int y2 = _second.y;
            if (y1 > y2) {
                y1 ^= y2;
                y2 ^= y1;
                y1 ^= y2;
            }

            _bounds = new Rectangle(x1, y1, x2 - x1, y2 - y1);
        } else {
            _bounds = null;
        }
        return _bounds;
    }

    @Override
    public boolean isDone() {
        return _first != null && _second != null;
    }
}
