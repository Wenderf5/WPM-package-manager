package com.wenderfabiano.wpm.validator;

import com.wenderfabiano.wpm.exceptions.MultipleActionsException;
import com.wenderfabiano.wpm.exceptions.UnrecognizedActionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Validator {
    private final List<String> actions;

    public Validator() throws ParserConfigurationException, IOException, SAXException {
        this.actions = getActions();
    }

    public void validate(String... actions) {
        try {
            actionValidator(actions);
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
            if (!this.actions.contains(action)) {
                throw new UnrecognizedActionException("The action '" + action + "' is not recognized by wpm!");
            }
        }
    }

    private void multipleActionsValidator(String... actions) {
        if (actions.length < 2) {
            return;
        }

        for (int i = 1; i < actions.length; i++) {
            if (actions[i].equals(actions[i - 1])) {
                throw new MultipleActionsException("We noticed an inconsistency in your command. The action '" + actions[i] + "' appears more than once.");
            }
        }
    }

    private List<String> getActions() throws ParserConfigurationException, IOException, SAXException {
        List<String> validActions = new ArrayList<>();

        InputStream xmlFile = Validator.class.getClassLoader().getResourceAsStream("actions-config.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        NodeList actionsList = doc.getElementsByTagName("action");

        for (int i = 0; i < actionsList.getLength(); i++) {
            if (actionsList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) actionsList.item(i);
                String command = element.getElementsByTagName("command").item(0).getTextContent();

                validActions.add(command);
            }
        }
        return validActions;
    }
}
