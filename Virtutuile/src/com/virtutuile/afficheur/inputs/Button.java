package com.virtutuile.afficheur.inputs;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.swing.BorderedEventPanel;
import com.virtutuile.afficheur.tools.ImageManipulator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Button extends BorderedEventPanel {
    private ImageIcon icon;
    private JLabel imageContainer;
    private JLabel text;

    public Button(String name) {
        setBackground(Constants.TOOLBAR_BACKGROUND);
        fixSize(Constants.BUTTON_SIZE);
        /*content.setCursor(new Cursor(Cursor.HAND_CURSOR));*/

        // Creating the label and style it
        text = new JLabel(name, SwingConstants.CENTER);
        Border textMargin = new EmptyBorder(3, 10, 10, 10);
        text.setBorder(new CompoundBorder(text.getBorder(), textMargin));
        text.setForeground(Constants.TOOLBAR_FONT_COLOR);

        // Setting the layout of the button and add it's components
        content.setLayout(new BorderLayout());
        add(text, BorderLayout.SOUTH);
        setMargin(5);
    }

    /**
     * Build a VButton. It's a combo of an icon and a text.
     *  @param name     The name of the button
     * @param icon The icon of the button
     */
    public Button(String name, Image icon) {
        this(name);
        // Creating the image and style it
        this.icon = ImageManipulator.Resize(new ImageIcon(icon), Constants.BUTTON_ICON_SIZE);
        imageContainer = new JLabel(this.icon);
        Border imgMargin = new EmptyBorder(10, 10, 3, 10);
        imageContainer.setBorder(new CompoundBorder(imageContainer.getBorder(), imgMargin));

        // Setting the layout of the button and add it's components
        add(imageContainer, BorderLayout.CENTER);
    }

    /**
     * Force the button to be this size
     *
     * @param size The desired size
     */
    public void fixSize(Dimension size) {
        setMaximumSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
    }

    // region mouse button colors
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        super.mousePressed(mouseEvent);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        super.mouseReleased(mouseEvent);
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        super.mouseEntered(mouseEvent);
        repaint();
    }
    // endregion

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        super.mouseExited(mouseEvent);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        if (isMouseActive || isClicked) {
            setBackground(Constants.BUTTON_ACTIVE_COLOR);
//            setBorder(new CornerBorder());
//            setBorder(new CornerBorder(Color.white, 1));
        } else if (isMouseHover) {
            setBackground(Constants.BUTTON_HOVER_COLOR);
//            setBorder(new CornerBorder());
//            setBorder(new CornerBorder(Color.black, 1));
        } else {
            setBackground(Constants.BUTTON_NORMAL_COLOR);
//            setBorder(new EmptyBorder(1, 1, 1, 1));
        }
        super.paint(g);
    }
}
