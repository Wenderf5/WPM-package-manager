package com.wenderfabiano.wpm;

import com.wenderfabiano.wpm.validator.Validator;

public class Main {
    public static void main(String[] args) {
        Validator validator = new Validator();
        validator.validate(args);
    }
}
