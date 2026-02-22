package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScPageCreationException extends RuntimeException {
    public ScPageCreationException(@NonNull Throwable th) {
        super("Error while creating instance of a page.", th);
    }
}
