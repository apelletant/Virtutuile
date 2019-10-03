package com.virtutuile.graphics.wrap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Vector;
import java.util.function.Consumer;

public class JPanelEventListened extends JPanel implements MouseListener, MouseMotionListener {
    protected boolean _is_active = false;
    protected boolean _hovered = false;

    HashMap<MouseEventKind, Vector<Consumer<MouseEvent>>> _events = new HashMap<>();

    public JPanelEventListened(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public JPanelEventListened(LayoutManager layout) {
        super(layout);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public JPanelEventListened(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public JPanelEventListened() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Adds a listener to a specific event.
     * @param event The event to add
     * @param cb The event to call
     */
    public void addEventListener(MouseEventKind event, Consumer<MouseEvent> cb) {

        if (_events.containsKey(event)) {
            for (Vector<Consumer<MouseEvent>> pair : _events.values()) {
                pair.add(cb);
            }
        } else {
            Vector<Consumer<MouseEvent>> vector = new Vector<>();

            vector.add(cb);
            _events.put(event, vector);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        invokeEvents(MouseEventKind.MouseClick, me);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        invokeEvents(MouseEventKind.MousePress, me);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        invokeEvents(MouseEventKind.MouseRelease, me);
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        invokeEvents(MouseEventKind.MouseMove, me);
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        invokeEvents(MouseEventKind.MouseEnter, me);
        _hovered = true;
    }

    @Override
    public void mouseExited(MouseEvent me) {
        invokeEvents(MouseEventKind.MouseLeave, me);
        _hovered = false;
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        invokeEvents(MouseEventKind.MouseDrag, me);
    }

    private void invokeEvents(MouseEventKind mek, MouseEvent me) {
        if (_events.containsKey(mek)) {
            for (Consumer<MouseEvent> cb : _events.get(mek)) {
                cb.accept(me);
            }
        }
    }
}
