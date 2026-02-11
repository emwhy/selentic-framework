package org.selion_framework.lib.exception;

public class SeDownloadFileException extends RuntimeException {
    public SeDownloadFileException(String text, Throwable th) {
        super(text, th);
    }

    public SeDownloadFileException(String text) {
        super(text);
    }
}
