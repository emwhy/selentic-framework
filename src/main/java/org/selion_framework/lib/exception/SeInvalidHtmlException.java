package org.selion_framework.lib.exception;

public class SeInvalidHtmlException extends RuntimeException {
    public SeInvalidHtmlException(String text, Throwable th) {
        super(text, th);
    }

    public SeInvalidHtmlException(String text) {
        super(text);
    }
}
