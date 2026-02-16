package org.selion_framework.lib;

import org.selion_framework.lib.exception.SnUnexpectedPageException;
import org.selion_framework.lib.exception.SnWaitTimeoutException;
import org.selion_framework.lib.util.SnLogHandler;
import org.slf4j.Logger;

import static org.selion_framework.lib.util.SnWait.waitUntil;

/**
 * {@code SnAbstractPage} is the abstract base class for all page classes.
 * All page classes must extend from {@code SnAbstractPage} when defined.
 *
 * @see SnComponent
 * @see SnAbstractComponent
 * @see SnPage
 */
public abstract class SnAbstractPage extends SnAbstractComponent {
    private static final Logger LOGGER = SnLogHandler.logger(SnAbstractPage.class);

    /**
     * Provides access to the builder which provides methods to build XPath selector objects for page-level elements.
     *
     * @see SnPageXPathBuilder
     */
    protected static final SnPageXPathBuilder _xpath = new SnPageXPathBuilder();

    /**
     * Provides access to the builder which provides methods to build CSS selector objects for page-level elements.
     *
     * @see SnPageCssSelectorBuilder
     */
    protected static final SnPageCssSelectorBuilder _cssSelector = new SnPageCssSelectorBuilder();

    /**
     * Waits for a specific component to be displayed on the page.
     *
     * <p>
     * This is a convenience method that waits for the component to become visible. It can be used
     * to ensure that a critical component is displayed before proceeding with test actions.
     * 
     *
     * <p>
     * This method is typically called within the {@link #waitForDisplayed()} method to wait for
     * key components that indicate the page has fully loaded.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * @Override
     * protected void waitForDisplayed() {
     *     waitForComponent(usernameField);
     *     waitForComponent(passwordField);
     * }
     * }</pre>
     * 
     *
     * @param c the {@link SnComponent} to wait for
     * @throws SnWaitTimeoutException if the component does not become displayed within the timeout period
     */
    protected void waitForComponent(SnComponent c) {
        c.waitForDisplayed();
    }

    /**
     * Waits for the page to be fully loaded and ready for interaction.
     *
     * @throws SnUnexpectedPageException if an error occurs while waiting for the page to load,
     *                                    or if the page does not load within the timeout period
     */
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