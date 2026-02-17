package org.emwhyware.selentic.lib.exception;

public class ScInvalidHtmlException extends RuntimeException {
    public ScInvalidHtmlException(String text, Throwable th) {
        super(text, th);
    }

    public ScInvalidHtmlException(String text) {
        super(text);
    }
}
