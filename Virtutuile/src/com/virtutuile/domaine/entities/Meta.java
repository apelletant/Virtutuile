package com.virtutuile.domaine.entities;

import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.shared.UnorderedMap;

import java.awt.*;
import java.util.UUID;

public class Meta {

    private UnorderedMap<UUID, Surface> surfaces;

    private EditionAction doing;

    private Surface selectedSurface;
    private Surface hoveredSurface;

    private Point clicked;
    private Point hover;
    private boolean mousePressed;

    public Meta() {
        selectedSurface = null;
        hoveredSurface = null;
        surfaces = new UnorderedMap<>();
        doing = EditionAction.Idle;
        clicked = null;
        hover = null;
        mousePressed = false;
    }

    public Surface getSelectedSurface() {
        return selectedSurface;
    }

    public void setSelectedSurface(Surface selectedSurface) {
        this.selectedSurface = selectedSurface;
    }

    public UnorderedMap<UUID, Surface> getSurfaces() {
        return surfaces;
    }

    public void setSurfaces(UnorderedMap<UUID, Surface> surfaces) {
        this.surfaces = surfaces;
    }

    public EditionAction getDoing() {
        return doing;
    }

    public void setDoing(EditionAction doing, boolean isDoing) {
        if (isDoing) {
            this.doing = doing;
        } else {
            this.doing = EditionAction.Idle;
        }
    }

    public Point getClicked() {
        return clicked;
    }

    public void setClicked(Point clicked) {
        this.clicked = clicked;
    }

    public Point getHover() {
        return hover;
    }

    public void setHover(Point hover) {
        this.hover = hover;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Surface getHoveredSurface() {
        return hoveredSurface;
    }

    public void setHoveredSurface(Surface hoveredSurface) {
        this.hoveredSurface = hoveredSurface;
    }

    public enum EditionAction {
        Idle,
        CreatingRectangularSurface,
        CreatingFreeSurface,
    }
}
