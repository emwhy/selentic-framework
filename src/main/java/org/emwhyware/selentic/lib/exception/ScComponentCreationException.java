package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScComponentCreationException extends RuntimeException {
    public ScComponentCreationException(@NonNull Throwable th) {
        super("Error while creating instance of a component.", th);
    }

    public ScComponentCreationException(@NonNull String text) {
        super("Error while creating instance of a component: " + text);
    }
}
