package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScInvalidHtmlException extends RuntimeException {
    public ScInvalidHtmlException(@NonNull String text, @NonNull Throwable th) {
        super(text, th);
    }

    public ScInvalidHtmlException(@NonNull String text) {
        super(text);
    }
}
