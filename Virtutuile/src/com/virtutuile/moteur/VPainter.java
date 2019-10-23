package com.virtutuile.moteur;

import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.constants.UIConstants;

import java.awt.*;
import java.util.List;

public class VPainter {

    private Graphics _graphics;
    private Graphics2D _graphics2D;

    public VPainter(Graphics graphics) {
        this._graphics = graphics;
        this._graphics2D = (Graphics2D) graphics;
    }

    public VPainter getFillColor() {
        return null;
    }

    public VPainter setFillColor(Color color) {
        return null;
    }

    public VPainter getBorderColor() {
        return null;
    }

    public VPainter setBorderColor(Color color) {
        return null;
    }

    public VPainter paintBorder(List<Point> points) {
        return null;
    }

    public VPainter paintShape(List<Point> points) {
        return null;
    }

    public VPainter paintBorderedShape(List<Point> points) {
        return null;
    }

    public void paint(VDrawableShape drawableShape) {
        int[] xPoints = drawableShape.polygon().xpoints;
        int[] yPoints = drawableShape.polygon().ypoints;

        _graphics2D.setColor(drawableShape.fillColor());
        fillPolygon(drawableShape.polygon());
        _graphics2D.setColor(drawableShape.borderColor());
        paintPolygon(drawableShape.polygon(), drawableShape.getBorderThickness());
        if (drawableShape.isMouseHovered() || drawableShape.isActive()) {
            drawGizmos(xPoints, yPoints);
        }
    }

    public void fillPolygon(Polygon polygon) {
        _graphics2D.fillPolygon(polygon);
    }

    public void paintPolygon(Polygon polygon, int thickness) {
        _graphics2D.setStroke(new BasicStroke(thickness));
        _graphics2D.drawPolygon(polygon);
    }

    public void drawGizmos(int[] xPoints, int[] yPoints) {
        drawHandles(xPoints, yPoints);
    }

    private void drawHandles(int[] xPoints, int[] yPoints) {
        final Dimension size = UIConstants.Gizmos.Handles.SIZE;

        for (int i = 0; i < xPoints.length; ++i) {
            Point anchor = new Point();

            anchor.x = xPoints[i] - Math.floorDiv(size.width, 2);
            anchor.y = yPoints[i] - Math.floorDiv(size.height, 2);
            _graphics2D.setColor(UIConstants.Gizmos.Handles.BACKGROUND_COLOR);
            _graphics2D.fillRect(anchor.x, anchor.y, size.width, size.height);
            _graphics2D.setStroke(new BasicStroke(UIConstants.Gizmos.Handles.BORDER_STROKE));
            _graphics2D.setColor(UIConstants.Gizmos.Handles.BORDER_COLOR);
            _graphics2D.drawRect(anchor.x, anchor.y, size.width, size.height);
        }
    }
}
