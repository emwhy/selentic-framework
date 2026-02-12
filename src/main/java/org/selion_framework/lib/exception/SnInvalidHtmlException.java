package org.selion_framework.lib.exception;

public class SnInvalidHtmlException extends RuntimeException {
    public SnInvalidHtmlException(String text, Throwable th) {
        super(text, th);
    }

    public SnInvalidHtmlException(String text) {
        super(text);
    }
}
