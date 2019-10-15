package com.virtutuile.afficheur.components.inputs;

import com.virtutuile.system.constants.PhysicConstants;
import com.virtutuile.afficheur.wrap.VLabel;
import com.virtutuile.system.Validators;
import com.virtutuile.system.exeptions.ValidationException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;

public class VMetricInput extends VInput {

    int _value;
    VLabel _unitLabel = new VLabel();
    PhysicConstants.Units _unit;

    public VMetricInput(String label) {
        this(label, true);
    }

    public VMetricInput(String label, boolean horizontalFlex) {
        super(label, horizontalFlex);
        setUnit(PhysicConstants.Units.Centimeter);
        _unitLabel.setPreferredSize(new Dimension(50, _unitLabel.getPreferredSize().height));
        _unitLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        _fieldBorder.add(_unitLabel, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    @Override
    protected void validateInput(DocumentEvent documentEvent) {
        try {
            Validators.isNumber(_field.getText());
            setValid(true);
            _errorLabel.setText(" ");
        } catch (ValidationException except) {
            _errorLabel.setText(except.getMessage());
            setValid(false);
        }
    }

    public int getValue() {
        return _value;
    }

    public VMetricInput setValue(int value) {
        this._value = value;
        _field.setText(String.valueOf(_value));
        repaint();
        return this;
    }

    public PhysicConstants.Units getUnit() {
        return _unit;
    }

    public VMetricInput setUnit(PhysicConstants.Units unit) {
        this._unit = unit;
        this._unitLabel.setText(PhysicConstants.UnitLabels.get(unit));
        repaint();
        return this;
    }
}
