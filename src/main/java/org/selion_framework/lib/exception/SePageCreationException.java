package org.selion_framework.lib.exception;

public class SePageCreationException extends RuntimeException {
    public SePageCreationException(Throwable th) {
        super("Error while creating instance of a page.", th);
    }
}
