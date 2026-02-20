package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScComponentWaitException extends RuntimeException {
    public ScComponentWaitException(@NonNull String text, @NonNull Throwable th) {
        super(text, th);
    }

    public ScComponentWaitException(@NonNull String text) {
        super(text);
    }
}
