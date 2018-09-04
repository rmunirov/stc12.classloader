package ru.innopolis.stc12.classloader;

import java.io.IOException;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {

        try {
            Process process = new ProcessBuilder("C://Program Files//Java//jdk1.8.0_181//bin/javac.exe", "D://Projects//java//classloaders//src//ru//innopolis//stc12//classloader/HelloWorld.java").start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Human humanProxy = (Human) Proxy.newProxyInstance(EuropeanHuman.class.getClassLoader(),
                new Class[]{Human.class}, new HumanInvoker());
        System.out.println(humanProxy.saySmth());
    }
}