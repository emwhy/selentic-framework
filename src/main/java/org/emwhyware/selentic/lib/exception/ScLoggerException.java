package org.emwhyware.selentic.lib.exception;

public class ScLoggerException extends RuntimeException {
    public ScLoggerException(String text, Throwable th) {
        super(text, th);
    }

    public ScLoggerException(String text) {
        super(text);
    }
}
