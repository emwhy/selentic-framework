package org.emwhyware.selentic.lib.exception;

public class ScPageCreationException extends RuntimeException {
    public ScPageCreationException(Throwable th) {
        super("Error while creating instance of a page.", th);
    }
}
