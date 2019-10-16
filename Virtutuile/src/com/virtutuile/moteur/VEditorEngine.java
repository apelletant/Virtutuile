package com.virtutuile.moteur;

import com.virtutuile.systeme.interfaces.IVGraphics;
import com.virtutuile.systeme.units.VCoordinates;

public class VEditorEngine {

    private VEditorState _editorState;

    public void paint(IVGraphics graphics) {

    }

    public void mouseHover(VCoordinates coordinates){

    }

    public void mouseLClick(VCoordinates coordinates) {

    }

    public void mouseRClick(VCoordinates coordinates) {

    }

    public VEditorState getEditorState() {
        return this._editorState;
    }

    public void setEditorState(VEditorState editorState) {
        this._editorState = editorState;
    }

    public enum VEditorState {
        Idle,
        CreatingRectShape,
        CreatingFreeShape
    }
}
