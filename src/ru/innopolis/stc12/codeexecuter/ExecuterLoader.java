package ru.innopolis.stc12.codeexecuter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExecuterLoader extends ClassLoader {
    public ExecuterLoader(ClassLoader parent) {
        super(parent);
    }

    private byte[] getClassByteArray(String resource) {
        try (FileInputStream fileInputStream = new FileInputStream(resource);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            int data = fileInputStream.read();
            while (data != -1) {
                byteArrayOutputStream.write(data);
                data = fileInputStream.read();
            }

            fileInputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassByteArray(name + ".class");
        if (classData != null) {
            return defineClass(name, classData, 0, classData.length);
        }

        return super.loadClass(name);
    }
}
