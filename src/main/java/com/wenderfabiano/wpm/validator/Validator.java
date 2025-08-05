package com.wenderfabiano.wpm.validator;

import com.wenderfabiano.wpm.actions.DefaultAction;
import com.wenderfabiano.wpm.exceptions.MultipleActionsException;
import com.wenderfabiano.wpm.exceptions.UnrecognizedActionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Validator {
    private final List<String> actions = new ArrayList<>(Arrays.asList("run", "compile", "package"));

    public void validate(String... actions) {
        if (actions.length < 1) {
            DefaultAction.execute();
            return;
        }

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
}
