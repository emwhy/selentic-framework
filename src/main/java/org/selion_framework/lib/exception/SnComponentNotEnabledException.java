package org.selion_framework.lib.exception;

public class SnComponentNotEnabledException extends RuntimeException {
    public SnComponentNotEnabledException(Throwable th) {
        super("Component is not enabled.", th);
    }
}
