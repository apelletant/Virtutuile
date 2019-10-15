package com.virtutuile.afficheur.components.inputs;

import com.virtutuile.system.constants.UIConstants;
import com.virtutuile.afficheur.wrap.VLabel;
import com.virtutuile.afficheur.wrap.panels.VPanel;
import com.virtutuile.system.SimpleDocumentListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import java.awt.*;

public class VInput extends VPanel {
    protected VLabel _label = new VLabel();
    protected VPanel _fieldMargin = new VPanel();
    protected VPanel _fieldBorder = new VPanel(new BorderLayout());
    protected JTextField _field = new JTextField();
    protected VLabel _errorLabel = new VLabel();
    protected Border _validBorder = BorderFactory.createLineBorder(UIConstants.INPUT_COLOR, 1, false);
    protected Border _invalidBorder = BorderFactory.createLineBorder(UIConstants.INPUT_COLOR_INVALID, 1, false);
    protected boolean _isValid;

    public VInput(String label) {
        this(label, true);
    }

    public VInput(String label, boolean horizontalFlex) {
        super(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        if (horizontalFlex)
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        _label.setText(label);
        _errorLabel.setFontSize(10);
        _errorLabel.setForeground(UIConstants.INPUT_COLOR_INVALID);
        _errorLabel.setText(" ");

        _field.setLayout(new BorderLayout());
        _field.setBorder(new EmptyBorder(3, 8, 3, 8));
        _field.setOpaque(false);
        _field.setForeground(UIConstants.EDITIONPANEL_FONT_COLOR);
        _field.setCaretColor(UIConstants.EDITIONPANEL_FONT_COLOR);
        _field.getDocument().addDocumentListener((SimpleDocumentListener) this::validateInput);

        _fieldBorder.setBorder(_validBorder);
        _fieldBorder.add(_field);

        _fieldMargin.setBorder(new EmptyBorder(5, 5, 5, 5));
        _fieldMargin.setLayout(new BoxLayout(_fieldMargin, BoxLayout.PAGE_AXIS));
        _fieldMargin.add(_fieldBorder, BorderLayout.CENTER);
        this.add(_label, BorderLayout.NORTH);
        this.add(_fieldMargin, BorderLayout.CENTER);
        this.add(_errorLabel, BorderLayout.SOUTH);

        this.setBorder(new EmptyBorder(5, 7, 5, 7));
        this.setBackground(UIConstants.INPUT_BACKGROUND_LIGHTER);
        setOpaque(true);
    }

    protected void validateInput(DocumentEvent documentEvent) {
    }

    public String getText() {
        return _field.getText();
    }

    public VInput setText(String text) {
        _field.setText(text);
        return this;
    }

    public boolean isValid() {
        return _isValid;
    }

    public VInput setValid(boolean validity) {
        _isValid = validity;
        _fieldBorder.setBorder(_isValid ? _validBorder : _invalidBorder);
        repaint();
        return this;
    }
}
