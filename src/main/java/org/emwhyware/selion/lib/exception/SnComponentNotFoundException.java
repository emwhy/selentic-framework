package org.emwhyware.selion.lib.exception;

public class SnComponentNotFoundException extends RuntimeException {
    public SnComponentNotFoundException(String text, Throwable th) {
        super(text, th);
    }
}
