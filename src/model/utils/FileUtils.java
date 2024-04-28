package model.utils;

import java.io.*;

public class FileUtils {

    public static void writeObject(String filepath, Object obj) throws IOException {
        File file = new File(filepath);
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            out.writeObject(obj);
            out.flush();
        }
    }

    public static Object readObject(File file) throws IOException, ClassNotFoundException {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            return in.readObject();
        }
    }

}
