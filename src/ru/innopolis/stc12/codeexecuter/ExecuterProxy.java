package ru.innopolis.stc12.codeexecuter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ExecuterProxy implements InvocationHandler {
    private final String CLASS_NAME = "ExecuteClass";

    public ExecuterProxy() {
    }

    private String createClass(String code) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(CLASS_NAME + ".java")) {

            String classStart = "public class " + CLASS_NAME + " {";
            String classEnd = "}";

            fileOutputStream.write(classStart.getBytes());
            fileOutputStream.write(code.getBytes());
            fileOutputStream.write(classEnd.getBytes());

            return CLASS_NAME + ".java";
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String compileClass(String path) {
        try {
            String javac = System.getenv("JAVA_HOME") + "//bin//" + "javac.exe";
            Process process = new ProcessBuilder(javac, path).start();  //TODO path it's worked, but how?
            if (process.waitFor() == 0) {
                return CLASS_NAME + ".class";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().compareTo("execute") == 0) {
            String classJava = createClass((String) args[0]);
            if (classJava != null) {
                if (compileClass(classJava) != null) {
                    ExecuterLoader executerLoader = new ExecuterLoader(proxy.getClass().getClassLoader());
                    Class executeClass = executerLoader.loadClass(CLASS_NAME);
                    Method[] executeMethods = executeClass.getMethods();
                    for (Method methodToExecute : executeMethods) {
                        if (methodToExecute.getName() != CLASS_NAME) {
                            return methodToExecute.invoke(executeClass.newInstance(), null);    //TODO how pass parametrs?
                        }
                    }
                }
            }
        }
        return null;
    }
}
