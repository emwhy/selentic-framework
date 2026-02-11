package org.selion_framework.lib.exception;

import org.selion_framework.lib.SeComponent;

import java.util.List;

public class SeComponentRulesException extends RuntimeException {

    public <T extends SeComponent> SeComponentRulesException(Class<T> componentType) {
        super("At least one component rule must be specified for " + componentType.getName());
    }

    public SeComponentRulesException(String text) {
        super(text);
    }

    public <T extends SeComponent> SeComponentRulesException(Class<T> componentType, List<String> ruleFailures) {
        super(exceptionMessage(componentType, ruleFailures));
    }

    private static <T extends SeComponent> String exceptionMessage(Class<T> componentType, List<String> ruleFailures) {
        final StringBuilder message = new StringBuilder("One or more component rules were violated. Incorrect web element may have been targeted for " + componentType.getName() + ".\n");

        for (String ruleFailure : ruleFailures) {
            message.append("\n\t").append(ruleFailure);
        }
        return message.toString();
    }
}
