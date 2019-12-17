package com.virtutuile.domaine;

import com.virtutuile.domaine.entities.Meta;

import java.io.*;
import java.util.Stack;

public class UndoRedo {
    private Meta meta;
    private Stack<ObjectInputStream> undoStack;
    private Stack<ObjectInputStream> redoStack;

    public UndoRedo(Meta originMeta) {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        meta = originMeta;
        undoStack.push(objectToOIS(meta));
    }

    public void addUndo(Meta meta) {
        ObjectInputStream ois = objectToOIS(meta);

        if (ois != null) {
//            System.out.println("\nDoing:");
//            System.out.println(meta.getDoing());
//            System.out.println("LastEVent:");
//            System.out.println(meta.getLastEvent());
//            System.out.println("added to undo");
//            System.out.println(meta.getDoing());
            undoStack.push(ois);
        }

        try {
            ois.close();
        } catch (IOException e) {
            System.out.println(e);
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

        if (undoStack.size() >= 1) {
            ObjectInputStream state = undoStack.pop();

            if (state != null) {
                newItem = OISToMeta(state);

//                meta.setMeta(newItem);
                addToRedo(objectToOIS(newItem));
            }
            try {
                state.close();
            } catch (IOException e) {
                System.out.println(e);
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
                    addUndo(newItem);
                    return (newItem);
                }
            }

            try {
                state.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }

    private void addToRedo(ObjectInputStream ios) {
        redoStack.push(ios);
    }

    private Meta OISToMeta(ObjectInputStream ois) {
        try {
            Meta tmp = (Meta) ois.readObject();

            if (tmp == null) {
                System.out.println("null");
                return null;
            }
            return tmp;
        } catch (IOException e) {
            System.out.println("io");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("not found");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("other");
            e.printStackTrace();
            return null;
        }
    }
}
