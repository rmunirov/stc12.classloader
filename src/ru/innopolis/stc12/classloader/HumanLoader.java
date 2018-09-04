package ru.innopolis.stc12.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class HumanLoader extends ClassLoader {
    public HumanLoader(ClassLoader parent) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.equals("ru.innopolis.stc12.classloader.EuropeanHuman")) {
            byte[] classData = getClassByteArray("C://tmp/EuropeanHuman.class");
            return defineClass(name, classData, 0, classData.length);
        }

        return super.loadClass(name);
    }
}