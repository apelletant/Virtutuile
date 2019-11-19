package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.swing.PanelEvents;
import com.virtutuile.afficheur.swing.events.KeyboardEventKind;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.domaine.Controller;

import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class Canvas extends PanelEvents {

    private Controller controller;
    private BottomToolbar bottomToolbar;
    private EditionPanel editionPanel;

    public Canvas(Controller controller, BottomToolbar bottomToolbar, EditionPanel editionPanel) {
        super();
        this.controller = controller;
        this.bottomToolbar = bottomToolbar;
        this.editionPanel = editionPanel;

        setFocusable(true);
        setOpaque(true);
        setBackground(Constants.DRAW_BACKGROUND);
        setName("Canvas");
        setBorder(null);

        setKeyboardEvents();
        setMouseEvents();
        setFocusable(true);
    }


    private void setMouseEvents() {
        addMouseEventListener(MouseEventKind.MousePress, (mouseEvent) -> {
            requestFocusInWindow();
            controller.mouseLClick(mouseEvent.getPoint());
            editionPanel.surfaceGetSurfaceDimensions();
            editionPanel.surfaceGetGroutThickness();
            repaint();
        });
        addMouseEventListener(MouseEventKind.MouseDrag, (mouseEvent) -> {
            controller.mouseDrag(mouseEvent.getPoint());
            repaint();
        });

        addMouseEventListener(MouseEventKind.MouseRelease, (mouseEvent -> {
            controller.mouseRelease(mouseEvent.getPoint());
            repaint();
        }));

        addMouseEventListener(MouseEventKind.MouseMove, (mouseEvent) -> {
            controller.mouseHover(mouseEvent.getPoint());
//            bottomToolbar.setSurfaceBounds();
            repaint();
        });

        //TODO: zoom mouse event
        addMouseEventListener(MouseEventKind.MouseWheel, (mouseEvent) -> {
            controller.updateZoom(((MouseWheelEvent) mouseEvent).getPreciseWheelRotation(), mouseEvent.getPoint());
            repaint();
        });
    }

    private void setKeyboardEvents() {
        addKeyboardEventListener(KeyboardEventKind.KeyPressed, (keyboardEventKind) -> {
            controller.keyEvent(keyboardEventKind);
            repaint();
        });
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        controller.setCanvasSize(getWidth(), getHeight());
        this.controller.paint(graphics);
        controller.setCanvasSize(this.getSize());
    }

    public Controller getController() {
        return controller;
    }

}
