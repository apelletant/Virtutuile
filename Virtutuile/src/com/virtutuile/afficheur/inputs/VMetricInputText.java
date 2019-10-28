package com.virtutuile.afficheur.inputs;

import com.virtutuile.afficheur.swing.tools.VLabel;
import com.virtutuile.domaine.systeme.constants.VPhysicsConstants;
import com.virtutuile.domaine.systeme.tools.ValidationsException;
import com.virtutuile.domaine.systeme.tools.Validators;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;

public class VMetricInputText extends VInputText {

    int _value;
    VLabel _unitLabel = new VLabel();
    VPhysicsConstants.Units _unit;

    public VMetricInputText(String label) {
        this(label, true);
    }

    public VMetricInputText(String label, boolean horizontalFlex) {
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

    public VMetricInputText setValue(int value) {
        this._value = value;
        _field.setText(String.valueOf(_value));
        repaint();
        return this;
    }

    public VPhysicsConstants.Units getUnit() {
        return _unit;
    }

    public VMetricInputText setUnit(VPhysicsConstants.Units unit) {
        this._unit = unit;
        this._unitLabel.setText(VPhysicsConstants.UnitLabels.get(unit));
        repaint();
        return this;
    }
}
