package com.virtutuile.afficheur.inputs;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.swing.Label;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.InputEventKind;
import com.virtutuile.afficheur.tools.ValidationsException;
import com.virtutuile.shared.TriConsumer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;
import java.util.function.BiConsumer;

public class TextInput extends Panel implements DocumentListener {
    protected Label label = new Label();
    protected Panel fieldMargin = new Panel();
    protected Panel fieldBorder = new Panel(new BorderLayout());
    protected JTextField field = new JTextField();
    protected Label errorLabel = new Label();
    protected Border validBorder = BorderFactory.createLineBorder(Constants.INPUT_COLOR, 1, false);
    protected Border invalidBorder = BorderFactory.createLineBorder(Constants.INPUT_COLOR_INVALID, 1, false);
    protected boolean isValid;
    protected HashMap<InputEventKind, Vector<BiConsumer<String, TextInput>>> events = new HashMap<>();
    protected BiConsumer<String, TextInput> inputValidator = (a, b) -> {
        invoke(InputEventKind.OnChange);
    };

    public TextInput(String label) {
        this(label, true, null);
    }

    public TextInput(String label, boolean horizontalFlex, String validator) {
        super(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        if (horizontalFlex)
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        this.label.setText(label);
        errorLabel.setFontSize(10);
        errorLabel.setForeground(Constants.INPUT_COLOR_INVALID);
        errorLabel.setText(" ");

        if (validator != null) {
            switch (validator) {
                case "integer":
                    setValidator(UnitInput::isInteger);
                    break;
                case "double":
                    setValidator(UnitInput::isDouble);
                    break;
                case "doubleInf":
                    setValidator(UnitInput::isDoubleInf);
                    break;
                default:
                    break;
            }
        }

        field.setLayout(new BorderLayout());
        field.setBorder(new EmptyBorder(3, 8, 3, 8));
        field.setOpaque(false);
        field.setForeground(Constants.EDITIONPANEL_FONT_COLOR);
        field.setCaretColor(Constants.EDITIONPANEL_FONT_COLOR);
        field.getDocument().addDocumentListener(this);

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

    public TextInput setValidator(TriConsumer<String, TextInput, BiConsumer<String, TextInput>>... validators) {

        for (int i = 0; i < validators.length; ++i) {
            TriConsumer<String, TextInput, BiConsumer<String, TextInput>> validator = validators[i];

            BiConsumer<String, TextInput> finalStart = inputValidator;
            inputValidator = (a, b) -> {
                try {
                    validator.apply(a, b, finalStart);
                    setValid(true);
                    errorLabel.setText(" ");
                } catch (ValidationsException except) {
                    setValid(false);
                    errorLabel.setText(except.getMessage());
                }
            };
        }

        return this;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        inputValidator.accept(getText(), this);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        inputValidator.accept(getText(), this);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        inputValidator.accept(getText(), this);
    }

    public void addInputListener(InputEventKind evt, BiConsumer<String, TextInput> callback) {
        if (!events.containsKey(evt)) {
            events.put(evt, new Vector<>());
        }
        events.get(evt).add(callback);
    }

    private void invoke(InputEventKind evt) {
        if (events.containsKey(evt)) {
            for (BiConsumer<String, TextInput> consumer : events.get(evt)) {
                consumer.accept(field.getText(), this);
            }
        }
    }

    public void setEditableFalse() {
        field.setEditable(false);
    }


    public static final boolean isInteger(String test, TextInput input, BiConsumer<String, TextInput> next) throws ValidationsException {
        try {
            Integer value = Integer.parseInt(test);
            if (value <= 0 ) {
                throw new ValidationsException("Must be > 0");
            }
            next.accept(test, input);
        } catch (NumberFormatException except) {
            throw new ValidationsException("Bad number format");
        }
        return true;
    }


    public static final boolean isDoubleInf(String test, TextInput input, BiConsumer<String, TextInput> next) throws ValidationsException {
        try {
            Double value = Double.parseDouble(test);
            if (value.isNaN()) {
                throw new ValidationsException("Must be a number");
            }
            next.accept(test, input);
        } catch (NumberFormatException except) {
            throw new ValidationsException("Bad number format");
        }
        return true;
    }


    public static final boolean isDouble(String test, TextInput input, BiConsumer<String, TextInput> next) throws ValidationsException {
        try {
            Double value = Double.parseDouble(test);
            if (value.isNaN() || value.isInfinite() || value <= 0 ) {
                throw new ValidationsException("NaN, infinite or smaller than / equal to 0 is not accepted");
            }
            next.accept(test, input);
        } catch (NumberFormatException except) {
            throw new ValidationsException("Bad number format");
        }
        return true;
    }
}
