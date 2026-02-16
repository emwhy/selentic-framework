package org.emwhyware.selion.lib.exception;

public class SnDownloadFileException extends RuntimeException {
    public SnDownloadFileException(String text, Throwable th) {
        super(text, th);
    }

    public SnDownloadFileException(String text) {
        super(text);
    }
}
