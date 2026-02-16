package org.emwhyware.selion.lib;

import org.emwhyware.selion.lib.util.SnLogHandler;
import org.openqa.selenium.By;
import org.slf4j.Logger;

public final class SnXPathRaw extends SnXPath {
    private static final Logger LOG = SnLogHandler.logger(SnXPathRaw.class);
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
