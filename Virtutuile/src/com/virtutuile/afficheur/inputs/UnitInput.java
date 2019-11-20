package com.virtutuile.afficheur.inputs;

import com.virtutuile.afficheur.swing.Label;
import com.virtutuile.afficheur.tools.ValidationsException;
import com.virtutuile.domaine.Constants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.function.BiConsumer;

public class UnitInput extends TextInput {

    int value;
    Label unitLabel = new Label();
    Constants.Units unit;

    public UnitInput(String label) {
        this(label, true);
    }

    public UnitInput(String label, boolean horizontalFlex) {
        super(label, horizontalFlex);
        setUnit(Constants.Units.Centimeter);
        unitLabel.setPreferredSize(new Dimension(50, unitLabel.getPreferredSize().height));
        unitLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fieldBorder.add(unitLabel, BorderLayout.EAST);
        setValidator(UnitInput::isDouble);
        revalidate();
        repaint();
    }

    public int getValue() {
        return value;
    }

    public UnitInput setValue(int value) {
        this.value = value;
        field.setText(String.valueOf(this.value));
        repaint();
        return this;
    }

    public Constants.Units getUnit() {
        return unit;
    }

    public UnitInput setUnit(Constants.Units unit) {
        this.unit = unit;
        this.unitLabel.setText(Constants.UnitLabels.get(unit));
        repaint();
        return this;
    }

    public static final boolean isNumber(String test) throws ValidationsException {
        try {
            Integer.parseInt(test);
        } catch (NumberFormatException except) {
            throw new ValidationsException("Bad number format");
        }
        return true;
    }

    public static final boolean isDouble(String test, TextInput input, BiConsumer<String, TextInput> next) throws ValidationsException {
        try {
            Double.parseDouble(test);
            next.accept(test, input);
        } catch (NumberFormatException except) {
            throw new ValidationsException("Bad number format");
        }
        return true;
    }
}
