package org.selion_framework.lib.exception;

public class SnLoggerException extends RuntimeException {
    public SnLoggerException(String text, Throwable th) {
        super(text, th);
    }

    public SnLoggerException(String text) {
        super(text);
    }
}
