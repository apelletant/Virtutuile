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

    public UndoRedo(Meta meta) {
        undoStack = new Stack<>();
        redoStack = new Stack<>();

        undoStack.push(objectToOIS(meta));
    }

    public void pushChange(Meta meta) {
        ObjectInputStream baos = objectToOIS(meta);
        if (baos != null) {
            undoStack.push(baos);
            if(undoStack.size() == 2 ) {
//                System.out.println("undo first time");
                changeUndoStatus();
            }
//            System.out.println("undo the other time");
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

    public void undo() {
        if (canUndo == true) {
//            System.out.println("undo");
        }
    }

    public void redo() {
        if (canRedo != true) {
//            System.out.println("redo");
        }
    }

    private void changeUndoStatus() {
        canUndo = !canUndo;
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

    private Meta OISToMeta(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        return (Meta) ois.readObject();
    }
}
