package org.selion_framework.lib.exception;

import org.selion_framework.lib.SnComponent;

import java.util.List;

public class SnComponentRulesException extends RuntimeException {

    public <T extends SnComponent> SnComponentRulesException(Class<T> componentType) {
        super("At least one component rule must be specified for " + componentType.getName());
    }

    public SnComponentRulesException(String text) {
        super(text);
    }

    public <T extends SnComponent> SnComponentRulesException(Class<T> componentType, List<String> ruleFailures) {
        super(exceptionMessage(componentType, ruleFailures));
    }

    private static <T extends SnComponent> String exceptionMessage(Class<T> componentType, List<String> ruleFailures) {
        final StringBuilder message = new StringBuilder("One or more component rules were violated. Incorrect web element may have been targeted for " + componentType.getName() + ".\n");

        for (String ruleFailure : ruleFailures) {
            message.append("\n\t").append(ruleFailure);
        }
        return message.toString();
    }
}
