package com.virtutuile.systeme.singletons;

import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.constants.VPhysicsConstants;
import com.virtutuile.systeme.units.VCoordinate;

import java.awt.*;
import java.util.Vector;
import java.util.function.Consumer;

public class VApplicationStatus {
    private static VApplicationStatus _applicationStatus = null;

    private VApplicationStatus() {
    }

    public VActionState doing = VActionState.Idle;
    public VActionManager manager = VActionManager.Shape;
    public UIConstants.Mouse.VCursor cursorShape = UIConstants.Mouse.VCursor.Pointer;
    private Vector<VPanelType> _activePanels = new Vector<>();
    private Consumer<Vector<VPanelType>> _onPanelChange;

    public static VApplicationStatus getInstance() {
        if (_applicationStatus == null) {
            _applicationStatus = new VApplicationStatus();
        }

        return _applicationStatus;
    }

    public void setOnPanelChange(Consumer<Vector<VPanelType>> consumer) {
        _onPanelChange = consumer;
    }

    public void setActivePanels(Vector<VPanelType> activePanels) {
        this.setActivePanels(activePanels, true);
    }

    public void setActivePanels(Vector<VPanelType> activePanels, boolean throwEvent) {
        this._activePanels = activePanels;
        if (throwEvent) {
            this._onPanelChange.accept(this._activePanels);
        }
    }

    public void setActivePanel(VPanelType panel) {
        this.setActivePanel(panel, true);
    }

    public void setActivePanel(VPanelType panel, boolean throwEvent) {
        this._activePanels.clear();
        this._activePanels.add(panel);
        if (throwEvent) {
            this._onPanelChange.accept(this._activePanels);
        }
    }

    public Vector<VPanelType> getActivePanels() {
        return this._activePanels;
    }

    public void addActivePanel(VPanelType activePanel) {
        this.addActivePanel(activePanel, true);
    }

    public void addActivePanel(VPanelType activePanel, boolean throwEvent) {

        if (!_activePanels.contains(activePanel)) {
            this._activePanels.add(activePanel);
            if (throwEvent) {
                this._onPanelChange.accept(this._activePanels);
            }
        }
    }

    public void removeActivePanel(VPanelType activePanel) {
        this.removeActivePanel(activePanel, true);
    }

    public void removeActivePanel(VPanelType activePanel, boolean throwEvent) {
        if (_activePanels.contains(activePanel)) {
            this._activePanels.remove(activePanel);
            if (throwEvent) {
                this._onPanelChange.accept(this._activePanels);
            }
        }
    }

    public enum VActionState {
        Idle,
        CreatingRectShape,
        CreatingFreeShape,
        ShapeSelected,
        EditingShape,
        PatternSelected,
        MovingShape,
        MergingShapes,
        StickingShapes,
        AligningShapes,
        EditingTile,
        Undo,
        Redo
    }

    public enum VPanelType {
        Settings,
        Informations,
        DrawShape,
        PatternManagement,
    }

    public enum VActionManager {
        Pattern,
        Shape
    }

    public static class VEditor {
        private static VEditor instance;

        private VEditor() {
        }

        public static VEditor getInstance() {
            if (instance == null) {
                instance = new VEditor();
            }
            return instance;
        }

        private double zoom = 100D;
        private Dimension editorSize = new Dimension();
        private VCoordinate editorPosition = new VCoordinate();
        public VPhysicsConstants.SystemUnit SystemUnit = VPhysicsConstants.SystemUnit.Metric;
        private boolean magneticGridIsActivated = false;

        public int getZoom() {
            return (int) zoom;
        }

        public double getRelativeZoom() {
            return zoom / 100;
        }

        public VEditor incrementZoom(double increment) {
            this.zoom = Math.min(Math.max(zoom + increment, UIConstants.Editor.MINIMAL_ZOOM), UIConstants.Editor.MAXIMAL_ZOOM);
            return this;
        }

        public VEditor setZoom(int zoom) {
            this.zoom = Math.min(Math.max(zoom, UIConstants.Editor.MINIMAL_ZOOM), UIConstants.Editor.MAXIMAL_ZOOM);
            return this;
        }

        public void setPosition(double longitude, double latitude) {
            editorPosition.longitude = longitude;
            editorPosition.latitude = latitude;
        }

        public VCoordinate getEditorPosition() {
            return editorPosition;
        }

        public void setSize(int width, int height) {
            editorSize.width = width;
            editorSize.height = height;
        }

        public Dimension getSize() {
            return editorSize;
        }

        public boolean getGridStatus() {
            return this.magneticGridIsActivated;
        }

        public void changeGridStatus() {
            magneticGridIsActivated = !magneticGridIsActivated;
        }
    }
}
