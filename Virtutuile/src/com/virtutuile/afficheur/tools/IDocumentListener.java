package com.virtutuile.afficheur.tools;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@FunctionalInterface
public
interface IDocumentListener extends DocumentListener {
    
    @Override
    default void insertUpdate(DocumentEvent e) {
        update(e);
    }

    void update(DocumentEvent e);

    @Override
    default void removeUpdate(DocumentEvent e) {
        update(e);
    }

    @Override
    default void changedUpdate(DocumentEvent e) {
        update(e);
    }
}
