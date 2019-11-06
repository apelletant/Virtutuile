package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.swing.panels.KeyboardEventKind;
import com.virtutuile.afficheur.swing.panels.MouseEventKind;
import com.virtutuile.afficheur.swing.panels.VPanelEvents;
import com.virtutuile.domaine.VEditorEngine;
import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.singletons.VApplicationStatus;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelEvent;

public class VEditor extends VPanelEvents {
    private VEditorEngine editorEngine;
    private UIConstants.Mouse.VCursor _currentCursor = VApplicationStatus.getInstance().cursorShape;

    public VEditor(VEditorEngine editorEngine) {
        setFocusable(true);
        this.setOpaque(true);
        this.setBackground(UIConstants.DRAW_BACKGROUND);
        this.setName("Toolbar");
        this.setBorder(null);
        this.editorEngine = editorEngine;

        setupMouseEvents();
        setupKeyboardEvents();
    }

    private void setupKeyboardEvents() {
        addKeyboardEventListener(KeyboardEventKind.KeyPressed, (ke) -> {
            editorEngine.keyEvent(ke);
            repaint();
        });
    }

    private void setupMouseEvents() {

        addMouseEventListener(MouseEventKind.MousePress, (mouseEvent) -> {
            requestFocusInWindow();
            editorEngine.mouseLClick(mouseEvent.getPoint().x, mouseEvent.getPoint().y);
            repaint();
        });
        addMouseEventListener(MouseEventKind.MouseDrag, (mouseEvent) -> {
            editorEngine.mouseDrag(mouseEvent.getPoint().x, mouseEvent.getPoint().y);
            repaint();
        });

        addMouseEventListener(MouseEventKind.MouseRelease, (mouseEvent -> {
            editorEngine.mouseRelease(mouseEvent.getPoint().x, mouseEvent.getPoint().y);
            repaint();
        }));

        addMouseEventListener(MouseEventKind.MouseMove, (mouseEvent) -> {
            editorEngine.mouseHover(mouseEvent.getPoint().x, mouseEvent.getPoint().y);
            repaint();
        });

        addMouseEventListener(MouseEventKind.MouseWheel, (mouseEvent) -> {
            MouseWheelEvent evt = (MouseWheelEvent) mouseEvent;
            VApplicationStatus.VEditor.getInstance().incrementZoom(evt.getPreciseWheelRotation() * UIConstants.Editor.ZOOM_FACTOR);
            repaint();
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        VApplicationStatus.VEditor.getInstance().setSize(getWidth(), getHeight());
        VApplicationStatus status = VApplicationStatus.getInstance();
        if (_currentCursor != status.cursorShape) {
            UIConstants.Mouse.SetCursor(this, status.cursorShape);
            _currentCursor = status.cursorShape;
        }
        this.editorEngine.paint(g);
    }
}
