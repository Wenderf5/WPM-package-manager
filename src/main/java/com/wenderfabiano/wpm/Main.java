package com.wenderfabiano.wpm;

import com.wenderfabiano.wpm.validator.Validator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Validator validator = new Validator();
        validator.validate(args);
    }
}
