package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.swing.panels.KeyboardEventKind;
import com.virtutuile.afficheur.swing.panels.MouseEventKind;
import com.virtutuile.afficheur.swing.panels.VPanelEvents;
import com.virtutuile.domaine.moteur.VEditorEngine;
import com.virtutuile.domaine.moteur.managers.VPainterManager;
import com.virtutuile.domaine.systeme.constants.UIConstants;
import com.virtutuile.domaine.systeme.constants.VPhysicsConstants;
import com.virtutuile.domaine.systeme.singletons.VActionStatus;
import com.virtutuile.domaine.systeme.units.VProperties;

import java.awt.*;

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
