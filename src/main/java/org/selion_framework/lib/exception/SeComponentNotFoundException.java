package org.selion_framework.lib.exception;

public class SeComponentNotFoundException extends RuntimeException {
    public SeComponentNotFoundException(String text, Throwable th) {
        super(text, th);
    }
}
