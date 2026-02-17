package org.emwhyware.selentic.lib.exception;

public class ScInitializationException extends RuntimeException {
    public ScInitializationException(String text, Throwable th) {
        super(text, th);
    }

    public ScInitializationException(String text) {
        super(text);
    }
}
