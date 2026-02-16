package org.emwhyware.selion.lib.exception;

public class SnPageCreationException extends RuntimeException {
    public SnPageCreationException(Throwable th) {
        super("Error while creating instance of a page.", th);
    }
}
