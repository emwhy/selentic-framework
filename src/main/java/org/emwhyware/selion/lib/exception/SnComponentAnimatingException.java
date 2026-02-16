package org.emwhyware.selion.lib.exception;

public class SnComponentAnimatingException extends RuntimeException {
    public SnComponentAnimatingException(Throwable th) {
        super("Component did not stop animating within expected time.", th);
    }
}
