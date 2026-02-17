package org.emwhyware.selion.lib;

import org.emwhyware.selion.lib.config.SelionConfig;
import org.emwhyware.selion.lib.exception.*;
import org.emwhyware.selion.lib.util.SnWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * {@code SnComponent} is the abstract base class for all component classes in the Selion Framework.
 * All component classes must extend from {@code SnComponent} or one of its subclasses when defined.
 * 
 *
 * <p>
 * This class serves as a wrapper around Selenium's {@link WebElement}, providing enhanced
 * functionality for interacting with UI components including:
 * <ul>
 *   <li>Element existence and visibility checking</li>
 *   <li>Automatic element re-initialization on stale references</li>
 *   <li>Component rule verification for type safety</li>
 *   <li>Scrolling and focus management</li>
 *   <li>Click and interaction methods</li>
 *   <li>Text extraction with various options (full text, own text, inner HTML, etc.)</li>
 *   <li>Attribute and property access</li>
 *   <li>Extensibility</li>
 * </ul>
 *
 * <p>
 * <strong>Component Structure:</strong> A typical component class looks like this:
 * <pre>{@code
 * // Must extend from SnComponent
 * public class SnSanityTestLongListEntryComponent extends SnComponent {
 *     // Selectors are defined at the top
 *     private static final SnCssSelector TITLE_TEXT = _cssSelector.descendant(_cssClasses("title"));
 *     private static final SnCssSelector CHECKBOX = _cssSelector.descendant(_type().is("checkbox"));
 *     private static final SnCssSelector TEXTBOX = _cssSelector.descendant(_type().is("text"));
 *
 *     // Requires this override to define component validation rules
 *     @Override
 *     protected void rules(SnComponentRule rule) {
 *         rule.tag().is("div");
 *         rule.cssClasses().has("long-component-list-entry");
 *     }
 *
 *     // Overriding text() changes what the text content of this component is.
 *     @Override
 *     public String text() {
 *         return titleText.text();
 *     }
 *
 *     // Child components within this component
 *     public final SnGenericComponent titleText = $genericComponent(TITLE_TEXT);
 *     public final SnCheckbox checkbox = $checkbox(CHECKBOX);
 *     public final SnTextbox textbox = $textbox(TEXTBOX);
 * }
 * }</pre>
 *
 *
 * <p>
 * <strong>Key Features:</strong>
 * <ul>
 *   <li><strong>Component Rules:</strong> The {@link #rules(SnComponentRule)} method must be overridden
 *       to define validation rules for the web element. This ensures type safety and helps prevent duplicate
 *       component implementations.</li>
 *   <li><strong>Key:</strong> Override {@link #key()} to provide a unique identifier for the component,
 *       especially useful when working with {@link SnComponentCollection}.</li>
 *   <li><strong>Lazy Initialization:</strong> Web elements are lazily initialized and automatically re-fetched
 *       if they become stale.</li>
 *   <li><strong>Hierarchy Support:</strong> Components can contain other components through child selectors,
 *       supporting complex nested structures.</li>
 * </ul>
 *
 *
 * <p>
 * <strong>Usage:</strong> Components are not directly instantiated via the constructor. Instead, use
 * the {@code $component(selector, type)} factory method to create instances which are accessible within subclasses of
 * {@link SnPage} and {@link SnComponent}.
 * 
 *
 * @see SnAbstractComponent
 * @see SnComponentRule
 * @see SnComponentCollection
 * @see SnPage
 */
public abstract class SnComponent extends SnAbstractComponent {
    private Optional<SnSelector> selector = Optional.empty();
    private SnAbstractComponent $callerComponent;
    private WebElement webElement;
    private Optional<SnComponentRule> rule = Optional.empty();

    /**
     * Provides access to the builder which provides methods to build XPath selector objects.
     *
     * @see SnXPath
     */
    final static protected SnComponentXPathBuilder _xpath = new SnComponentXPathBuilder();

    /**
     * Provides access to the builder which provides methods to build CSS selector objects.
     *
     * @see SnCssSelector
     */
    final static protected SnComponentCssSelectorBuilder _cssSelector = new SnComponentCssSelectorBuilder();

    /**
     * Protected constructor. Components are not directly instantiated through calling the constructor.
     * Use {@code $component(selector, type)} to create instances of a component.
     */
    protected SnComponent() {
    }

    /**
     * Internal method to set the selector for this component.
     *
     * @param selector the {@link SnSelector} to use for locating the web element
     */
    final void setSelector(SnSelector selector) {
        this.selector = Optional.of(selector);
    }

    /**
     * Returns the page class that contains this instance of the component.
     *
     * @return the {@link SnAbstractPage} that owns this component
     */
    final protected SnAbstractPage ownerPage() {
        return $callerComponent instanceof SnAbstractPage ? (SnAbstractPage) $callerComponent : ((SnComponent) $callerComponent).ownerPage();
    }

    /**
     * Internal method to set the underlying web element for this component.
     *
     * @param webElement the {@link WebElement} to be wrapped by this component
     */
    final void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    /**
     * Internal method to set the parent component or page that called this component.
     *
     * @param $componentOrPage a calling component or page.
     */
    final void setCallerComponent(SnAbstractComponent $componentOrPage) {
        this.$callerComponent = $componentOrPage;
    }

    /**
     * Returns the underlying Selenium {@link WebElement} wrapped by this component.
     *
     * <p>
     * This method performs the following operations:
     * <ul>
     *   <li>Validates the cached web element is still valid (not stale)</li>
     *   <li>Re-fetches the element if it has become stale or null</li>
     *   <li>Searches for the element from the page or parent component based on the selector</li>
     * </ul>
     * 
     *
     * @return the {@link WebElement} represented by this component
     * @throws SnElementNotFoundException if the selector is not present or element cannot be found
     */
    private WebElement webElement() {
        if (this.webElement == null) {
            if (this.selector.isPresent() && (this.$callerComponent instanceof SnAbstractPage || this.selector.get().isAbsolute())) {
                return Selion.driver().findElement(selector.get().build());
            } else if (this.selector.isPresent()) {
                return selector.get() instanceof SnXPath ? ((SnComponent) this.$callerComponent).existingElement().findElement(((SnXPath) selector.get()).build(true)) : ((SnComponent) this.$callerComponent).existingElement().findElement(selector.get().build());
            } else {
                throw new SnElementNotFoundException("Selector is not present.");
            }
        }

        return this.webElement;
    }

    /**
     * Sets property rules for the component to be associated with a web element.
     *
     * <p>
     * This abstract method must be implemented by all subclasses to define the validation rules
     * that ensure the intended web element type is referred to by this component.
     * 
     * <p>
     * This is important for:
     * <ul>
     *   <li>Type safety - ensuring that only the correct type of element is used with this component</li>
     *   <li>Preventing duplicate implementations - allowing searching by web element properties</li>
     *   <li>Validation - verifying that the element has the expected tag, classes, and attributes</li>
     * </ul>
     * 
     * <p>
     * At least one rule must be provided in the method. If there is no special properties that can be defined,
     * rule.
     * 
     *
     * @param rule the {@link SnComponentRule} used to define validation rules for this component
     * @see SnComponentRule
     */
    protected abstract void rules(SnComponentRule rule);

    /**
     * Verifies that the web element matches all rules defined for this component.
     *
     * <p>
     * This method is called during element initialization to ensure the element matches the expected
     * properties. The verification is performed only once and then cached.
     * 
     *
     * @param element the {@link WebElement} to verify
     * @throws SnComponentRulesException if the element does not match the defined rules
     */
    private void verifyRules(WebElement element) {
        if (this.rule.isEmpty()) {
            final SnComponentRule componentRule = new SnComponentRule(element);

            this.rule = Optional.of(componentRule);
            this.rules(componentRule);
            componentRule.verifyRules(this.getClass());
        }
    }

    /**
     * Returns an instance of {@link WebElement} after ensuring that it exists in the DOM.
     *
     * <p>
     * This method waits for the component to exist and verifies it matches the defined rules.
     * 
     *
     * @return the {@link WebElement} that exists in the DOM
     * @throws SnElementNotFoundException if the element does not exist or becomes stale
     */
    protected final WebElement existingElement() {
        try {
            WebElement element;

            SnWait.waitUntil(this.waitTimeout(), this::exists);
            element = webElement();
            this.verifyRules(element);
            return element;
        } catch (SnWaitTimeoutException ex) {
            throw new SnElementNotFoundException("Element does not exist.", ex);
        }
    }

    /**
     * Returns an instance of {@link WebElement} after ensuring that the web element is displayed.
     *
     * <p>
     * This method waits for the component to be visible on the page before returning the element.
     * 
     *
     * @return the {@link WebElement} that is currently displayed
     * @throws SnElementNotFoundException if the element is not displayed or becomes stale
     */
    protected final WebElement displayedElement() {
        try {
            this.waitForDisplayed();
            return webElement();
        } catch (SnWaitTimeoutException ex) {
            throw new SnElementNotFoundException("Element is not displayed.", ex);
        }
    }

    /**
     * Returns an instance of {@link WebElement} after scrolling to the web element.
     *
     * <p>
     * This method is commonly used before interacting with an element to ensure it is visible
     * and in the viewport. It uses default scroll options.
     * 
     *
     * @return the {@link WebElement} after scrolling it into view
     */
    protected final WebElement scrolledElement() {
        return this.scrolledElement(scrollOptions());
    }

    /**
     * Returns an instance of {@link WebElement} after scrolling to the web element with specified options.
     *
     * <p>
     * This method is commonly used before interacting with an element to ensure it is visible
     * and in the viewport. The scroll behavior can be customized using {@link SnScrollOptions} which can be
     * acquired by calling {@link #scrollOptions()} method.
     * 
     *
     * @param options the {@link SnScrollOptions} specifying scroll behavior
     * @return the {@link WebElement} after scrolling it into view
     */
    protected final WebElement scrolledElement(SnScrollOptions options) {
        final WebElement e = displayedElement();

        Selion.executeScript("arguments[0].scrollIntoView(arguments[1])", this, options.toString());
        return e;
    }

    /**
     * Returns true if the component exists in the DOM.
     *
     * <p>
     * This method checks whether the element currently exists in the DOM by attempting to access
     * its tag name. It handles stale element references gracefully by catching the exception
     * and returning false.
     * 
     *
     * @return true if the element exists in the DOM; false otherwise
     */
    public final boolean exists() {
        try {
            this.webElement().getTagName();

            return true;
        } catch (NoSuchElementException | StaleElementReferenceException ex) {
            return false;
        }
    }

    /**
     * Returns true if the component is displayed on the page.
     *
     * <p>
     * This method can be overridden in subclasses as needed, as sometimes the "isDisplayed" status
     * may depend on other component properties or visibility rules. This method is used in
     * {@link #waitForDisplayed()} and {@link #displayedElement()}, affecting their waiting behavior.
     * 
     *
     * @return true if the element is displayed; false otherwise
     */
    public boolean isDisplayed() {
        return this.exists() && webElement().isDisplayed();
    }

    /**
     * Waits for the component to be displayed, as defined by {@link #isDisplayed()}.
     *
     * <p>
     * This method blocks until the component becomes visible or a timeout occurs.
     * 
     *
     * @throws SnComponentNotDisplayedException if the element does not become displayed within the timeout period
     */
    protected void waitForDisplayed() {
        existingElement();
        SnWait.waitUntil(this.waitTimeout(), this::isDisplayed, SnComponentNotDisplayedException::new);
    }

    /**
     * Waits for the component to finish animating.
     *
     * <p>
     * This method blocks until the component is no longer animated or a timeout occurs.
     * 
     *
     * <p>
     * <strong>Limitation</strong>: This method only recognizes animations that are created using {@code element.animate()}
     * JavaScript function, which can be recognized by {@code document.getAnimations()}. CSS based manual animations (often
     * implemented by using {@code setInterval()} function) is not recognized by this method.
     * 
     *
     * @throws SnComponentAnimatingException if the element does not stop animating within the timeout period
     */
    protected final void waitForAnimation() {
        this.waitForDisplayed();
        SnWait.waitUntil(this.waitTimeout(), () -> (Boolean) Selion.executeScript(
                        """
                            let e = arguments[0];
                            return !e.getAnimations().some(a => a.playState === 'running' || a.playState === 'pending');
                        """,
                        this
                ),
                SnComponentAnimatingException::new
        );
    }

    /**
     * Returns the scroll options to be used when scrolling this component into view.
     *
     * <p>
     * Override this method in subclasses to customize scrolling behavior for specific components.
     * 
     *
     * @return the {@link SnScrollOptions} for this component, defaults to new instance with default options
     */
    protected final SnScrollOptions scrollOptions() {
        return new SnScrollOptions();
    }

    /**
     * Clicks this component after scrolling it into view.
     *
     * <p>
     * This is a protected method, so it can be used within the extended component class as needed.
     * If click needs to be public, extend from {@link SnClickableComponent} instead.
     * 
     *
     * @throws SnElementNotFoundException if the element is not displayed
     */
    protected void click() {
        this.scrolledElement().click();
    }

    /**
     * Double-clicks this component after scrolling it into view.
     *
     * <p>
     * This is a protected method, so it can be used within the extended component class as needed.
     * If double-click needs to be public, extend from {@link SnClickableComponent} instead.
     * 
     *
     * @throws SnElementNotFoundException if the element is not displayed
     */
    protected void doubleClick() {
        final Actions actions = new Actions(Selion.driver());

        actions.doubleClick(this.scrolledElement()).perform();
    }

    /**
     * Clicks this component at the specified coordinates relative to the component's top-left corner,
     * after scrolling it into view.
     *
     * <p>
     * This is a protected method, so it can be used within the extended component class as needed.
     * If click needs to be public, extend from {@link SnClickableComponent} instead.
     * 
     *
     * @param x the x-coordinate offset from the component's top-left corner
     * @param y the y-coordinate offset from the component's top-left corner
     * @throws SnElementNotFoundException if the element is not displayed
     */
    protected void clickAt(int x, int y) {
        final Actions actions = new Actions(Selion.driver());

        actions.moveToElement(this.scrolledElement(), x, y).click().perform();
    }

    /**
     * Double-clicks this component at the specified coordinates relative to the component's top-left corner,
     * after scrolling it into view.
     *
     * <p>
     * This is a protected method, so it can be used within the extended component class as needed.
     * If double-click needs to be public, extend from {@link SnClickableComponent} instead.
     * 
     *
     * @param x the x-coordinate offset from the component's top-left corner
     * @param y the y-coordinate offset from the component's top-left corner
     * @throws SnElementNotFoundException if the element is not displayed
     */
    protected void doubleClickAt(int x, int y) {
        final Actions actions = new Actions(Selion.driver());

        actions.moveToElement(this.scrolledElement(), x, y).doubleClick().perform();
    }

    /**
     * Moves focus to this component after scrolling it into view.
     *
     * <p>
     * This method can be used to focus on a component before performing keyboard interactions or
     * to ensure the component is visible on the page.
     * 
     */
    protected void focus() {
        new Actions(Selion.driver())
                .moveToElement(this.scrolledElement())
                .perform();
    }

    /**
     * Returns the text from this component.
     *
     * @return the text content of this component
     */
    public String text() {
        return this.existingElement().getText().trim();
    }

    /**
     * Returns a unique key or identifier for this component.
     *
     * <p>
     * By default, this method returns the same value as {@link #text()}. Override this method to provide
     * a custom text representation for specific component types.
     * 
     *
     * <p>
     * The string returned by {@code key()} is used as a key value in {@link SnComponentCollection},
     * so overriding it to return a proper unique value simpplifies getting a component from the collection using
     * {@link SnComponentCollection#entry(String)}.
     * 
     *
     * @return a unique key identifying this component instance
     *
     * @see SnComponentCollection#entry(String)
     */
    protected String key() {
        return this.text();
    }

    /**
     * Returns the ID attribute of the component, if it exists.
     *
     * @return an {@link Optional} containing the ID attribute value, or empty if not present
     */
    public final Optional<String> id() {
        String id;

        return (id = this.existingElement().getAttribute("id")) == null ? Optional.empty() : Optional.of(id);
    }

    /**
     * Returns the inner HTML text of the component.
     *
     * <p>
     * This includes all HTML markup and text of all child elements.
     * 
     *
     * @return the inner HTML content of this component
     */
    protected final String innerHtml() {
        return this.existingElement().getAttribute("innerHTML");
    }

    /**
     * Returns the text content of this component node only, excluding all texts in sub-nodes.
     *
     * <p>
     * This method parses the HTML and removes all nested HTML tags, returning only the direct text
     * content of this element.
     * 
     *
     * @return the text content of this component excluding child elements
     * @throws SnInvalidHtmlException if the HTML cannot be properly parsed
     */
    protected final String ownText() {
        return SeOwnText.removeHtml(this.innerHtml());
    }

    /**
     * Returns the inner text of the component.
     *
     * <p>
     * This is the text visible to the user, excluding hidden elements and excluding HTML markup.
     * 
     *
     * @return the inner text content of this component
     */
    protected final String innerText() {
        return this.existingElement().getAttribute("innerText");
    }

    /**
     * Returns a list of classes of this component.
     * @return classes in this component.
     */
    protected final List<String> cssClasses() {
        final String classes = this.attr("class").orElse("").trim();

        return Arrays.stream(classes.split("\\s+")).toList();
    }

    /**
     * Returns the HTML tag name of the component.
     *
     * @return the tag name (e.g., "div", "button", "input") in lowercase
     */
    public String tag() {
        return this.existingElement().getTagName();
    }

    /**
     * Returns the specified HTML attribute value of the component, if it exists.
     *
     * @param name the name of the attribute to retrieve
     * @return an {@link Optional} containing the attribute value, or empty if not present
     */
    public Optional<String> attr(String name) {
        String a;

        return (a = this.existingElement().getDomAttribute(name)) == null ? Optional.empty() : Optional.of(a);
    }



    /**
     * Returns the wait timeout for this component in milliseconds.
     *
     * <p>
     * The wait timeout determines how long the component will wait for operations like
     * element existence, visibility, or animation to complete before throwing a timeout exception.
     * 
     *
     * <p>
     * The default wait timeout is as defined in {@link SelionConfig}. It can be changed only for this component
     * by overriding this method and providing another value.
     * 
     *
     * @return the wait timeout in milliseconds
     */
    protected long waitTimeout() {
        return SelionConfig.config().waitTimeoutMilliseconds();
    }

    /**
     * Returns {@link Actions}.
     *
     * @return {@link Actions} object.
     */
    protected final Actions actions() {
        return new Actions(Selion.driver());
    }

    /**
     * A static utility class that computes and extracts "own text" from HTML content.
     *
     * <p>
     * This class provides functionality to remove HTML tags and markup from inner HTML content,
     * returning only the direct text content of an element while handling self-closing and
     * void tags appropriately.
     * 
     */
    private static class SeOwnText {
        private static final Set<String> VOIDED_TAGS = Set.of("br", "img", "hr", "input", "meta", "link", "source", "area", "base", "col", "embed", "param", "track", "wbr");
        private static final Pattern TAG_PATTERN = Pattern.compile("<(\\w+)[^>]*?>([^<^>]*?)</\\s*?\\1\\s*?>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        private static final Pattern ANY_TAG = Pattern.compile("<(\\w+)[^>]*?>|</\\s*?\\w+\\s*?>|<\\w+\\s*?/>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        /**
         * Removes HTML tags and markup from the provided HTML text, returning only the text content.
         *
         * <p>
         * This method:
         * <ul>
         *   <li>Normalizes whitespace</li>
         *   <li>Removes void/self-closing tags</li>
         *   <li>Recursively removes paired HTML tags</li>
         *   <li>Validates that all HTML is properly removed</li>
         * </ul>
         * 
         *
         * @param originalHtmlText the HTML text to process
         * @return the extracted text content without HTML markup
         * @throws SnInvalidHtmlException if the HTML cannot be properly parsed
         */
        private static String removeHtml(String originalHtmlText) {
            Matcher matcher;
            String resultText = originalHtmlText.replaceAll("\\s+", " ");

            for (String voidedPattern : voidedPatterns()) {
                resultText = resultText.replaceAll(voidedPattern, "");
            }

            while ((matcher = TAG_PATTERN.matcher(resultText)).find()) {
                resultText = matcher.replaceFirst("");
            }

            if (ANY_TAG.matcher(resultText).find()) {
                throw new SnInvalidHtmlException("Cannot parse HTML text: \n\n" + originalHtmlText);
            }

            return resultText.trim();
        }

        /**
         * Generates regex patterns for void/self-closing HTML tags.
         *
         * @return a list of regex patterns matching void tag patterns
         */
        private static List<String> voidedPatterns() {
            return VOIDED_TAGS.stream().map(s -> "<" + s + "[^>]*?>|<\" + s + \"[^>]*?/>").toList();
        }
    }
}