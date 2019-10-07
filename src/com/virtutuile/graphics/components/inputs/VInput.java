package com.virtutuile.graphics.components.inputs;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class VInput extends JPanel {
    private JTextField _field = new JTextField();
    private JLabel _label = new JLabel();

    public VInput(String label) {
        _label.setText(label);

        this.setLayout(new BorderLayout());
        this.add(_label, BorderLayout.NORTH);
        this.add(_field, BorderLayout.CENTER);
    }

}
