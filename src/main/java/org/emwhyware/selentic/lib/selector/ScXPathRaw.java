package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.slf4j.Logger;

/**
 * Allows using raw XPath text rather than defining it with the builder.
 *
 * @see ScXPath
 * @see ScCssSelector
 */
public final class ScXPathRaw extends ScXPath {
    private static final Logger LOG = ScLogHandler.logger(ScXPathRaw.class);
    private final String selectorText;

    ScXPathRaw(@NonNull String selectorText) {
        super("RAW");
        this.selectorText = selectorText;
    }

    @Override
    public String toString() {
        return this.selectorText;
    }

    @Override
    protected String nodeText() {
        return "";
    }

}
