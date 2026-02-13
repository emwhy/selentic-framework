package org.selion_framework.lib;

import org.selion_framework.lib.exception.SnUnexpectedPageException;
import org.selion_framework.lib.util.SnLogHandler;
import org.slf4j.Logger;

import static org.selion_framework.lib.util.SnWait.waitUntil;

public abstract class SnAbstractPage extends SnAbstractComponent {
    private static final Logger LOGGER = SnLogHandler.logger(SnAbstractPage.class);

    protected static final SnPageXPathBuilder _xpath = new SnPageXPathBuilder();
    protected static final SnPageCssSelectorBuilder _cssSelector = new SnPageCssSelectorBuilder();

    protected void waitForComponent(SnComponent c) {
        c.waitForDisplayed();
    }

    protected final void waitForPage() {
        try {
            waitUntil(() -> {
                final String readyState = String.valueOf(Selion.executeScript("return document.readyState"));

                return readyState != null && readyState.equals("complete");
            });
            this.waitForDisplayed();
            LOGGER.debug("Page URL: {}", Selion.driver().getCurrentUrl());
        } catch (Throwable th) {
            throw new SnUnexpectedPageException(this.getClass().getCanonicalName(), th);
        }
    }
}
