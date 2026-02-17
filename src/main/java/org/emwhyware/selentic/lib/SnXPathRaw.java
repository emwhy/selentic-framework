package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.openqa.selenium.By;
import org.slf4j.Logger;

public final class SnXPathRaw extends SnXPath {
    private static final Logger LOG = ScLogHandler.logger(SnXPathRaw.class);
    private final String selectorText;

    SnXPathRaw(String selectorText) {
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
