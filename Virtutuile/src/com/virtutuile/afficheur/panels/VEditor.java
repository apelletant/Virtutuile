package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.swing.panels.KeyboardEventKind;
import com.virtutuile.afficheur.swing.panels.MouseEventKind;
import com.virtutuile.afficheur.swing.panels.VPanelEvents;
import com.virtutuile.moteur.VEditorEngine;
import com.virtutuile.moteur.managers.VPainterManager;
import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.constants.VPhysicsConstants;
import com.virtutuile.systeme.singletons.VActionStatus;
import com.virtutuile.systeme.units.VProperties;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.function.Consumer;

public class VEditor extends VPanelEvents {
    Point p;
    private VEditorEngine _editorEngine;
    private UIConstants.Mouse.VCursor _currentCursor = VActionStatus.getInstance().cursorShape;

    public VEditor(VEditorEngine editorEngine) {
        setFocusable(true);
        this.setBackground(UIConstants.DRAW_BACKGROUND);
        this.setName("Toolbar");
        this.setBorder(null);
        this._editorEngine = editorEngine;

        addMouseEventListener(MouseEventKind.MousePress, (mouseEvent) -> {
            requestFocusInWindow();
            VProperties properties = new VProperties() {{
                coordinates.add(VPhysicsConstants.pointToCoordinates(mouseEvent.getPoint()));
            }};
            editorEngine.mouseLClick(properties);
            repaint();
        });
        addMouseEventListener(MouseEventKind.MouseDrag, (mouseEvent) -> {
            editorEngine.mouseDrag(VPhysicsConstants.pointToCoordinates(mouseEvent.getPoint()));
            repaint();
        });

        addMouseEventListener(MouseEventKind.MouseMove, (me) -> {
            editorEngine.mouseHover(VPhysicsConstants.pointToCoordinates(me.getPoint()));
            repaint();
        });

        addKeyboardEventListener(KeyboardEventKind.KeyPressed, (ke) -> {
            editorEngine.keyEvent(ke);
            repaint();
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        VActionStatus status = VActionStatus.getInstance();
        if (_currentCursor != status.cursorShape) {
            UIConstants.Mouse.SetCursor(this, status.cursorShape);
            _currentCursor = status.cursorShape;
        }
        this._editorEngine.paint(VPainterManager.getInstance().getPainter(g));
    }
}
