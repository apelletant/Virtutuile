package com.virtutuile.afficheur.swing;

import com.virtutuile.afficheur.swing.events.KeyboardEventKind;
import com.virtutuile.afficheur.swing.events.MouseEventKind;

import javax.swing.event.MouseInputListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Vector;
import java.util.function.Consumer;

public class BorderedEventPanel extends BorderedPanel implements MouseInputListener, MouseMotionListener, KeyListener {

    protected boolean isClicked = false;
    protected boolean isMouseActive = false;
    protected boolean isMouseHover = false;
    private HashMap<MouseEventKind, Vector<Consumer<MouseEvent>>> mouseEvents = new HashMap<>();
    private HashMap<KeyboardEventKind, Vector<Consumer<KeyEvent>>> keyboardEvents = new HashMap<>();

    public BorderedEventPanel() {
        super();
        border.addMouseListener(this);
        border.addKeyListener(this);
        border.addMouseMotionListener(this);
    }

    private void invokeMouseEvent(MouseEventKind mek, MouseEvent me) {
        if (mouseEvents.containsKey(mek)) {
            for (Consumer<MouseEvent> cb : mouseEvents.get(mek)) {
                cb.accept(me);
            }
        }
    }

    private void invokeKeyboardEvent(KeyboardEventKind kek, KeyEvent ke) {
        if (keyboardEvents.containsKey(kek)) {
            for (Consumer<KeyEvent> cb : keyboardEvents.get(kek)) {
                cb.accept(ke);
            }
        }
    }

    public boolean isActive() {
        return isMouseActive;
    }

    public void setActive(boolean isActive) {
        isMouseActive = isActive;
    }

    /**
     * Adds a listener to a specific event.
     *
     * @param event The event to add
     * @param cb    The event to call
     */
    public void addMouseEventListener(MouseEventKind event, Consumer<MouseEvent> cb) {

        if (mouseEvents.containsKey(event)) {
            for (Vector<Consumer<MouseEvent>> pair : mouseEvents.values()) {
                pair.add(cb);
            }
        } else {
            Vector<Consumer<MouseEvent>> vector = new Vector<>();
            vector.add(cb);
            mouseEvents.put(event, vector);
        }
    }

    /**
     * Adds a listener to a specific event.
     *
     * @param event The event to add
     * @param cb    The event to call
     */
    public void addKeyboardEventListener(KeyboardEventKind event, Consumer<KeyEvent> cb) {

        if (keyboardEvents.containsKey(event)) {
            for (Vector<Consumer<KeyEvent>> pair : keyboardEvents.values()) {
                pair.add(cb);
            }
        } else {
            Vector<Consumer<KeyEvent>> vector = new Vector<>();
            vector.add(cb);
            keyboardEvents.put(event, vector);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        isClicked = true;
        if (me.getButton() == MouseEvent.BUTTON1) {
            invokeMouseEvent(MouseEventKind.MouseLClick, me);
        } else if (me.getButton() == MouseEvent.BUTTON2) {
            invokeMouseEvent(MouseEventKind.MouseRClick, me);
        }
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
