package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.exception.ScUnexpectedPageException;
import org.emwhyware.selentic.lib.exception.ScWaitTimeoutException;
import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.slf4j.Logger;

import static org.emwhyware.selentic.lib.util.ScWait.waitUntil;

/**
 * {@code SnAbstractPage} is the abstract base class for all page classes.
 * All page classes must extend from {@code SnAbstractPage} when defined.
 *
 * @see ScComponent
 * @see ScAbstractComponent
 * @see ScPage
 */
public abstract class ScAbstractPage extends ScAbstractComponent {
    private static final Logger LOGGER = ScLogHandler.logger(ScAbstractPage.class);

    /**
     * Provides access to the builder which provides methods to build XPath selector objects for page-level elements.
     *
     * @see ScPageXPathBuilder
     */
    protected static final ScPageXPathBuilder _xpath = new ScPageXPathBuilder();

    /**
     * Provides access to the builder which provides methods to build CSS selector objects for page-level elements.
     *
     * @see ScPageCssSelectorBuilder
     */
    protected static final ScPageCssSelectorBuilder _cssSelector = new ScPageCssSelectorBuilder();

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
     * @param c the {@link ScComponent} to wait for
     * @throws ScWaitTimeoutException if the component does not become displayed within the timeout period
     */
    protected void waitForComponent(ScComponent c) {
        c.waitForDisplayed();
    }

    /**
     * Waits for the page to be fully loaded and ready for interaction.
     *
     * @throws ScUnexpectedPageException if an error occurs while waiting for the page to load,
     *                                    or if the page does not load within the timeout period
     */
    protected final void waitForPage() {
        try {
            waitUntil(() -> {
                final String readyState = String.valueOf(Selentic.executeScript("return document.readyState"));

                return readyState != null && readyState.equals("complete");
            });
            this.waitForDisplayed();
            LOGGER.debug("Page URL: {}", Selentic.driver().getCurrentUrl());
        } catch (Throwable th) {
            throw new ScUnexpectedPageException(this.getClass().getCanonicalName(), th);
        }
    }
}