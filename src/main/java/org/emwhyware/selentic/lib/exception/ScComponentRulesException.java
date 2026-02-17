package org.emwhyware.selentic.lib.exception;

import org.emwhyware.selentic.lib.ScComponent;

import java.util.List;

public class ScComponentRulesException extends RuntimeException {

    public <T extends ScComponent> ScComponentRulesException(Class<T> componentType) {
        super("At least one component rule must be specified for " + componentType.getName());
    }

    public ScComponentRulesException(String text) {
        super(text);
    }

    public <T extends ScComponent> ScComponentRulesException(Class<T> componentType, List<String> ruleFailures) {
        super(exceptionMessage(componentType, ruleFailures));
    }

    private static <T extends ScComponent> String exceptionMessage(Class<T> componentType, List<String> ruleFailures) {
        final StringBuilder message = new StringBuilder("One or more component rules were violated. Incorrect web element may have been targeted for ");

        message.append(componentType.getName());

        for (String ruleFailure : ruleFailures) {
            message.append("\n . ").append(ruleFailure);
        }
        return message.toString();
    }
}
