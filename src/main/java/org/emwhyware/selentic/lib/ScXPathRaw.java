package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.openqa.selenium.By;
import org.slf4j.Logger;

public final class ScXPathRaw extends ScXPath {
    private static final Logger LOG = ScLogHandler.logger(ScXPathRaw.class);
    private final String selectorText;

    ScXPathRaw(String selectorText) {
        super("");
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
