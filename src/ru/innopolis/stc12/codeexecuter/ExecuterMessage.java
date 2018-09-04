package ru.innopolis.stc12.codeexecuter;

public class ExecuterMessage implements Executer {
    @Override
    public void execute(String code) {
        System.out.println("code: " + code);
    }
}
