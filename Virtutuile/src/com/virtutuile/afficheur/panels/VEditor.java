package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.swing.panels.KeyboardEventKind;
import com.virtutuile.afficheur.swing.panels.MouseEventKind;
import com.virtutuile.afficheur.swing.panels.VPanelEvents;
import com.virtutuile.domaine.VEditorEngine;
import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.singletons.VApplicationStatus;

import java.awt.*;

public class VEditor extends VPanelEvents {
    private VEditorEngine _editorEngine;
    private UIConstants.Mouse.VCursor _currentCursor = VApplicationStatus.getInstance().cursorShape;

    public VEditor(VEditorEngine editorEngine) {
        setFocusable(true);
        this.setBackground(UIConstants.DRAW_BACKGROUND);
        this.setName("Toolbar");
        this.setBorder(null);
        this._editorEngine = editorEngine;

        addMouseEventListener(MouseEventKind.MousePress, (mouseEvent) -> {
            requestFocusInWindow();
            editorEngine.mouseLClick(mouseEvent.getPoint().x, mouseEvent.getPoint().y);
            repaint();
        });
        addMouseEventListener(MouseEventKind.MouseDrag, (mouseEvent) -> {
            editorEngine.mouseDrag(mouseEvent.getPoint().x, mouseEvent.getPoint().y);
            repaint();
        });

        addMouseEventListener(MouseEventKind.MouseMove, (mouseEvent) -> {
            editorEngine.mouseHover(mouseEvent.getPoint().x, mouseEvent.getPoint().y);
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
        VApplicationStatus status = VApplicationStatus.getInstance();
        if (_currentCursor != status.cursorShape) {
            UIConstants.Mouse.SetCursor(this, status.cursorShape);
            _currentCursor = status.cursorShape;
        }
        this._editorEngine.paint(g);
    }
}
