package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScWindowException extends RuntimeException {
    public ScWindowException(@NonNull String text, @NonNull Throwable th) {
        super(text, th);
    }

    public ScWindowException(@NonNull String text) {
        super(text);
    }
}
