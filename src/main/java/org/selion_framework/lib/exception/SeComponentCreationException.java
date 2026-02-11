package org.selion_framework.lib.exception;

public class SeComponentCreationException extends RuntimeException {
    public SeComponentCreationException(Throwable th) {
        super("Error while creating instance of a component.", th);
    }

    public SeComponentCreationException(String text) {
        super("Error while creating instance of a component: " + text);
    }
}
