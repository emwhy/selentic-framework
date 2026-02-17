package org.emwhyware.selion.lib;

import org.emwhyware.selion.lib.config.SelionConfig;
import org.emwhyware.selion.lib.exception.SnComponentNotEnabledException;
import org.emwhyware.selion.lib.util.SnWait;
import org.openqa.selenium.WebElement;

/**
 * Abstract base class for web components that support click interactions.
 * <p>
 * The {@code SnClickableComponent} extends {@link SnComponent} and provides functionality for
 * interactive web elements that can be clicked, such as buttons, links, and clickable divs.
 * All click operations are guarded by automatic wait and enabled checks to ensure elements
 * are in a valid state before interaction.
 * 
 * <p>
 * Key features:
 * <ul>
 *   <li><strong>Enabled State Validation</strong>: All click operations wait for the element to be enabled</li>
 *   <li><strong>Automatic Scrolling</strong>: Elements are automatically scrolled into view before clicking</li>
 *   <li><strong>Error Handling</strong>: Throws {@link SnComponentNotEnabledException} if element doesn't become enabled</li>
 *   <li><strong>Multiple Click Types</strong>: Supports single click, double click, and positioned clicks</li>
 * </ul>
 *
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * button.click();           // Waits for enabled, then clicks
 * button.doubleClick();     // Waits for enabled, then double clicks
 * button.clickAt(10, 20);   // Waits for enabled, then clicks at offset (10, 20)
 * </pre>
 *
 * <h2>Behavior:</h2>
 * <p>
 * All click operations follow this sequence:
 * <ol>
 *   <li>Wait for element to exist and be enabled (using default timeout from {@link SelionConfig})</li>
 *   <li>Scroll element into view</li>
 *   <li>Perform the click action</li>
 * </ol>
 * If the element fails to become enabled within the timeout period, a {@link SnComponentNotEnabledException}
 * is thrown immediately, preventing invalid interactions with disabled elements.
 *
 *
 * @see SnComponent
 * @see SnButton
 * @see SnLink
 */
public abstract class SnClickableComponent extends SnComponent {

    /**
     * Determines if this component exists and is currently enabled.
     * <p>
     * An element is considered enabled if it exists in the DOM and the WebElement's
     * {@link WebElement#isEnabled()} method returns true. This typically means the element
     * is not disabled and is available for user interaction.
     * 
     *
     * @return true if the component exists and is enabled, false otherwise
     */
    public boolean isEnabled() {
        return this.exists() && this.existingElement().isEnabled();
    }

    /**
     * Waits until this component is enabled, then returns the scrolled web element.
     * <p>
     * This internal method performs the following operations:
     * <ol>
     *   <li>Waits until {@link #isEnabled()} returns true using default timeout</li>
     *   <li>Scrolls the element into view</li>
     *   <li>Returns the web element ready for interaction</li>
     * </ol>
     * 
     * <p>
     * The wait operation uses the framework's default timeout configuration.
     * If the element does not become enabled within this timeout, a {@link SnComponentNotEnabledException}
     * is thrown.
     * 
     *
     * @return the scrolled {@link WebElement} that is ready for interaction
     * @throws SnComponentNotEnabledException if the component does not become enabled within the timeout period
     */
    protected final WebElement enabledElement() {
        final WebElement scrolled = scrolledElement();

        SnWait.waitUntil(this.waitTimeout(), this::isEnabled, SnComponentNotEnabledException::new);
        return scrolled;
    }

    /**
     * Performs a single click on this component.
     * <p>
     * This method waits for the component to become enabled before performing the click action.
     * The click is executed on the scrolled (into view) element. If the component fails to become
     * enabled within the default timeout, a {@link SnComponentNotEnabledException} is thrown
     * and the click is not performed.
     * 
     *
     * @throws SnComponentNotEnabledException if the component does not become enabled within the timeout
     */
    public void click() {
        enabledElement();
        super.click();
    }

    /**
     * Performs a double click on this component.
     * <p>
     * This method waits for the component to become enabled before performing the double click action.
     * The double click is executed on the scrolled (into view) element. If the component fails to become
     * enabled within the default timeout, a {@link SnComponentNotEnabledException} is thrown
     * and the double click is not performed.
     * 
     *
     * @throws SnComponentNotEnabledException if the component does not become enabled within the timeout
     */
    public void doubleClick() {
        enabledElement();
        super.doubleClick();
    }

    /**
     * Performs a click at a specific offset position on this component.
     * <p>
     * This method waits for the component to become enabled before performing the click action at the
     * specified coordinates relative to the element's top-left corner. The click is executed on the
     * scrolled (into view) element. If the component fails to become enabled within the default timeout,
     * a {@link SnComponentNotEnabledException} is thrown and the click is not performed.
     * 
     *
     * @param x the x-coordinate offset from the element's top-left corner
     * @param y the y-coordinate offset from the element's top-left corner
     * @throws SnComponentNotEnabledException if the component does not become enabled within the timeout
     */
    public void clickAt(int x, int y) {
        enabledElement();
        super.clickAt(x, y);
    }

    /**
     * Performs a double click at a specific offset position on this component.
     * <p>
     * This method waits for the component to become enabled before performing the double click action at the
     * specified coordinates relative to the element's top-left corner. The double click is executed on the
     * scrolled (into view) element. If the component fails to become enabled within the default timeout,
     * a {@link SnComponentNotEnabledException} is thrown and the double click is not performed.
     * 
     *
     * @param x the x-coordinate offset from the element's top-left corner
     * @param y the y-coordinate offset from the element's top-left corner
     * @throws SnComponentNotEnabledException if the component does not become enabled within the timeout
     */
    public void doubleClickAt(int x, int y) {
        enabledElement();
        super.doubleClickAt(x, y);
    }
}