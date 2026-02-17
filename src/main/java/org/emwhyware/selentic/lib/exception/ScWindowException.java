package org.emwhyware.selentic.lib.exception;

public class ScWindowException extends RuntimeException {
    public ScWindowException(String text, Throwable th) {
        super(text, th);
    }

    public ScWindowException(String text) {
        super(text);
    }
}
