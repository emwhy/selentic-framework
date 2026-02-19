package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.openqa.selenium.By;
import org.slf4j.Logger;

public final class ScCssSelectorRaw extends ScCssSelector {
    private static final Logger LOG = ScLogHandler.logger(ScCssSelectorRaw.class);
    private final String selectorText;

    ScCssSelectorRaw(String selectorText) {
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
