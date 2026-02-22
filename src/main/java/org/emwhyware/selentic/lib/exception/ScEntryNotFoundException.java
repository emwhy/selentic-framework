package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScEntryNotFoundException extends RuntimeException {
    public ScEntryNotFoundException(@NonNull String key) {
        super("Unable to find entry with key: " + key);
    }

    public ScEntryNotFoundException(int index) {
        super("Unable to find entry with index: " + index);
    }
}
