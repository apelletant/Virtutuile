package com.virtutuile.domaine.systeme.singletons;

import com.virtutuile.domaine.systeme.constants.UIConstants;

import java.util.Vector;
import java.util.function.Consumer;

public class VApplicationStatus {
    private static VApplicationStatus _actionsStatus  = null;

    private VApplicationStatus(){}

    public VActionState doing = VActionState.Idle;
    public VActionManager manager = VActionManager.Shape;
    public UIConstants.Mouse.VCursor cursorShape = UIConstants.Mouse.VCursor.Pointer;
    private Vector<VPanelType> _activePanels = new Vector<>();
    private Consumer<Vector<VPanelType>> _onPanelChange;

    public static VApplicationStatus getInstance() {
        if (_actionsStatus == null) {
            _actionsStatus = new VApplicationStatus();
        }

        return _actionsStatus;
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

    public void addActivePanel(VPanelType activePanels) {
        this.addActivePanel(activePanels, true);
    }

    public void addActivePanel(VPanelType activePanel, boolean throwEvent) {

        if (!_activePanels.contains(activePanel)) {
            this._activePanels.add(activePanel);
        }
        if (throwEvent) {
            this._onPanelChange.accept(this._activePanels);
            System.out.println("add active panel");
        }
    }

    public void removeActivePanel(VPanelType activePanel) {
       this.removeActivePanel(activePanel, true);
    }

    public void removeActivePanel(VPanelType activePanel, boolean throwEvent) {
        this._activePanels.remove(activePanel);
        if (throwEvent) {
            this._onPanelChange.accept(this._activePanels);
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
}
