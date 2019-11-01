package com.virtutuile.afficheur.inputs;

import com.virtutuile.afficheur.swing.panels.VBorderedEventPanel;
import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.tools.ImageManipulator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;

public class VButton extends VBorderedEventPanel {
    private ImageIcon _icon;
    private JLabel _imgContainer;
    private JLabel _text;

    public VButton(String name) {
        this.setBackground(UIConstants.TOOLBAR_BACKGROUND);
        this.fixSize(UIConstants.BUTTON_SIZE);
        _content.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Creating the label and style it
        _text = new JLabel(name, SwingConstants.CENTER);
        Border textMargin = new EmptyBorder(3, 10, 10, 10);
        _text.setBorder(new CompoundBorder(_text.getBorder(), textMargin));
        _text.setForeground(UIConstants.TOOLBAR_FONT_COLOR);

        // Setting the layout of the button and add it's components
        this._content.setLayout(new BorderLayout());
        this.add(_text, BorderLayout.SOUTH);
        this.setMargin(5);
    }

    /**
     * Build a VButton. It's a combo of an icon and a text.
     *  @param name     The name of the button
     * @param icon The icon of the button
     */
    public VButton(String name, Image icon) {
        this(name);
        // Creating the image and style it
        _icon = ImageManipulator.Resize(new ImageIcon(icon), UIConstants.BUTTON_ICON_SIZE);
        _imgContainer = new JLabel(_icon);
        Border imgMargin = new EmptyBorder(10, 10, 3, 10);
        _imgContainer.setBorder(new CompoundBorder(_imgContainer.getBorder(), imgMargin));

        // Setting the layout of the button and add it's components
        this.add(_imgContainer, BorderLayout.CENTER);
    }

    /**
     * Force the button to be this size
     *
     * @param size The desired size
     */
    private void fixSize(Dimension size) {
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.setMinimumSize(size);
    }

    // region mouse button colors
    @Override
    public void mousePressed(MouseEvent me) {
        super.mousePressed(me);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        super.mouseReleased(me);
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        super.mouseEntered(me);
        repaint();
    }
    // endregion

    @Override
    public void mouseExited(MouseEvent me) {
        super.mouseExited(me);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        if (this._isMouseActive || this._isClicked) {
            setBackground(UIConstants.BUTTON_ACTIVE_COLOR);
//            setBorder(new CornerBorder());
//            setBorder(new CornerBorder(Color.white, 1));
        } else if (this._isMouseHover) {
            setBackground(UIConstants.BUTTON_HOVER_COLOR);
//            setBorder(new CornerBorder());
//            setBorder(new CornerBorder(Color.black, 1));
        } else {
            setBackground(UIConstants.BUTTON_NORMAL_COLOR);
//            setBorder(new EmptyBorder(1, 1, 1, 1));
        }
        super.paint(g);
    }
}
