package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScInitializationException extends RuntimeException {
    public ScInitializationException(@NonNull String text, @NonNull Throwable th) {
        super(text, th);
    }

    public ScInitializationException(@NonNull String text) {
        super(text);
    }
}
