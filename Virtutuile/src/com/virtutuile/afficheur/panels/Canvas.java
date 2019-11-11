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

    public Canvas(Controller controller, BottomToolbar bottomToolbar) {
        super();
        this.controller = controller;
        this.bottomToolbar = bottomToolbar;

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
            MouseWheelEvent event = (MouseWheelEvent) mouseEvent;
            /*VApplicationStatus.VEditor.getInstance().incrementZoom(evt.getPreciseWheelRotation() * UIConstants.Editor.ZOOM_FACTOR);*/
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
        this.controller.paint(graphics);
        controller.setCanvasSize(this.getSize());
    }

    public Controller getController() {
        return controller;
    }

}
