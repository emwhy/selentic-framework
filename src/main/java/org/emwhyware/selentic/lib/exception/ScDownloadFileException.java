package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScDownloadFileException extends RuntimeException {
    public ScDownloadFileException(@NonNull String text,@NonNull  Throwable th) {
        super(text, th);
    }

    public ScDownloadFileException(@NonNull String text) {
        super(text);
    }
}
