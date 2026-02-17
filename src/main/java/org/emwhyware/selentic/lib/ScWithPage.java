package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.exception.ScPageCreationException;
import org.emwhyware.selentic.lib.exception.ScUnexpectedPageException;

import java.lang.reflect.InvocationTargetException;

/**
 * {@code SnWithPage} is a builder class for initializing and working with page instances
 *
 * <p>
 * This class is obtained through the {@link ScPage#with(Class)}
 * factory method. It handles the instantiation of page objects and provides methods for performing
 * actions within the context of the page.
 * 
 *
 * <p>
 * <strong>Purpose:</strong> {@code SnWithPage} encapsulates the complexity of:
 * <ul>
 *   <li>Creating page instances using reflection</li>
 *   <li>Handling page instantiation errors gracefully</li>
 *   <li>Waiting for pages to load before returning control</li>
 *   <li>Providing access to the components within the page</li>
 * </ul>
 *
 *
 * <p>
 * <strong>Typical Usage:</strong>
 * <pre>{@code
 * // Simple page initialization and action
 * final WithPage<HomePage> homePage = SnPage.with(HomePage.class);
 *
 * homePage.inPage(home -> {
 *     home.searchBox.setText("Selenium");
 *     home.searchButton.click();
 * });
 *
 * }</pre>
 *
 *
 * @param <T> the type of page being created; must extend from {@link ScPage}
 *
 * @see ScPage
 * @see ScPage#with(Class)
 * @see InPageAction
 * @see ScPageCreationException
 */
public final class ScWithPage<T extends ScPage> {
    private final T page;

    /**
     * Constructs a {@code SnWithPage} builder by instantiating the specified page type.
     *
     * <p>
     * This constructor uses reflection to create a new instance of the page class via its
     * no-argument constructor. This is called internally by the {@link ScPage#with(Class)}
     * factory method.
     * 
     *
     * <p>
     * If the page cannot be instantiated (due to missing no-arg constructor, access restrictions, etc.),
     * a {@link ScPageCreationException} is thrown.
     * 
     *
     * @param pageType the class of the page to instantiate; must extend from {@link ScPage}
     *                 and have a no-argument constructor (typically protected or private)
     * @throws ScPageCreationException if the page instance cannot be created, including:
     *                                  <ul>
     *                                    <li>No accessible no-argument constructor</li>
     *                                    <li>Constructor throws an exception</li>
     *                                    <li>Access restrictions preventing instantiation</li>
     *                                    <li>Other reflection-related errors</li>
     *                                  </ul>
     */
    ScWithPage(Class<T> pageType) {
        try {
            this.page = pageType.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
            throw new ScPageCreationException(ex);
        }
    }

    /**
     * Allows actions within the context of the page, giving access to components within the page. It automatically
     * waits for page loading before entering the method.
     *
     * <p>
     * This method is the primary way to interact with a page using the fluent builder pattern.
     * 
     *
     * <p>
     * The page is guaranteed to be fully loaded before the action code executes, ensuring that
     * all elements are available and ready for interaction.
     * 
     *
     * <p>
     * <strong>Usage Examples:</strong>
     * 
     *
     * <p>
     * <pre>{@code
     * final SnWithPage<HomePage> homePage = SnPage.with(HomePage.class);
     *
     * homePage.inPage(home -> {
     *     home.searchBox.setText("Selenium");
     *     home.searchButton.click();
     * });
     * }</pre>
     * 
     *
     * @param action the {@link InPageAction} to execute within the page context;
     *               receives the fully loaded page instance as a parameter
     * @throws ScUnexpectedPageException if the page does not fully load within the timeout period
     * @throws ScPageCreationException if the page instance could not be created
     *
     * @see ScPage#waitForPage()
     * @see InPageAction
     */
    public void inPage(InPageAction<T> action) {
        this.page.waitForPage();
        action.inPage(this.page);
    }

    /**
     * Functional interface for performing actions within a page context.
     *
     * @param <T> the type of page; must extend from {@link ScPage}
     *
     * @see ScWithPage#inPage(InPageAction)
     */
    public interface InPageAction<T extends ScPage> {
        /**
         * Performs an action on the provided page instance.
         *
         * <p>
         * This method is called by {@link ScWithPage#inPage(InPageAction)} with a fully
         * loaded and initialized page instance.
         * 
         *
         * @param page the fully loaded page instance; guaranteed to be non-null and ready for use
         */
        void inPage(T page);
    }
}