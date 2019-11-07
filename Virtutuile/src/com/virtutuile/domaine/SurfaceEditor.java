package com.virtutuile.domaine;

import com.virtutuile.domaine.entities.Meta;
import com.virtutuile.domaine.entities.surfaces.RectangularSurface;
import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.domaine.entities.surfaces.SurfaceBuilder;
import com.virtutuile.shared.Vector2D;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class SurfaceEditor {

    private Meta meta;
    private SurfaceBuilder builder;

    /*private HashMap<Meta.EditionAction, Consumer<Point2D>> _actions = new HashMap<>() {{
        put(Meta.EditionAction.Idle, SurfaceEditor.this::selectShape);
    }};*/

    public SurfaceEditor(Meta meta) {
        this.meta = meta;
        builder = null;
    }

    private void selectShape(Point2D point2D) {
        Surface surface = getSurfaceAt(point2D);

        if (meta.getSelectedSurface() != null) {
            meta.getSelectedSurface().setSelected(false);
        }
        meta.setSelectedSurface(surface);
        if (surface != null) {
            meta.setSelectedSurface(meta.getSurfaces().get(surface.getId()));
            meta.getSelectedSurface().setSelected(true);
            meta.getSurfaces().remove(meta.getSelectedSurface().getId());
            meta.getSurfaces().put(meta.getSelectedSurface().getId(), meta.getSelectedSurface());
        }
    }

    public Surface getSurfaceAt(Point2D point2D) {
        AtomicReference<Surface> surface = new AtomicReference<>();
        meta.getSurfaces().forEach((key, value) -> {
            if (value != null
                    && value.getPolygon() != null
                    && value.getPolygon().contains(Constants.point2DToPoint(point2D))) {
                surface.set(value);
            }
        });
        return surface.get();
    }

    public Surface getShapeById(UUID id) {
        if (meta.getSurfaces().containsKey(id)) {
            return meta.getSurfaces().get(id);
        }
        return null;
    }

    public void removeShape(UUID id) {
        if (meta.getSurfaces().containsKey(id)) {
            meta.getSurfaces().remove(id);
        }
    }

    public UUID getShapeIdAt(Point2D point2D) {
        AtomicReference<UUID> shapeId = new AtomicReference<>();
        meta.getSurfaces().forEach((key, value) -> {
            if (value != null
                    && value.getPolygon() != null
                    && value.getPolygon().contains(Constants.point2DToPoint(point2D))) {
                shapeId.set(key);
            }
        });
        return shapeId.get();
    }


    public Surface getShapeNear(Point2D point2D, double limitDistance) {
        AtomicReference<Surface> shape = new AtomicReference<>();
        final double[] nearest = {Double.NaN};

        meta.getSurfaces().forEach((key, value) -> {
            double dist = value.circleIntersect(new Circle(point2D.getX(), point2D.getY(), limitDistance));

            if (!Double.isNaN(dist) && dist < limitDistance) {
                if (Double.isNaN(nearest[0])) {
                    nearest[0] = dist;
                    shape.set(value);
                } else if (nearest[0] > dist) {
                    shape.set(value);
                }
            }
        });
        return shape.get();
    }

    public Surface getShapeNear(Point2D point2D) {
        return getShapeNear(point2D, Constants.pixelsToCentimeters(Constants.Mouse.DEFAULT_PRECISION));
    }

    public Point2D[] getLineFromShape(Surface surface, Point2D point2D, double limitDistance) {
        return null;
    }

    public Point2D[] getLineFromShape(Surface surface, Point2D point2D) {
        return getLineFromShape(surface, point2D, Constants.pixelsToCentimeters(Constants.Mouse.DEFAULT_PRECISION));
    }

    private void hoverShapeHandling(Point2D point2D, Surface surface, boolean outofShape) {

        if (builder != null && (meta.getDoing() == Meta.EditionAction.CreatingRectangularSurface ||
                meta.getDoing() == Meta.EditionAction.CreatingRectangularSurface)) {
            builder.movePoint(point2D);
        } else {

            // TODO
            // Logic pour afficher les data de la surface
            // dans la bottom toolBar

            surface.setMouseHover(true);
            meta.setHoveredSurface(surface);
            if (outofShape) {
                Point2D vertice = surface.getVerticeNear(point2D, Constants.pixelsToCentimeters(Constants.Mouse.DEFAULT_PRECISION));
                //rotate
            }
        }
    }

    public void mouseHover(Point point){
        meta.setHover(point);
        Point2D point2D = Constants.pointToPoints2D(point);

        Surface surface = getSurfaceAt(point2D);
        boolean outOfShape = false;

        if (surface == null) {
            outOfShape = true;
            surface = getShapeNear(point2D);
            Point2D[] lineFromPoints = getLineFromShape(surface, point2D);

        }
        if (meta.getHoveredSurface() != null) {
            meta.getHoveredSurface().setMouseHover(false);
        }

        if (surface != null) {
            hoverShapeHandling(point2D, surface, outOfShape);
        } else {
            meta.setHoveredSurface(null);
        }
    }

    public void mouseRelease(Point point) {
        meta.setHover(point);
        meta.setMousePressed(false);
        Point2D point2D = Constants.pointToPoints2D(point);

        if (meta.getDoing() == Meta.EditionAction.CreatingRectangularSurface) {
            builder.placePoint(point2D);
            Surface surface = builder.getSurface();
            meta.getSurfaces().put(surface.getId(), surface);
            builder = null;
        }
        mouseHover(point);
    }

    public void mouseLClick(Point point) {
        meta.setClicked(point);
        Point2D point2D = Constants.pointToPoints2D(point);
        meta.setMousePressed(true);
        if (meta.getDoing() == Meta.EditionAction.CreatingRectangularSurface) {
            builder = RectangularSurface.getBuilder();
            builder.placePoint(point2D);
        }
        selectShape(point2D);
    }

    public void mouseRClick(Point point) {

    }

    public void mouseDrag(Point point) {
        if (meta.getDoing() == Meta.EditionAction.CreatingRectangularSurface) {
            builder.movePoint(point);
        } else {
            if (meta.getSelectedSurface() != null) {
                meta.getSelectedSurface().move(meta.getHover(), point);
                if (meta.getSelectedSurface().getPatternGroup() != null
                        && meta.getSelectedSurface().getPatternGroup().getTiles().size() > 0) {
                    meta.getSelectedSurface().getPatternGroup().getTiles().forEach((tile) -> {
                        tile.move(meta.getHover(), point);
                    });
                }
            } else if (meta.getHoveredSurface() != null) {
                Vector2D root = Vector2D.from(meta.getHoveredSurface().getCenter());
                Vector2D origin = Vector2D.from(meta.getHover());
                Vector2D target = Vector2D.from(point);


                meta.getHoveredSurface().rotateRad(target.angleBetweenRad(root) - origin.angleBetweenRad(root));
            }/* else if (_cursor == CursorEventType.Resize && _hoveredShape != null) {
                actionStatus.cursorShape = UIConstants.Mouse.VCursor.Resize;
                //ancien code, gérer le resize à l'aide des curseurs #cursor pas réimplémenté pour le moment
            }*/
        }
        meta.setHover(point);
    }

    public void deleteSelectedShape() {
        Surface surface = meta.getSelectedSurface();
        if (surface != null) {
            meta.getSurfaces().remove(surface.getId());
            meta.setSelectedSurface(null);
        }
    }

    public Surface getCurrentCreatingSurface() {
        if (builder != null) {
            return builder.getSurface();
        }
        return null;
    }

    public void applyPattern(String patternName) {
        Surface surface = meta.getSelectedSurface();
        if (surface != null) {
            surface.applyPattern(patternName);
        }
    }
}
