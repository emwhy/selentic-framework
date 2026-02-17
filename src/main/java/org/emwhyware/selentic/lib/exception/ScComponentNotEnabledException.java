package org.emwhyware.selentic.lib.exception;

public class ScComponentNotEnabledException extends RuntimeException {
    public ScComponentNotEnabledException(Throwable th) {
        super("Component is not enabled.", th);
    }
}
