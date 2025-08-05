package com.wenderfabiano.wpm.exceptions;

public class UnrecognizedActionException extends RuntimeException {
    public UnrecognizedActionException(String message){
        super(message);
    }
}
