package com.wenderfabiano.wpm.exceptions;

public class MultipleSameActionsException extends RuntimeException {
    public MultipleSameActionsException(String message){
        super(message);
    }
}
