package com.wenderfabiano.wpm.validator;

public class Action {
    private final String name;
    private final String command;
    private final String fullClassName;

    public Action(String name, String command, String fullClassName) {
        this.name = name;
        this.command = command;
        this.fullClassName = fullClassName;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public String getFullClassName() {
        return fullClassName;
    }
}
