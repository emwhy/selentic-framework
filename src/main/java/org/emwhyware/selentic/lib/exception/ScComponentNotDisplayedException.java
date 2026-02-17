package org.emwhyware.selentic.lib.exception;

public class ScComponentNotDisplayedException extends RuntimeException {
    public ScComponentNotDisplayedException(Throwable th) {
        super("Component is not displayed.", th);
    }
}
