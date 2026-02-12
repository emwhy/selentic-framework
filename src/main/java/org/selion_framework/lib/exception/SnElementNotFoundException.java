package org.selion_framework.lib.exception;

public class SnElementNotFoundException extends RuntimeException {
    public SnElementNotFoundException(String text, Throwable th) {
        super(text, th);
    }

    public SnElementNotFoundException(String text) {
        super(text);
    }
}
