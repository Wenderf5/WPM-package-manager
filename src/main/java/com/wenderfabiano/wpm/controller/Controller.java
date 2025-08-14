package com.wenderfabiano.wpm.controller;

import java.lang.reflect.InvocationTargetException;

public class Controller {
    public void callAction(String action) {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?> clazz = loader.loadClass(action);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            clazz.getMethod("execute").invoke(instance);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            System.out.println("Error: Unable to perform action.");
        }
    }
}
