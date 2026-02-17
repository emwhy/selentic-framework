package org.emwhyware.selentic.lib.exception;

public class ScDownloadFileException extends RuntimeException {
    public ScDownloadFileException(String text, Throwable th) {
        super(text, th);
    }

    public ScDownloadFileException(String text) {
        super(text);
    }
}
