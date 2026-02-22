package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScSelectorSegmentException extends RuntimeException {
    public ScSelectorSegmentException(@NonNull String text) {
        super(text);
    }
}
