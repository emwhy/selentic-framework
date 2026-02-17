package org.emwhyware.selentic.lib.exception;

public class ScComponentAnimatingException extends RuntimeException {
    public ScComponentAnimatingException(Throwable th) {
        super("Component did not stop animating within expected time.", th);
    }
}
