package org.selion_framework.lib.exception;

public class SeEntryNotFoundException extends RuntimeException {
    public SeEntryNotFoundException(String key) {
        super("Unable to find entry with key: " + key);
    }

    public SeEntryNotFoundException(int index) {
        super("Unable to find entry with index: " + index);
    }
}
