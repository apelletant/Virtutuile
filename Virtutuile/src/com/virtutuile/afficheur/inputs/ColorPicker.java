package com.virtutuile.afficheur.inputs;

import com.virtutuile.afficheur.swing.events.InputEventKind;

import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;
import java.util.function.BiConsumer;

public class ColorPicker extends JColorChooser implements ChangeListener {
    protected HashMap<InputEventKind, Vector<BiConsumer<Color, ColorPicker>>> events = new HashMap<>();

    public ColorPicker() {
        getSelectionModel().addChangeListener(this);
    }

    public ColorPicker(Color initialColor) {
        super(initialColor);
        getSelectionModel().addChangeListener(this);
    }

    public ColorPicker(ColorSelectionModel model) {
        super(model);
        getSelectionModel().addChangeListener(this);
    }

    public void addInputListener(InputEventKind evt, BiConsumer<Color, ColorPicker> callback) {
        if (!events.containsKey(evt)) {
            events.put(evt, new Vector<>());
        }
        events.get(evt).add(callback);
    }

    private void invoke(InputEventKind evt) {
        if (events.containsKey(evt)) {
            for (BiConsumer<Color, ColorPicker> consumer : events.get(evt)) {
                consumer.accept(getColor(), this);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        invoke(InputEventKind.OnChange);
    }
}
