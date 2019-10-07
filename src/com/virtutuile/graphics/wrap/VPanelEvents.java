package com.virtutuile.graphics.wrap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Vector;
import java.util.function.Consumer;

public class VPanelEvents extends JPanel implements MouseListener, MouseMotionListener {
    protected boolean _isClicked = false;
    protected boolean _isMouseActive = false;
    protected boolean _isMouseOver = false;

    HashMap<MouseEventKind, Vector<Consumer<MouseEvent>>> _events = new HashMap<>();

    public VPanelEvents(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public VPanelEvents(LayoutManager layout) {
        super(layout);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public VPanelEvents(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public VPanelEvents() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public boolean isActive() {
        return this._isMouseActive;
    }

    public void setActive(boolean isActive) {
        this._isMouseActive = isActive;
    }

    /**
     * Adds a listener to a specific event.
     *
     * @param event The event to add
     * @param cb    The event to call
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
        if (me.getButton() == MouseEvent.BUTTON1) {
            invokeEvents(MouseEventKind.MouseLClick, me);
        } else if (me.getButton() == MouseEvent.BUTTON2) {
            invokeEvents(MouseEventKind.MouseRClick, me);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        _isClicked = true;
        invokeEvents(MouseEventKind.MousePress, me);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        _isClicked = false;
        invokeEvents(MouseEventKind.MouseRelease, me);
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        invokeEvents(MouseEventKind.MouseEnter, me);
        _isMouseOver = true;
    }

    @Override
    public void mouseExited(MouseEvent me) {
        invokeEvents(MouseEventKind.MouseLeave, me);
        _isMouseOver = false;
    }

    private void invokeEvents(MouseEventKind mek, MouseEvent me) {
        if (_events.containsKey(mek)) {
            for (Consumer<MouseEvent> cb : _events.get(mek)) {
                cb.accept(me);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        invokeEvents(MouseEventKind.MouseDrag, me);
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        invokeEvents(MouseEventKind.MouseMove, me);
    }

    /**
     * Force the button to be this size
     *
     * @param size The desired size
     */
    public void fixSize(Dimension size) {
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.setMinimumSize(size);
    }
}
