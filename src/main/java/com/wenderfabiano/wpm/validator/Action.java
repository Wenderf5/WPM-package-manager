package com.wenderfabiano.wpm.validator;

public class Action {
    private String name;
    private String command;

    public Action(String name, String command){
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }
}
