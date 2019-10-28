package com.virtutuile.domaine.systeme.singletons;

import com.virtutuile.domaine.systeme.constants.UIConstants;

public class VActionStatus {
    private static VActionStatus _actionsStatus  = null;

    private VActionStatus(){}

    public VActionState doing = VActionState.Idle;
    public VActionManager manager = VActionManager.Shape;
    public UIConstants.Mouse.VCursor cursorShape = UIConstants.Mouse.VCursor.Pointer;

    public static VActionStatus getInstance() {
        if (_actionsStatus == null) {
            _actionsStatus = new VActionStatus();
        }

        return _actionsStatus;
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

    public enum VActionManager {
        Pattern,
        Shape
    }
}
