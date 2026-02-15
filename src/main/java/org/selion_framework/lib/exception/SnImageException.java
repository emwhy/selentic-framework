package org.selion_framework.lib.exception;

public class SnImageException extends RuntimeException {
    public SnImageException(String text, Throwable th) {
        super(text, th);
    }

    public SnImageException(String text) {
        super(text);
    }
}
