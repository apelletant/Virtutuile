package com.virtutuile.graphics.components.inputs;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VInput extends JPanel {
    protected JLabel _label = new JLabel();
    protected JPanel _fieldContainer = new JPanel(new BorderLayout());
    protected JTextField _field = new JTextField();

    public VInput(String label, boolean horizontalFlex) {
        super(new BorderLayout());
        _label.setText(label);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        if (horizontalFlex)
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        _fieldContainer.add(_field, BorderLayout.CENTER);
        this.add(_label, BorderLayout.NORTH);
        this.add(_fieldContainer, BorderLayout.CENTER);
    }

    public VInput(String label) {
        this(label, true);
    }
}
