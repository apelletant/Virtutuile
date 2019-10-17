package com.virtutuile.afficheur.swing.panels;


import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Vector;
import java.util.function.Consumer;

public class VBorderedEventPanel extends VBorderedPanel implements MouseInputListener, MouseMotionListener {

    protected boolean _isClicked = false;
    protected boolean _isMouseActive = false;
    protected boolean _isMouseHover = false;

    HashMap<MouseEventKind, Vector<Consumer<MouseEvent>>> _events = new HashMap<>();

    public VBorderedEventPanel() {
        super();
        _border.addMouseListener(this);
        _border.addMouseMotionListener(this);
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
        _isMouseHover = true;
    }

    @Override
    public void mouseExited(MouseEvent me) {
        invokeEvents(MouseEventKind.MouseLeave, me);
        _isMouseHover = false;
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

}
