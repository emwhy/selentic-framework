package org.selion_framework.lib.exception;

public class SeInitializationException extends RuntimeException {
    public SeInitializationException(String text, Throwable th) {
        super(text, th);
    }

    public SeInitializationException(String text) {
        super(text);
    }
}
