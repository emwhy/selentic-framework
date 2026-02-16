package org.emwhyware.selion.lib.exception;

public class SnComponentCreationException extends RuntimeException {
    public SnComponentCreationException(Throwable th) {
        super("Error while creating instance of a component.", th);
    }

    public SnComponentCreationException(String text) {
        super("Error while creating instance of a component: " + text);
    }
}
