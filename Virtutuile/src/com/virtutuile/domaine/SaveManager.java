package com.virtutuile.domaine;

import com.virtutuile.domaine.entities.Meta;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveManager {
    private Meta meta;

    public SaveManager(Meta meta) {
        this.meta = meta;
    }

    public void saveCanvas(String path) {
        path = verifyFileExtention(path);
        System.out.println(path);

        writeInFile(path);
    }

    private void writeInFile(String path) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this.meta);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Meta loadCanvas(String path) {
        path = verifyFileExtention(path);

        try {

            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();
            objectIn.close();
            meta = (Meta) obj;
            System.out.println(meta.getGridSize());
            return meta;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String verifyFileExtention (String path) {
        if (path.contains(".")) {
            String[] splitedPath = path.split(".");
            if (splitedPath.length > 1 && splitedPath[1] != Constants.Gizmos.SaveManagerConstant.EXTENTION) {
                path = splitedPath[0] + "." + Constants.Gizmos.SaveManagerConstant.EXTENTION;
            }
        } else {
            path += "." + Constants.Gizmos.SaveManagerConstant.EXTENTION;
        }
        return path;
    }
}
