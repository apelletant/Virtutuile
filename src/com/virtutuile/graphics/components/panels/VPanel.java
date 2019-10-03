package com.virtutuile.graphics.components.panels;

import com.virtutuile.graphics.wrap.MouseEventKind;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.Vector;
import java.util.function.Consumer;

public class VPanel extends JPanel implements MouseListener, MouseMotionListener {
    protected boolean _is_active = false;
    protected boolean _hovered = false;
    private int eventCnt = 0;
    java.util.Timer timer = new java.util.Timer("doubleClickTimer", false);

    HashMap<MouseEventKind, Vector<Consumer<MouseEvent>>> _events = new HashMap<>();

    public VPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public VPanel(LayoutManager layout) {
        super(layout);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public VPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public VPanel() {
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
        eventCnt = me.getClickCount();
        if ( me.getClickCount() == 1 ) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if ( eventCnt == 1 ) {
                        invokeEvents(MouseEventKind.MouseClick, me);
                    } else if ( eventCnt > 1 ) {
                        invokeEvents(MouseEventKind.MouseDbClick, me);
                    }
                    eventCnt = 0;
                }
            }, 400);
        }
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

    /**
     * Force the button to be this size
     * @param size The desired size
     */
    public void fixSize(Dimension size) {
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.setMinimumSize(size);
    }

    private void invokeEvents(MouseEventKind mek, MouseEvent me) {
        if (_events.containsKey(mek)) {
            for (Consumer<MouseEvent> cb : _events.get(mek)) {
                cb.accept(me);
            }
        }
    }

    private Integer dbClickInterval() {
        Number obj = (Number) Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");
        return (obj != null) ? obj.intValue() : 300;
    }
}
