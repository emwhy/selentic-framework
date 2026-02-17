package org.emwhyware.selentic.lib.exception;

public class ScComponentNotFoundException extends RuntimeException {
    public ScComponentNotFoundException(String text, Throwable th) {
        super(text, th);
    }
}
