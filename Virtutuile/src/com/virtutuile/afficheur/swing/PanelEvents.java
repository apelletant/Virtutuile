package com.virtutuile.afficheur.swing;

import com.virtutuile.afficheur.swing.events.KeyboardEventKind;
import com.virtutuile.afficheur.swing.events.MouseEventKind;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Vector;
import java.util.function.Consumer;

public class PanelEvents extends Panel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    protected boolean isClicked;
    protected boolean isMouseActive;
    protected boolean isMouseHover;

    private HashMap<MouseEventKind, Vector<Consumer<MouseEvent>>> _mouseEvents = new HashMap<>();
    private HashMap<KeyboardEventKind, Vector<Consumer<KeyEvent>>> _keyboardEvents = new HashMap<>();

    public PanelEvents(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
    }

    public PanelEvents(LayoutManager layout) {
        super(layout);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
    }

    public PanelEvents(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
    }

    public PanelEvents() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
    }

    public boolean isActive() {
        return this.isMouseActive;
    }

    public void setActive(boolean isActive) {
        this.isMouseActive = isActive;
    }

    /**
     * Adds a listener to a specific event.
     *
     * @param event The event to add
     * @param cb    The event to call
     */
    public void addMouseEventListener(MouseEventKind event, Consumer<MouseEvent> cb) {

        if (_mouseEvents.containsKey(event)) {
            for (Vector<Consumer<MouseEvent>> pair : _mouseEvents.values()) {
                pair.add(cb);
            }
        } else {
            Vector<Consumer<MouseEvent>> vector = new Vector<>();

            vector.add(cb);
            _mouseEvents.put(event, vector);
        }
    }

    /**
     * Adds a listener to a specific event.
     *
     * @param event The event to add
     * @param cb    The event to call
     */
    public void addKeyboardEventListener(KeyboardEventKind event, Consumer<KeyEvent> cb) {

        if (_keyboardEvents.containsKey(event)) {
            for (Vector<Consumer<KeyEvent>> pair : _keyboardEvents.values()) {
                pair.add(cb);
            }
        } else {
            Vector<Consumer<KeyEvent>> vector = new Vector<>();

            vector.add(cb);
            _keyboardEvents.put(event, vector);
        }
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

    private void invokeMouseEvent(MouseEventKind mek, MouseEvent me) {
        if (_mouseEvents.containsKey(mek)) {
            for (Consumer<MouseEvent> cb : _mouseEvents.get(mek)) {
                cb.accept(me);
            }
        }
    }

    private void invokeKeyboardEvent(KeyboardEventKind kek, KeyEvent ke) {
        if (_keyboardEvents.containsKey(kek)) {
            for (Consumer<KeyEvent> cb : _keyboardEvents.get(kek)) {
                cb.accept(ke);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getButton() == MouseEvent.BUTTON1) {
            invokeMouseEvent(MouseEventKind.MouseLClick, me);
        } else if (me.getButton() == MouseEvent.BUTTON2) {
            invokeMouseEvent(MouseEventKind.MouseRClick, me);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        isClicked = true;
        invokeMouseEvent(MouseEventKind.MousePress, me);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        isClicked = false;
        invokeMouseEvent(MouseEventKind.MouseRelease, me);
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        invokeMouseEvent(MouseEventKind.MouseEnter, me);
        isMouseHover = true;
    }

    @Override
    public void mouseExited(MouseEvent me) {
        invokeMouseEvent(MouseEventKind.MouseLeave, me);
        isMouseHover = false;
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        invokeMouseEvent(MouseEventKind.MouseDrag, me);
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        invokeMouseEvent(MouseEventKind.MouseMove, me);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        invokeMouseEvent(MouseEventKind.MouseWheel, mwe);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        invokeKeyboardEvent(KeyboardEventKind.KeyTyped, e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        invokeKeyboardEvent(KeyboardEventKind.KeyPressed, e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        invokeKeyboardEvent(KeyboardEventKind.KeyReleased, e);

    }
}
