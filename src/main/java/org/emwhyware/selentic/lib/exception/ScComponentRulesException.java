package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.ScComponent;

import java.util.List;

public class ScComponentRulesException extends RuntimeException {

    public <T extends ScComponent> ScComponentRulesException(@NonNull Class<T> componentType) {
        super("At least one component rule must be specified for " + componentType.getName());
    }

    public ScComponentRulesException(@NonNull String text) {
        super(text);
    }

    public <T extends ScComponent> ScComponentRulesException(@NonNull Class<T> componentType, @NonNull List<String> ruleFailures) {
        super(exceptionMessage(componentType, ruleFailures));
    }

    private static <T extends ScComponent> String exceptionMessage(@NonNull Class<T> componentType, @NonNull List<String> ruleFailures) {
        final StringBuilder message = new StringBuilder("One or more component rules were violated. Incorrect web element may have been targeted for ");

        message.append(componentType.getName());

        for (final String ruleFailure : ruleFailures) {
            message.append("\n . ").append(ruleFailure);
        }
        return message.toString();
    }
}
