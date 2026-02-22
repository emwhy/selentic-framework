package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScElementNotFoundException extends RuntimeException {
    public ScElementNotFoundException(@NonNull String text, @NonNull Throwable th) {
        super(text, th);
    }

    public ScElementNotFoundException(@NonNull String text) {
        super(text);
    }
}
