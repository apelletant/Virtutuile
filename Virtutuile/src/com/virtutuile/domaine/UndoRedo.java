package com.virtutuile.domaine;

import com.virtutuile.domaine.entities.Meta;

import java.io.*;
import java.util.Stack;

public class UndoRedo {
    private Meta meta;
    private Boolean canUndo = false;
    private Boolean canRedo = false;

    private Stack<ObjectInputStream> undoStack;
    private Stack<ObjectInputStream> redoStack;

    private static boolean firstMouve = true;

    public UndoRedo(Meta originMeta) {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        meta = originMeta;
    }

    public void addUndo(Meta meta) {
        ObjectInputStream ois = objectToOIS(meta);

        if (ois != null) {
            System.out.println("add undo stack");
            undoStack.push(ois);
        }
    }

    private ObjectInputStream objectToOIS(Meta meta) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(meta);
            out.close();

            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
            return in;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Meta undo() {
        Meta newItem = null;

        if (undoStack.size() > 1) {
            System.out.println("undo1");
            ObjectInputStream state = undoStack.pop();

            if (state != null) {
                System.out.println("undo2");
                newItem = OISToMeta(state);

                meta.setMeta(newItem);
                addToRedo(objectToOIS(newItem));
            }
        }
        return newItem;
    }

    public Meta redo() {
        Meta newItem = null;

        if (redoStack.size() > 0) {
            ObjectInputStream state = redoStack.pop();

            if (state != null) {
                newItem = OISToMeta(state);
                if (newItem != null) {
                    meta.setMeta(newItem);
                }
            }
        }
        return newItem;
    }

    private void addToRedo(ObjectInputStream ios) {
        redoStack.push(ios);
    }

    private void changeUndoStatus() {
        canUndo = !canUndo;
        firstMouve = !firstMouve;
    }

    private void changeRedoStatus() {
        canRedo = !canRedo;
    }

    private Meta binaryToMeta() {

        return new Meta();
    }

    private byte[] metaToBynary() {
        return null;
    }

    public boolean canUndo() {
        return canUndo;
    }

    public boolean canRedo() {
        return canRedo;
    }

    private Meta OISToMeta(ObjectInputStream ois) {
        try {
            return (Meta) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
