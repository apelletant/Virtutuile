package com.virtutuile.graphics.components.inputs;

import com.virtutuile.constants.PhysicConstants;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class VDistanceInput extends VInput {

    int _value;
    JLabel _unitLabel = new JLabel();
    PhysicConstants.Units _unit;

    public VDistanceInput(String label, boolean horizontalFlex) {
        super(label, horizontalFlex);
        setUnit(PhysicConstants.Units.Centimeter);
        _unitLabel.setPreferredSize(new Dimension(50, _unitLabel.getPreferredSize().height));
        _fieldContainer.add(_unitLabel, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    public VDistanceInput(String label) {
        this(label, true);
    }

    public int getValue() {
        return _value;
    }

    public VDistanceInput setValue(int value) {
        this._value = value;
        _field.setText(String.valueOf(_value));
        repaint();
        return this;
    }

    public PhysicConstants.Units getUnit() {
        return _unit;
    }

    public VDistanceInput setUnit(PhysicConstants.Units unit) {
        this._unit = unit;
        this._unitLabel.setText(PhysicConstants.UnitLabels.get(unit));
        repaint();
        return this;
    }
}
