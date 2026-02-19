package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.openqa.selenium.By;
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

    ScXPathRaw(String selectorText) {
        super("RAW");
        this.selectorText = selectorText;
    }

    @Override
    protected String nodeText() {
        return "";
    }

    @Override
    protected By build() {
        return this.build(false);
    }

    @Override
    protected By build(boolean withPrefix) {
        final String s = (withPrefix ? "." : "") + this.selectorText;

        LOG.debug("Selector XPath: {}", s);
        return By.xpath(s);
    }
}
