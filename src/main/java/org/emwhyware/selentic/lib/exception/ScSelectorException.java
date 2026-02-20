package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScSelectorException extends RuntimeException {
    public ScSelectorException(@NonNull String text, @NonNull Throwable th) {
        super(text, th);
    }

    public ScSelectorException(@NonNull String text) {
        super(text);
    }
}
