package com.virtutuile.domaine;

import com.virtutuile.domaine.managers.VPainterManager;
import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.units.Vector2D;

import java.awt.*;
import java.util.List;

public class VPainter {

    private Graphics _graphics;
    private Graphics2D _graphics2D;
    VPainterManager _manager = VPainterManager.getInstance();

    public VPainter(Graphics graphics) {
        this._graphics = graphics;
        this._graphics2D = (Graphics2D) graphics;
    }

    //TODO create magnetic grid here

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
        if (drawableShape == null)
            return;
        _graphics2D.setColor(drawableShape.fillColor());
        fillPolygon(drawableShape.polygon());
        _graphics2D.setColor(drawableShape.borderColor());
        paintPolygon(drawableShape.polygon(), drawableShape.getBorderThickness());
        if (drawableShape.isMouseHovered() || drawableShape.isActive()) {
            drawGizmos(drawableShape);
        }
    }

    public void fillPolygon(Polygon polygon) {
        _graphics2D.fillPolygon(polygon);
    }

    public void paintPolygon(Polygon polygon, int thickness) {
        _graphics2D.setStroke(new BasicStroke(thickness));
        _graphics2D.drawPolygon(polygon);
    }

    public void drawGizmos(VDrawableShape shape) {
        if ((this._manager.getGizmos() & VPainterManager.GIZ_BOUNDS) != 0) {
            drawBoundingBox(shape.polygon().getBounds());
        }
        drawHandles(shape.polygon().xpoints, shape.polygon().ypoints);
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

    /**
     * Draw the bounding boxes like following :
     * A--------------------B
     * |                    |
     * |                    |
     * D--------------------C
     * <p>
     * Began
     * |                    |
     * -A--------------------B-
     * |                    |
     * |                    |
     * -D--------------------C-
     * |                    |
     *
     * @param box
     */
    private void drawBoundingBox(Rectangle box) {
        float[] dashes = {10f};

        _graphics2D.setStroke(new BasicStroke(1.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f, dashes, 0.0f));

        _graphics2D.setColor(UIConstants.Gizmos.BoundingBoxes.BOX_COLOR);
        _graphics2D.drawRect(box.x, box.y, box.width, box.height);
        _graphics2D.setStroke(new BasicStroke(UIConstants.Gizmos.BoundingBoxes.STROKE));
        Point corner = new Point(box.x, box.y);
        Point expansion = Vector2D.from(corner).tarnslateDeg(180, UIConstants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        _graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        expansion = Vector2D.from(corner).tarnslateDeg(270, UIConstants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        _graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        corner.x += box.width;
        expansion = Vector2D.from(corner).tarnslateDeg(270, UIConstants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        _graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        expansion = Vector2D.from(corner).tarnslateDeg(0, UIConstants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        _graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        corner.y += box.height;
        expansion = Vector2D.from(corner).tarnslateDeg(0, UIConstants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        _graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        expansion = Vector2D.from(corner).tarnslateDeg(90, UIConstants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        _graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        corner.x -= box.width;
        expansion = Vector2D.from(corner).tarnslateDeg(180, UIConstants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        _graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        expansion = Vector2D.from(corner).tarnslateDeg(90, UIConstants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        _graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
    }
}
