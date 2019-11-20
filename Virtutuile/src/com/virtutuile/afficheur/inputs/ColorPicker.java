package com.virtutuile.afficheur.inputs;

import com.virtutuile.afficheur.swing.events.InputEventKind;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;
import java.util.function.BiConsumer;

public class ColorPicker extends JColorChooser implements DocumentListener {
    protected HashMap<InputEventKind, Vector<BiConsumer<Color, ColorPicker>>> events = new HashMap<>();

    @Override
    public void insertUpdate(DocumentEvent e) {
        invoke(InputEventKind.OnChange);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        invoke(InputEventKind.OnChange);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        invoke(InputEventKind.OnChange);
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


}
