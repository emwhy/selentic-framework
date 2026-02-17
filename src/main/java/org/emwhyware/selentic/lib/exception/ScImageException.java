package org.emwhyware.selentic.lib.exception;

public class ScImageException extends RuntimeException {
    public ScImageException(String text, Throwable th) {
        super(text, th);
    }

    public ScImageException(String text) {
        super(text);
    }
}
