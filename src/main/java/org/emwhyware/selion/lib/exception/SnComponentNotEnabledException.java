package org.emwhyware.selion.lib.exception;

public class SnComponentNotEnabledException extends RuntimeException {
    public SnComponentNotEnabledException(Throwable th) {
        super("Component is not enabled.", th);
    }
}
