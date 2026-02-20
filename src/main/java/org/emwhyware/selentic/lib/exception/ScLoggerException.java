package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScLoggerException extends RuntimeException {
    public ScLoggerException(@NonNull String text, @NonNull Throwable th) {
        super(text, th);
    }

    public ScLoggerException(@NonNull String text) {
        super(text);
    }
}
