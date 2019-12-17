package com.virtutuile.domaine;

import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.domaine.entities.Meta;
import com.virtutuile.domaine.entities.surfaces.*;
import com.virtutuile.domaine.entities.tools.PolygonTransformer;
import com.virtutuile.shared.Pair;
import com.virtutuile.shared.Vector2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import static com.virtutuile.domaine.entities.Meta.EditionAction.CreatingFreeSurface;

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
            meta.addToUndo();
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

        if (builder != null && meta.getDoing() == CreatingFreeSurface) {
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

            Path2D.Double path = PolygonTransformer.flate(surface.getPolygon(), 1);

            Path p = PolygonTransformer.awtPathToJavafx(path);

            Surface s1 = new Surface(path, false);
            meta.getSurfaces().put(s1.getId(), s1);
            builder = null;
            /*System.out.println("Surface Origin : {" + surface.getVertices()[0].getX() + ", " + surface.getVertices()[0].getY());
            System.out.println("Surface Dimensions : Width: " + surface.getPolygon().getBounds2D().getWidth() + ", Height: " + surface.getPolygon().getBounds2D().getHeight());*/
        }

        if (meta.getDoing() != Meta.EditionAction.CreatingFreeSurface) {
            meta.addToUndo();
        }
        mouseHover(point);
    }

    public void mouseLClick(Point2D point) {
        meta.setMousePressed(true);
        switch (meta.getDoing()) {
            case CreatingRectangularSurface:
                if (builder == null)
                    builder = RectangularSurface.getBuilder();
                builder.placePoint(point);
                break;
            case CreatingFreeSurface:
                if (builder == null)
                    builder = FreeSurface.getBuilder();
                builder.placePoint(point);
                break;
            case Align:
                Surface surfaceReferenceAlign = getSurfaceAt(point);
                if (surfaceReferenceAlign != null)
                    alignSurfaces(surfaceReferenceAlign, meta.getSelectedSurface());
                break;
            case Stick:
                Surface surfaceReferenceStick = getSurfaceAt(point);
                if (surfaceReferenceStick != null
                        && surfaceReferenceStick != meta.getSelectedSurface())
                    stickSurfaces(surfaceReferenceStick, meta.getSelectedSurface());
                break;
            default:
                selectShape(point);
                break;
        }
    }

    private void stickSurfaces(Surface surfaceReferenceStick, Surface selectedSurface) {
        Rectangle2D.Double refBounds = null;
        Rectangle2D.Double surfaceBounds = null;

        if (selectedSurface != null) {
            surfaceBounds = selectedSurface.getBounds();
        } else {
            return;
        }

        if (surfaceReferenceStick != null) {
            refBounds = surfaceReferenceStick.getBounds();
            switch (meta.getStickOrientation()) {
                case Horizontal:
                    if (refBounds.getY() >= surfaceBounds.getY()) {
                        selectedSurface.move(new Point2D.Double(surfaceBounds.x, surfaceBounds.y),
                                new Point2D.Double(refBounds.x, surfaceReferenceStick.getBounds().y - surfaceBounds.getHeight()));
                    } else {
                        selectedSurface.move(new Point2D.Double(surfaceBounds.x, surfaceBounds.y),
                                new Point2D.Double(refBounds.x, surfaceReferenceStick.getBounds().y + refBounds.getHeight()));
                    }
                    break;
                case Vertical:
                    if (refBounds.getX() >= surfaceBounds.getX()) {
                        selectedSurface.move(new Point2D.Double(surfaceBounds.x, surfaceBounds.y),
                                new Point2D.Double(surfaceReferenceStick.getBounds().x - surfaceBounds.getWidth(), refBounds.y));
                    } else {
                        selectedSurface.move(new Point2D.Double(surfaceBounds.x, surfaceBounds.y),
                                new Point2D.Double(surfaceReferenceStick.getBounds().x + refBounds.getWidth(), refBounds.y));
                    }
                    break;
                default:
                    break;
            }
            meta.addToUndo();
        }
    }

    private void alignSurfaces(Surface surfaceReferenceAlign, Surface selectedSurface) {
        Double[] origins = determineOrigin(surfaceReferenceAlign, selectedSurface);
        Double originRef;
        double offset = 0.0;

        if (origins != null) {
            originRef = origins[0];

            meta.setLastAlignedSurface(surfaceReferenceAlign);
            switch (meta.getAlignDirection()) {
                case Left:
                    selectedSurface.move(new Point2D.Double(selectedSurface.getBounds().x, selectedSurface.getBounds().y),
                            new Point2D.Double(originRef, selectedSurface.getBounds().y),
                            selectedSurface);
                    break;
                case Right:
                    selectedSurface.move(new Point2D.Double(selectedSurface.getBounds().x, selectedSurface.getBounds().y),
                            new Point2D.Double(originRef + surfaceReferenceAlign.getBounds().getWidth(), selectedSurface.getBounds().y),
                            selectedSurface);
                    break;
                case Top:
                    selectedSurface.move(new Point2D.Double(selectedSurface.getBounds().x, selectedSurface.getBounds().y),
                            new Point2D.Double(selectedSurface.getBounds().x, originRef),
                            selectedSurface);
                    break;
                case Bottom:
                    selectedSurface.move(new Point2D.Double(selectedSurface.getBounds().x, selectedSurface.getBounds().y),
                            new Point2D.Double(selectedSurface.getBounds().x, originRef + surfaceReferenceAlign.getBounds().getHeight()),
                            selectedSurface);
                    break;
                case CenteredHorizontal:
                    offset = (surfaceReferenceAlign.getBounds().getHeight() - selectedSurface.getBounds().getHeight()) / 2;
                    selectedSurface.move(new Point2D.Double(selectedSurface.getBounds().x, selectedSurface.getBounds().y),
                            new Point2D.Double(selectedSurface.getBounds().x, originRef + offset),
                            selectedSurface);
                    break;
                case CenteredVertical:
                    offset = (selectedSurface.getBounds().getWidth() - surfaceReferenceAlign.getBounds().getWidth()) / 2;
                    selectedSurface.move(new Point2D.Double(selectedSurface.getBounds().x, selectedSurface.getBounds().y),
                            new Point2D.Double(originRef + (offset * -1), selectedSurface.getBounds().y),
                            selectedSurface);
                    break;
                default:
                    break;
            }
            meta.addToUndo();
        }
    }

    private Double[] determineOrigin(Surface surfaceReferenceAlign, Surface surface) {
        Double[] origins = null;
        if (surfaceReferenceAlign != null && surface != null) {
            origins = new Double[2];
            if (meta.getAlignDirection() != Meta.Direction.Undefined
                    && isOriginX()) {
                origins[0] = surfaceReferenceAlign.getBounds().getX();
                origins[1] = surface.getBounds().getX();
                return origins;
            } else {
                origins[0] = surfaceReferenceAlign.getBounds().getY();
                origins[1] = surface.getBounds().getY();
                return origins;
            }
        }
        return origins;
    }

    private boolean isOriginX() {
        switch (meta.getAlignDirection()) {
            case Left:
            case Right:
            case CenteredVertical:
                return true;
            default:
                return false;
        }
    }

    public void mouseRClick(Point2D point) {
        if (meta.getDoing() == CreatingFreeSurface) {
            if (builder != null && (builder instanceof FreeSurface.Builder)) {
                Surface surface = builder.getSurface();
                meta.getSurfaces().put(surface.getId(), surface);
                builder = null;
                meta.addToUndo();
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
                meta.getSelectedSurface().move(meta.getHover(), point, meta.getSelectedSurface());
                /*if (meta.getSelectedSurface().getPatternGroup() != null
                        && meta.getSelectedSurface().getPatternGroup().getTiles().size() > 0) {
                    meta.getSelectedSurface().getPatternGroup().getTiles().forEach((tile) -> {
                        tile.move(meta.getHover(), point);
                    });
                }*/
            }
            else if (meta.getHoveredSurface() != null) {
                Vector2D root = Vector2D.from(meta.getHoveredSurface().getCenter());
                Vector2D origin = Vector2D.from(meta.getHover());
                Vector2D target = Vector2D.from(point);

                meta.getHoveredSurface().rotateRad(target.angleBetweenRad(root) - origin.angleBetweenRad(root));
            }
//            }/* else if (_cursor == CursorEventType.Resize && _hoveredShape != null) {
//                actionStatus.cursorShape = UIConstants.Mouse.VCursor.Resize;
//                //ancien code, gérer le resize à l'aide des curseurs #cursor pas réimplémenté pour le moment
//            }*/
        }
    }

    public void rotateSurface(double rotationDeg) {
        meta.getSelectedSurface().rotateDeg(rotationDeg);
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
            removeFromLinkedList(surface);
            meta.addToUndo();
        }
    }

    private void removeFromLinkedList(Surface surface) {
        Surface tmpPrevious;
        Surface tmpNext;

        if (surface.getNext() != null) {
            tmpPrevious = surface.getPrevious();
            tmpNext = surface.getNext();
            tmpPrevious.setNext(tmpNext);
            tmpNext.setPrevious(tmpPrevious);
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
            meta.addToUndo();
        }
    }

    public void setAlignDistance(double distance) {
        Surface surface = meta.getSelectedSurface();
        Surface reference = meta.getLastAlignedSurface();
        Double originRef;

        if (meta.getUnitSetted().equals("Imperial")) {
            distance = meta.inchToCentimeter(distance);
        }

        if (surface != null
                && reference != null) {
            switch (meta.getAlignDirection()) {
                case Left:
                    originRef = reference.getBounds().getX();
                    if (originRef > surface.getBounds().x) {
                        distance *= -1;
                    }
                    surface.move(new Point2D.Double(surface.getBounds().x, surface.getBounds().y),
                            new Point2D.Double(originRef + distance, surface.getBounds().y), meta.getSelectedSurface());
                    break;
                case Right:
                    originRef = reference.getBounds().getX();
                    if (originRef > surface.getBounds().x) {
                        distance *= -1;
                    }
                    originRef = reference.getBounds().getX();
                    surface.move(new Point2D.Double(surface.getBounds().x, surface.getBounds().y),
                            new Point2D.Double(originRef + reference.getBounds().getWidth() + distance, surface.getBounds().y), meta.getSelectedSurface());
                    break;
                case Top:
                    originRef = reference.getBounds().getY();
                    if (originRef > surface.getBounds().y) {
                        distance *= -1;
                    }
                    surface.move(new Point2D.Double(surface.getBounds().x, surface.getBounds().y),
                            new Point2D.Double(surface.getBounds().x, originRef + distance), meta.getSelectedSurface());
                    break;
                case Bottom:
                    originRef = reference.getBounds().getY();
                    if (originRef > surface.getBounds().y) {
                        distance *= -1;
                    }
                    surface.move(new Point2D.Double(surface.getBounds().x, surface.getBounds().y),
                            new Point2D.Double(surface.getBounds().x, originRef + reference.getBounds().getHeight() + distance), meta.getSelectedSurface());
                    break;
                default:
                    break;
            }
            meta.addToUndo();
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
            meta.addToUndo();
        }
    }

    public void dragPattern(Point2D.Double mouse) {
        Point2D.Double hover = (Point2D.Double) meta.getHover();

        if (meta.getSelectedSurface() != null && meta.getSelectedSurface().getPatternGroup() != null)
            meta.getSelectedSurface().getPatternGroup().moveOrigin(mouse.x - hover.x, mouse.y - hover.y);
    }

    public void stickSurfaces() {
        Surface surfaceIntersect;
        Surface selectedSurface = meta.getSelectedSurface();
        if (selectedSurface != null) {
            surfaceIntersect = meta.getSurfaceIntersected(selectedSurface);
            if (surfaceIntersect != null) {
                linkSurfaces(selectedSurface, surfaceIntersect);
                meta.addToUndo();
            }
        }
    }

    private void printLinked(Surface surface) {
        Surface it = surface.getNext();
        if (surface.getNext() != null) {
            while (it != surface) {
                it = it.getNext();
            }
        }
    }

    private boolean isNotAlreadyLinked(Surface surface, Surface surfaceToAdd) {
        Surface it = surface.getNext();

        if (it != null) {
            if (it != surface) {
                while (it != surface) {
                    if (it == surfaceToAdd) {
                        return false;
                    }
                    it = it.getNext();
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private void linkSurfaces(Surface selectedSurface, Surface surfaceIntersect) {
        printLinked(selectedSurface);

        Surface tmp;
        Surface tmp2;
        if (selectedSurface.getNext() != null && isNotAlreadyLinked(surfaceIntersect, selectedSurface)) {
            tmp = selectedSurface.getNext();
            if (surfaceIntersect.getNext() != null) {
                tmp2 = surfaceIntersect.getPrevious();
                selectedSurface.setNext(surfaceIntersect);
                surfaceIntersect.setPrevious(selectedSurface);
                tmp2.setNext(tmp);
                tmp.setPrevious(tmp2);

            } else {
                selectedSurface.setNext(surfaceIntersect);
                surfaceIntersect.setPrevious(selectedSurface);
                surfaceIntersect.setNext(tmp);
                tmp.setPrevious(surfaceIntersect);
            }
        } else if (surfaceIntersect.getNext() != null && isNotAlreadyLinked(selectedSurface, surfaceIntersect)) {
            tmp = surfaceIntersect.getNext();
            if (selectedSurface.getNext() != null) {
                tmp2 = selectedSurface.getPrevious();
                surfaceIntersect.setNext(selectedSurface);
                selectedSurface.setPrevious(surfaceIntersect);
                tmp2.setNext(tmp);
                tmp.setPrevious(tmp2);
            } else {
                surfaceIntersect.setNext(selectedSurface);
                selectedSurface.setPrevious(surfaceIntersect);
                selectedSurface.setNext(tmp);
                tmp.setPrevious(selectedSurface);
            }
        } else if (isNotAlreadyLinked(selectedSurface, surfaceIntersect)) {
            selectedSurface.setNext(surfaceIntersect);
            selectedSurface.setPrevious(surfaceIntersect);
            surfaceIntersect.setNext(selectedSurface);
            surfaceIntersect.setPrevious(selectedSurface);
        }

    }

    public void unstickSurfaces() {
        Surface selectedSurface = meta.getSelectedSurface();
        Surface tmpPrevious = null;
        Surface tmpNext = null;

        if (selectedSurface != null
                && selectedSurface.getNext() != null) {
            tmpNext = selectedSurface.getNext();
            tmpPrevious = selectedSurface.getPrevious();

            tmpNext.setPrevious(tmpPrevious);
            tmpPrevious.setNext(tmpNext);

            selectedSurface.setNext(null);
            selectedSurface.setPrevious(null);
            meta.addToUndo();
        }
    }

    private Surface transformToOneSurface(Path2D.Double[] polygons) {
        Surface[] surfaces = new Surface[polygons.length];
        Surface returnedSurface = null;

        for (int i = 0; i < polygons.length; i++) {
            surfaces[i] = new Surface(polygons[i], false);
        }

        for (Surface surface : surfaces) {
            for (Surface surfaceContain : surfaces) {
                if (surface != surfaceContain) {
                    if (surface.getId() != surfaceContain.getId()) {
                        if (surface.contains(surfaceContain)) {
                            surfaceContain.setHole(true);
                        }
                    }
                }
            }
        }

        for (Surface surface : surfaces) {
            if (!surface.isHole()) {
                returnedSurface = surface;
            }
        }

        for (Surface surface : surfaces) {
            if (surface != returnedSurface) {
                assert returnedSurface != null;
                returnedSurface.addPath(surface.getVertices());
            }
        }

        return returnedSurface;
    }

    public void mergeSurfaces() {
        meta.setDoing(Meta.EditionAction.Idle);

        boolean merged = false;
        Surface oldSelectedSurface = meta.getSelectedSurface();
        Surface secondSurface = null;
        Surface mergedSurface = null;

        if (meta.getSelectedSurface() != null) {
            Iterator<Pair<UUID, Surface>> iterator = meta.getSurfaces().iterator();
            do {
                Pair<UUID, Surface> pair = iterator.next();
                if (meta.getSelectedSurface().getId() != pair.getKey()) {
                    if (meta.getSelectedSurface().containsOrIntersect(pair.getValue())) {
                        Path2D.Double[] polygons = PolygonTransformer.merge(meta.getSelectedSurface().getPolygon(), pair.getValue().getPolygon());
                        if (polygons.length > 1) {
                            mergedSurface = transformToOneSurface(polygons);
                        } else {
                            mergedSurface = new Surface(polygons[0], false);
                        }
                        meta.getSurfaces().put(mergedSurface.getId(), mergedSurface);
                        merged = true;
                        secondSurface = pair.getValue();
                    }
                    if (merged) {
                        if (oldSelectedSurface.getPatternGroup() != null) {
                            mergedSurface.setTypeOfTile(oldSelectedSurface.getTypeOfTile());
                            mergedSurface.getGrout().setThickness(oldSelectedSurface.getGrout().getThickness());
                            mergedSurface.getGrout().setColor(oldSelectedSurface.getGrout().getColor());
                            mergedSurface.applyPattern(oldSelectedSurface.getPatternGroup().getPattern().getName(), meta.getDefaultTile());
                            mergedSurface.getPatternGroup().setRotation(oldSelectedSurface.getPatternGroup().getRotation());
                            mergedSurface.getPatternGroup().setShiftDirection(oldSelectedSurface.getPatternGroup().getShiftDirection());
                            mergedSurface.getPatternGroup().setShift(oldSelectedSurface.getPatternGroup().getShift());
                            mergedSurface.getPatternGroup().recalcPattern(mergedSurface);
                        } else if (secondSurface.getPatternGroup() != null) {
                            mergedSurface.setTypeOfTile(secondSurface.getTypeOfTile());
                            mergedSurface.getGrout().setThickness(secondSurface.getGrout().getThickness());
                            mergedSurface.getGrout().setColor(secondSurface.getGrout().getColor());
                            mergedSurface.applyPattern(secondSurface.getPatternGroup().getPattern().getName(), meta.getDefaultTile());
                            mergedSurface.getPatternGroup().setRotation(secondSurface.getPatternGroup().getRotation());
                            mergedSurface.getPatternGroup().setShiftDirection(secondSurface.getPatternGroup().getShiftDirection());
                            mergedSurface.getPatternGroup().setShift(secondSurface.getPatternGroup().getShift());
                            mergedSurface.getPatternGroup().recalcPattern(mergedSurface);
                        }
                        break;
                    }
                }
            } while(iterator.hasNext());
            if (merged) {
                meta.setSelectedSurface(null);
                removeFromLinkedList(oldSelectedSurface);
                removeFromLinkedList(secondSurface);
                meta.getSurfaces().remove(oldSelectedSurface.getId());
                meta.getSurfaces().remove(secondSurface.getId());
                meta.addToUndo();
            }
        }
    }

    public void rotatePattern(double rotation) {
        Surface surface = meta.getSelectedSurface();

        if (surface != null && surface.getPatternGroup() != null) {
            surface.getPatternGroup().setRotation(rotation);
            surface.getPatternGroup().recalcPattern(surface);
            if (meta.getLastEvent() != MouseEventKind.MouseDrag) {
                meta.addToUndo();
            }
        }
    }
}
