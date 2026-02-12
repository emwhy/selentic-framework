package org.selion_framework.lib.exception;

public class SnWindowException extends RuntimeException {
    public SnWindowException(String text, Throwable th) {
        super(text, th);
    }

    public SnWindowException(String text) {
        super(text);
    }
}
