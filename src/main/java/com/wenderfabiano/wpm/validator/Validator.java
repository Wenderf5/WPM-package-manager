package com.wenderfabiano.wpm.validator;

import com.wenderfabiano.wpm.controller.Controller;
import com.wenderfabiano.wpm.exceptions.MultipleActionsException;
import com.wenderfabiano.wpm.exceptions.NoActionsFoundException;
import com.wenderfabiano.wpm.exceptions.UnrecognizedActionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Validator {
    private List<Action> validActions = new ArrayList<>();

    public Validator() {
        getValidActions();
    }

    public void validate(String... actions) {
        try {
            emptyActionsValidator(actions);
            actionExistsValidator(actions);
            multipleActionsValidator(actions);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        callController(actions);
    }

    private void getValidActions() {
        try {
            InputStream xmlFile = Validator.class.getClassLoader().getResourceAsStream("actions-config.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            NodeList actionsList = document.getElementsByTagName("action");
            for (int i = 0; i < actionsList.getLength(); i++) {
                Element element = (Element) actionsList.item(i);

                String name = element.getElementsByTagName("name").item(0).getTextContent();
                String command = element.getElementsByTagName("command").item(0).getTextContent();
                String fullClassName = element.getElementsByTagName("fullClassName").item(0).getTextContent();

                Action action = new Action(name, command, fullClassName);
                validActions.add(action);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load valid actions.");
        }
    }

    private void emptyActionsValidator(String... actions) {
        if (actions.length < 1) {
            throw new NoActionsFoundException("No action passed. To see what actions can be performed, use the 'help' action.");
        }
    }

    private void actionExistsValidator(String... actions) {
        List<String> validComands = new ArrayList<>();
        for (Action validAction : validActions) {
            validComands.add(validAction.getCommand());
        }

        for (String action : actions) {
            if (!validComands.contains(action)) {
                throw new UnrecognizedActionException("The argument '" + action + "' is not recognized by wpm!");
            }
        }
    }

    private void multipleActionsValidator(String... actions) {
        if (actions.length > 1) {
            throw new MultipleActionsException("You sent more than one command. Please perform only one action at a time!");
        }
    }

    private void callController(String... actions) {
        for (Action action : validActions) {
            if (action.getCommand().equals(actions[0])) {
                Controller controller = new Controller();
                controller.callAction(action.getFullClassName());
            }
        }
    }
}
