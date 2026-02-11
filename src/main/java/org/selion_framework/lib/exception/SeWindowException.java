package org.selion_framework.lib.exception;

public class SeWindowException extends RuntimeException {
    public SeWindowException(String text, Throwable th) {
        super(text, th);
    }

    public SeWindowException(String text) {
        super(text);
    }
}
