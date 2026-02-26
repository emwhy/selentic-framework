package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.slf4j.Logger;

/**
 * Allows using raw CSS selector text rather than defining it with the builder.
 *
 * @see ScCssSelector
 * @see ScXPath
 */
public final class ScCssSelectorRaw extends ScCssSelector {
    private static final Logger LOG = ScLogHandler.logger(ScCssSelectorRaw.class);
    private final String selectorText;

    ScCssSelectorRaw(@NonNull String selectorText) {
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
