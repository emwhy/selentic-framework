package org.emwhyware.selentic.lib.exception;

public class ScComponentCreationException extends RuntimeException {
    public ScComponentCreationException(Throwable th) {
        super("Error while creating instance of a component.", th);
    }

    public ScComponentCreationException(String text) {
        super("Error while creating instance of a component: " + text);
    }
}
