package org.selion_framework.lib.exception;

public class SnInitializationException extends RuntimeException {
    public SnInitializationException(String text, Throwable th) {
        super(text, th);
    }

    public SnInitializationException(String text) {
        super(text);
    }
}
