package org.selion_framework.lib.exception;

public class SeLoggerException extends RuntimeException {
    public SeLoggerException(String text, Throwable th) {
        super(text, th);
    }

    public SeLoggerException(String text) {
        super(text);
    }
}
