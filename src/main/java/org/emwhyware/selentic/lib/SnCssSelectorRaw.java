package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.openqa.selenium.By;
import org.slf4j.Logger;

public final class SnCssSelectorRaw extends SnCssSelector {
    private static final Logger LOG = ScLogHandler.logger(SnCssSelectorRaw.class);
    private final String selectorText;

    SnCssSelectorRaw(String selectorText) {
        super();
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
