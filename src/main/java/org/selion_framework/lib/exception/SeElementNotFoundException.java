package org.selion_framework.lib.exception;

public class SeElementNotFoundException extends RuntimeException {
    public SeElementNotFoundException(String text, Throwable th) {
        super(text, th);
    }

    public SeElementNotFoundException(String text) {
        super(text);
    }
}
