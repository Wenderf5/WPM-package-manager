package com.wenderfabiano.wpm.validator;

import com.wenderfabiano.wpm.exceptions.MultipleActionsException;
import com.wenderfabiano.wpm.exceptions.MultipleSameActionsException;
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
    private List<String> validActions = new ArrayList<>();

    public void validate(String... actions) {
        try {
            //Gets all valid actions for comparison and validation.
            getValidActions();

            //Methods that are called to validate arguments passed by the user.
            actionValidator(actions);
            multipleSameActionsValidator(actions);
            multipleActionsValidator(actions);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("Ok");
        //Chama o controller
    }

    private void actionValidator(String... actions) {
        for (String action : actions) {
            if (!this.validActions.contains(action)) {
                throw new UnrecognizedActionException("The argument '" + action + "' is not recognized by wpm!");
            }
        }
    }

    private void multipleSameActionsValidator(String... actions) {
        if (actions.length < 2) {
            return;
        }

        for (String actionToBeCompared : actions) {
            int sameAction = 0;
            for (String action : actions) {
                if (actionToBeCompared.equals(action)) {
                    sameAction++;
                    if (sameAction > 1) {
                        throw new MultipleSameActionsException("We noticed an inconsistency in your command. The action '" + action + "' appears more than once.");
                    }
                }
            }
        }
    }

    private void multipleActionsValidator(String... actions) {
        if (actions.length < 2) {
            return;
        }

        int detectedActions = 0;
        for (String action : actions) {
            if (this.validActions.contains(action)) {
                detectedActions++;
            }
            if (detectedActions > 1) {
                throw new MultipleActionsException("You sent more than one command. Please perform only one action at a time!");
            }
        }
    }

    private void getValidActions() {
        try {
            InputStream xmlFile = Validator.class.getClassLoader().getResourceAsStream("actions-config.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList actionsList = doc.getElementsByTagName("action");
            for (int i = 0; i < actionsList.getLength(); i++) {
                Element element = (Element) actionsList.item(i);
                String command = element.getElementsByTagName("command").item(0).getTextContent();
                validActions.add(command);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load valid actions.");
        }
    }
}
