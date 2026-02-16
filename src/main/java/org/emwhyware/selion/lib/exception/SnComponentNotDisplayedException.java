package org.emwhyware.selion.lib.exception;

public class SnComponentNotDisplayedException extends RuntimeException {
    public SnComponentNotDisplayedException(Throwable th) {
        super("Component is not displayed.", th);
    }
}
