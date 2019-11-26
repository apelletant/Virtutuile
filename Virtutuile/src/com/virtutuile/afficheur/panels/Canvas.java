package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.swing.PanelEvents;
import com.virtutuile.afficheur.swing.events.KeyboardEventKind;
import com.virtutuile.afficheur.swing.events.MouseEventKind;

import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class Canvas extends PanelEvents {

    private MainWindow mainWindow;

    public Canvas(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;

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
            mainWindow.getController().mouseLClick(mouseEvent.getPoint());
            mainWindow.getEditionPanel().surfaceGetSurfaceDimensions();
            mainWindow.getEditionPanel().surfaceGetGroutThickness();
            repaint();
        });
        addMouseEventListener(MouseEventKind.MouseDrag, (mouseEvent) -> {
            mainWindow.getController().mouseDrag(mouseEvent.getPoint());
            repaint();
        });

        addMouseEventListener(MouseEventKind.MouseRelease, (mouseEvent -> {
            mainWindow.getController().mouseRelease(mouseEvent.getPoint());
            repaint();
        }));

        addMouseEventListener(MouseEventKind.MouseMove, (mouseEvent) -> {
            mainWindow.getController().mouseHover(mouseEvent.getPoint());
            mainWindow.getBottomToolbar().getHoverSurfaceBound();
            repaint();
        });

        addMouseEventListener(MouseEventKind.MouseWheel, (mouseEvent) -> {
            mainWindow.getController().updateZoom(((MouseWheelEvent) mouseEvent).getPreciseWheelRotation(), mouseEvent.getPoint());
            repaint();
        });
    }

    private void setKeyboardEvents() {
        addKeyboardEventListener(KeyboardEventKind.KeyPressed, (keyboardEventKind) -> {
            mainWindow.getController().keyEvent(keyboardEventKind);
            repaint();
        });
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        mainWindow.getController().setCanvasSize(getWidth(), getHeight());
        mainWindow.getController().paint(graphics);
        mainWindow.getController().setCanvasSize(getSize());
    }

}
