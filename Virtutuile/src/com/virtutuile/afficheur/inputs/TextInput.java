package com.virtutuile.afficheur.inputs;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.swing.Label;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.tools.IDocumentListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import java.awt.*;

public class TextInput extends Panel {
    protected Label label = new Label();
    protected Panel fieldMargin = new Panel();
    protected Panel fieldBorder = new Panel(new BorderLayout());
    protected JTextField field = new JTextField();
    protected Label errorLabel = new Label();
    protected Border validBorder = BorderFactory.createLineBorder(Constants.INPUT_COLOR, 1, false);
    protected Border invalidBorder = BorderFactory.createLineBorder(Constants.INPUT_COLOR_INVALID, 1, false);
    protected boolean isValid;

    public TextInput(String label) {
        this(label, true);
    }

    public TextInput(String label, boolean horizontalFlex) {
        super(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        if (horizontalFlex)
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        this.label.setText(label);
        errorLabel.setFontSize(10);
        errorLabel.setForeground(Constants.INPUT_COLOR_INVALID);
        errorLabel.setText(" ");

        field.setLayout(new BorderLayout());
        field.setBorder(new EmptyBorder(3, 8, 3, 8));
        field.setOpaque(false);
        field.setForeground(Constants.EDITIONPANEL_FONT_COLOR);
        field.setCaretColor(Constants.EDITIONPANEL_FONT_COLOR);
        field.getDocument().addDocumentListener((IDocumentListener) this::validateInput);

        fieldBorder.setBorder(validBorder);
        fieldBorder.add(field);

        fieldMargin.setBorder(new EmptyBorder(5, 5, 5, 5));
        fieldMargin.setLayout(new BoxLayout(fieldMargin, BoxLayout.PAGE_AXIS));
        fieldMargin.add(fieldBorder, BorderLayout.CENTER);
        this.add(this.label, BorderLayout.NORTH);
        this.add(fieldMargin, BorderLayout.CENTER);
        this.add(errorLabel, BorderLayout.SOUTH);

        this.setBorder(new EmptyBorder(5, 7, 5, 7));
        this.setBackground(Constants.SUBPANEL_BACKGROUND);
        setOpaque(true);
    }

    protected void validateInput(DocumentEvent documentEvent) {
    }

    public String getText() {
        return field.getText();
    }

    public TextInput setText(String text) {
        field.setText(text);
        return this;
    }

    public boolean isValid() {
        return isValid;
    }

    public TextInput setValid(boolean validity) {
        isValid = validity;
        fieldBorder.setBorder(isValid ? validBorder : invalidBorder);
        repaint();
        return this;
    }
}
