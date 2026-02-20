package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.openqa.selenium.By;
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
    protected String nodeText() {
        return "";
    }

    @Override
    protected By build() {
        LOG.debug("Selector CSS Selector: {}", this.selectorText);
        return By.cssSelector(this.selectorText);
    }
}
