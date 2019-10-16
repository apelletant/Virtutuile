package com.virtutuile.afficheur.components.inputs;

import com.virtutuile.systeme.constants.VPhysicsConstants;
import com.virtutuile.afficheur.wrap.VLabel;
import com.virtutuile.systeme.tools.Validators;
import com.virtutuile.systeme.tools.ValidationsException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;

public class VMetricInput extends VInput {

    int _value;
    VLabel _unitLabel = new VLabel();
    VPhysicsConstants.Units _unit;

    public VMetricInput(String label) {
        this(label, true);
    }

    public VMetricInput(String label, boolean horizontalFlex) {
        super(label, horizontalFlex);
        setUnit(VPhysicsConstants.Units.Centimeter);
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
        } catch (ValidationsException except) {
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

    public VPhysicsConstants.Units getUnit() {
        return _unit;
    }

    public VMetricInput setUnit(VPhysicsConstants.Units unit) {
        this._unit = unit;
        this._unitLabel.setText(VPhysicsConstants.UnitLabels.get(unit));
        repaint();
        return this;
    }
}
