package org.emwhyware.selion.lib.exception;

public class SnEntryNotFoundException extends RuntimeException {
    public SnEntryNotFoundException(String key) {
        super("Unable to find entry with key: " + key);
    }

    public SnEntryNotFoundException(int index) {
        super("Unable to find entry with index: " + index);
    }
}
