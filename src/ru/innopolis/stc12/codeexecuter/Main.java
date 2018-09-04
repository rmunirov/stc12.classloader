package ru.innopolis.stc12.codeexecuter;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {

        Executer executerProxy = (Executer) Proxy.newProxyInstance(ExecuterMessage.class.getClassLoader(), new Class[]{Executer.class}, new ExecuterProxy());

        String code = "public void getMessage() {System.out.println(\"It's work!\");}";

        executerProxy.execute(code);
    }
}
