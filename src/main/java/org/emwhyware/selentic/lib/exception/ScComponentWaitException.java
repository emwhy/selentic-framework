package org.emwhyware.selentic.lib.exception;

public class ScComponentWaitException extends RuntimeException {
    public ScComponentWaitException(String text, Throwable th) {
        super(text, th);
    }

    public ScComponentWaitException(String text) {
        super(text);
    }
}
