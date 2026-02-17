package org.emwhyware.selentic.lib.exception;

public class ScEntryNotFoundException extends RuntimeException {
    public ScEntryNotFoundException(String key) {
        super("Unable to find entry with key: " + key);
    }

    public ScEntryNotFoundException(int index) {
        super("Unable to find entry with index: " + index);
    }
}
