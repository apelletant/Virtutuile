package com.virtutuile.domaine;

import com.virtutuile.domaine.entities.Meta;
import com.virtutuile.domaine.entities.surfaces.*;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

public class SurfaceEditor {

    private Meta meta;
    private SurfaceBuilder builder;

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
                    && value.getPolygon().contains(point2D)) {
                surface.set(value);
            }
        });
        return surface.get();
    }

    public Tile getTileAt(Point2D mousePos) {
        AtomicReference<Tile> tile = new AtomicReference<>();
        Vector<Tile> tiles = null;

        if (meta.getHoveredSurface() != null &&
                meta.getHoveredSurface().getPatternGroup() != null &&
                meta.getHoveredSurface().getPatternGroup().getTiles() != null) {
            tiles = meta.getHoveredSurface().getPatternGroup().getTiles();
        }

        if (tiles != null) {
            tiles.forEach((hoveredTile) -> {
                Rectangle2D.Double bounds = hoveredTile.getBounds();
                if ((mousePos.getX() >= bounds.x && mousePos.getX() <= bounds.x + bounds.width) &&
                        (mousePos.getY() >= bounds.y && mousePos.getY() <= bounds.y + bounds.height)) {
                    tile.set(hoveredTile);
                }

            });
        }
        if (tile != null) {
            return tile.get();
        }
        return null;
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
                    && value.getPolygon().contains(point2D)) {
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
        return getShapeNear(point2D, meta.pixelsToCentimeters(Constants.Mouse.DEFAULT_PRECISION));
    }

    private void hoverShapeHandling(Point2D point2D, Surface surface, boolean outofShape) {

        if (builder != null && (meta.getDoing() == Meta.EditionAction.CreatingRectangularSurface ||
                meta.getDoing() == Meta.EditionAction.CreatingRectangularSurface)) {
            builder.movePoint(point2D);
        } else {
            surface.setMouseHover(true);
            meta.setHoveredSurface(surface);
            if (outofShape) {
                Point2D vertice = surface.getVerticeNear(point2D, meta.pixelsToCentimeters(Constants.Mouse.DEFAULT_PRECISION));
            }
        }
    }

    public void mouseHover(Point2D point) {

        Surface surface = getSurfaceAt(point);
        boolean outOfShape = false;

        if (builder != null && meta.getDoing() == Meta.EditionAction.CreatingFreeSurface) {
            builder.movePoint(point);
        }

        if (meta.getSelectedSurface() == null) {
            meta.setSelectedSurfaceCanBeResized(false);
        }

        /*if (surface == null) {
            outOfShape = true;
            surface = getShapeNear(point);
        }*/
        if (meta.getHoveredSurface() != null) {
            meta.getHoveredSurface().setMouseHover(false);
            meta.setHoveredTile(getTileAt(point));
        }

        /*if (meta.getSelectedSurface() != null) {
            System.out.println(meta.getSelectedSurface().getBounds());
            System.out.println(point2D.getX() + " " + point2D.getY());
        }*/

        if (meta.getSelectedSurface() != null
                && meta.getSelectedSurface().getBoundsAsSurface().containsOrIntersect(point)
                && !meta.getSelectedSurface().getPolygon().contains(point)) {
            meta.setSelectedSurfaceCanBeResized(true);
        }


        if (surface != null) {
            hoverShapeHandling(point, surface, outOfShape);
        } else {
            meta.setHoveredSurface(null);
        }
    }

    public void mouseRelease(Point2D point) {
        meta.setMousePressed(false);

        if (meta.getDoing() == Meta.EditionAction.CreatingRectangularSurface) {
            builder.placePoint(point);
            Surface surface = builder.getSurface();
            meta.getSurfaces().put(surface.getId(), surface);
            builder = null;
            /*System.out.println("Surface Origin : {" + surface.getVertices()[0].getX() + ", " + surface.getVertices()[0].getY());
            System.out.println("Surface Dimensions : Width: " + surface.getPolygon().getBounds2D().getWidth() + ", Height: " + surface.getPolygon().getBounds2D().getHeight());*/
        }
        mouseHover(point);
    }

    public void mouseLClick(Point2D point) {
        meta.setMousePressed(true);
        if (meta.getDoing() == Meta.EditionAction.CreatingRectangularSurface) {
            if (builder == null)
                builder = RectangularSurface.getBuilder();
            builder.placePoint(point);
        } else if (meta.getDoing() == Meta.EditionAction.CreatingFreeSurface) {
            if (builder == null)
                builder = FreeSurface.getBuilder();
            builder.placePoint(point);
        } else if (meta.getDoing() == Meta.EditionAction.Align) {
            Surface surfaceReferenceAlign = getSurfaceAt(point);
            alignSurfaces(surfaceReferenceAlign, meta.getSelectedSurface());
        } else {
            /*if (meta.getSelectedSurface() != null) {
                if (meta.getSelectedSurface().getBoundsAsSurface().containsOrIntersect(point)) {
                    meta.setSelectedSurfaceCanBeResized(true);
                    System.out.println("eho coucou je suis dans tes bounds");
                }
            } else {
                System.out.println(point);
                if (meta.getSelectedSurface() != null) {
                    System.out.println(meta.getSelectedSurface().getBounds());
                }
            }*/
            selectShape(point);
        }
    }

    private void alignSurfaces(Surface surfaceReferenceAlign, Surface selectedSurface) {
        Double[] origins = determineOrigin(surfaceReferenceAlign, selectedSurface);
        Double originRef;
        Double originSurface;
        originRef = origins[0];
        originSurface = origins[1];
        if (originRef != null
                && originSurface != null) {
            meta.setLastAlignedSurface(surfaceReferenceAlign);
            switch (meta.getAlignDirection()) {
                case Left:
                    selectedSurface.move(new Point2D.Double(selectedSurface.getBounds().x, selectedSurface.getBounds().y),
                            new Point2D.Double(originRef, selectedSurface.getBounds().y));
                    break;
                case Right:
                    selectedSurface.move(new Point2D.Double(selectedSurface.getBounds().x, selectedSurface.getBounds().y),
                            new Point2D.Double(originRef + surfaceReferenceAlign.getBounds().getWidth(), selectedSurface.getBounds().y));
                    break;
                case Top:
                    selectedSurface.move(new Point2D.Double(selectedSurface.getBounds().x, selectedSurface.getBounds().y),
                            new Point2D.Double(selectedSurface.getBounds().x, originRef));
                    break;
                case Bottom:
                    selectedSurface.move(new Point2D.Double(selectedSurface.getBounds().x, selectedSurface.getBounds().y),
                            new Point2D.Double(selectedSurface.getBounds().x, originRef + surfaceReferenceAlign.getBounds().getHeight()));
                    break;
                default:
                    break;
            }
        }

    }

    //TODO: check null commenté (si décommente, align fonctionne plus)
    private Double[] determineOrigin(Surface surfaceReferenceAlign, Surface surface) {
        Double origins[] = new Double[2];
        if (meta.getAlignDirection() != Meta.Direction.Undefined
                && isOriginX()) {
            /*if (origins[0] != null)*/
            origins[0] = surfaceReferenceAlign.getBounds().getX();
            origins[1] = surface.getBounds().getX();
            return origins;
        } else {
            /*if (origins[0] != null)*/
            origins[0] = surfaceReferenceAlign.getBounds().getY();
            origins[1] = surface.getBounds().getY();
            return origins;
        }
    }

    private boolean isOriginX() {
        switch (meta.getAlignDirection()) {
            case Left:
            case Right:
                return true;
            default:
                return false;
        }
    }

    public void mouseRClick(Point2D point) {
        if (meta.getDoing() == Meta.EditionAction.CreatingFreeSurface) {
            if (builder != null && (builder instanceof FreeSurface.Builder)) {
                Surface surface = builder.getSurface();
                meta.getSurfaces().put(surface.getId(), surface);
                builder = null;
            }
        }
    }

    public void mouseDrag(Point2D point) {
        if (meta.getDoing() == Meta.EditionAction.CreatingRectangularSurface) {
            builder.movePoint(point);
        } else if (meta.getDoing() == Meta.EditionAction.Align) {
            //TODO: Do nothing mais bon faut refacto mdr
        } else {
            if (meta.getSelectedSurface() != null) {
                meta.getSelectedSurface().move(meta.getHover(), point);
                if (meta.getSelectedSurface().getPatternGroup() != null
                        && meta.getSelectedSurface().getPatternGroup().getTiles().size() > 0) {
                    meta.getSelectedSurface().getPatternGroup().getTiles().forEach((tile) -> {
                        tile.move(meta.getHover(), point);
                    });
                }
                /*if (meta.isSelectedSurfaceCanBeResized()) {
                    Point2D ratio = calcResizeRatio(point);
                    meta.getSelectedSurface().rescale(ratio.getX(), ratio.getY());
                }*/
            }
//            else if (meta.getHoveredSurface() != null) {
//                Vector2D root = Vector2D.from(meta.getHoveredSurface().getCenter());
//                Vector2D origin = Vector2D.from(meta.getHover());
//                Vector2D target = Vector2D.from(point);
//
//
//                meta.getHoveredSurface().rotateRad(target.angleBetweenRad(root) - origin.angleBetweenRad(root));
//            }/* else if (_cursor == CursorEventType.Resize && _hoveredShape != null) {
//                actionStatus.cursorShape = UIConstants.Mouse.VCursor.Resize;
//                //ancien code, gérer le resize à l'aide des curseurs #cursor pas réimplémenté pour le moment
//            }*/
        }
    }

    public void endBuildingSurface() {
        if (builder != null && (builder instanceof FreeSurface.Builder)) {
            builder.movePoint(null);
            Surface surface = builder.getSurface();
            meta.getSurfaces().put(surface.getId(), surface);
            builder = null;
        }
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

    public void applyPattern(String patternName, Tile tile) {
        Surface surface = meta.getSelectedSurface();
        if (surface != null) {
            surface.applyPattern(patternName, tile);
        }
    }

    public void setAlignDistance(double distance) {
        Surface surface = meta.getSelectedSurface();
        Surface reference = meta.getLastAlignedSurface();
        Double originRef;
        if (surface != null
                && reference != null) {
            switch (meta.getAlignDirection()) {
                case Left:
                    originRef = reference.getBounds().getX();
                    if (originRef > surface.getBounds().x) {
                        distance *= -1;
                    }
                    surface.move(new Point2D.Double(surface.getBounds().x, surface.getBounds().y),
                            new Point2D.Double(originRef + distance, surface.getBounds().y));
                    break;
                case Right:
                    originRef = reference.getBounds().getX();
                    if (originRef > surface.getBounds().x) {
                        distance *= -1;
                    }
                    originRef = reference.getBounds().getX();
                    surface.move(new Point2D.Double(surface.getBounds().x, surface.getBounds().y),
                            new Point2D.Double(originRef + reference.getBounds().getWidth() + distance, surface.getBounds().y));
                    break;
                case Top:
                    originRef = reference.getBounds().getY();
                    if (originRef > surface.getBounds().y) {
                        distance *= -1;
                    }
                    surface.move(new Point2D.Double(surface.getBounds().x, surface.getBounds().y),
                            new Point2D.Double(surface.getBounds().x, originRef + distance));
                    break;
                case Bottom:
                    originRef = reference.getBounds().getY();
                    if (originRef > surface.getBounds().y) {
                        distance *= -1;
                    }
                    surface.move(new Point2D.Double(surface.getBounds().x, surface.getBounds().y),
                            new Point2D.Double(surface.getBounds().x, originRef + reference.getBounds().getHeight() + distance));
                    break;
                default:
                    break;
            }
        }
    }

    public Double getAlignmentDistance() {
        Surface surface = meta.getSelectedSurface();
        Surface reference = meta.getLastAlignedSurface();
        Double originRef;
        Double distance = 0.0;

        return 0.0;
        /* TODO: améliorer / supprimer le code --> voir avec Martin */
        /*if ((meta.getDoing() == Meta.EditionAction.Align
                || meta.getAlignDirection() != Meta.Direction.Undefined)
                && (surface != null && reference != null)) {
            switch (meta.getAlignDirection()) {
                case Left:
                case Right:
                    originRef = reference.getBounds().getX();
                    distance = originRef - surface.getBounds().getX();
                    break;
                case Top:
                case Bottom:
                    originRef = reference.getBounds().getY();
                    distance = originRef - surface.getBounds().getY();
                    break;
                default:
                    break;
            }
        }
        if (distance < 0) {
            distance *= 1;
        }
        return distance;*/
    }

    public void setSurfaceColor(Color color) {
        Surface surface = meta.getSelectedSurface();

        if (surface != null) {
            if (surface.getPatternGroup() == null) {
                surface.setFillColor(color);
                surface.setSettedColor(color);
            }
            surface.setSettedColor(color);
        }
    }
}
