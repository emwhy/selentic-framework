package org.emwhyware.selentic.lib.exception;

public class ScElementNotFoundException extends RuntimeException {
    public ScElementNotFoundException(String text, Throwable th) {
        super(text, th);
    }

    public ScElementNotFoundException(String text) {
        super(text);
    }
}
