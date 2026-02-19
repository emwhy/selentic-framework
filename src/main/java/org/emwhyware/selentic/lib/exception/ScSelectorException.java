package org.emwhyware.selentic.lib.exception;

public class ScSelectorException extends RuntimeException {
    public ScSelectorException(String text, Throwable th) {
        super(text, th);
    }

    public ScSelectorException(String text) {
        super(text);
    }
}
