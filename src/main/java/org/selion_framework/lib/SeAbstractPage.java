package org.selion_framework.lib;

import org.selion_framework.lib.exception.SeUnexpectedPageException;
import org.selion_framework.lib.util.SeLogHandler;
import org.slf4j.Logger;

import static org.selion_framework.lib.util.SeWait.waitUntil;

public abstract class SeAbstractPage extends SeAbstractComponent {
    private static final Logger LOGGER = SeLogHandler.logger(SeAbstractPage.class);

    protected static final SePageLocatorBuilder _xpath = new SePageLocatorBuilder();

    protected abstract void additionalWait();

    protected void waitForComponent(SeComponent c) {
        c.waitForDisplayed();
    }

    protected final void waitForPage() {
        try {
            waitUntil(() -> {
                final String readyState = String.valueOf(Selion.executeScript("return document.readyState"));

                return readyState != null && readyState.equals("complete");
            });
            this.additionalWait();
            LOGGER.debug("Page URL: {}", Selion.driver().getCurrentUrl());
        } catch (Throwable th) {
            throw new SeUnexpectedPageException(this.getClass().getCanonicalName(), th);
        }
    }
}
