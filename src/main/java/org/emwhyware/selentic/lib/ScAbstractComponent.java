package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.selentic.lib.config.SelenticConfig;
import org.emwhyware.selentic.lib.exception.ScComponentCreationException;
import org.emwhyware.selentic.lib.exception.ScComponentWaitException;
import org.emwhyware.selentic.lib.exception.ScElementNotFoundException;
import org.emwhyware.selentic.lib.selector.*;
import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.emwhyware.selentic.lib.util.ScWait;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

/**
 * {@code ScAbstractComponent} is the abstract base class for all components and pages.
 * It provides a comprehensive methods for creating, locating, and interacting with UI components through a
 * fluent, declarative syntax.
 *
 * <p>
 * This class serves as a foundation for building component-based test automation by providing:
 * <ul>
 *   <li>Selector builders for XPath and CSS selector construction</li>
 *   <li>Convenient component factory methods for common HTML elements</li>
 *   <li>Component collection management</li>
 *   <li>Frame content handling with automatic frame switching</li>
 *   <li>Dialog component management</li>
 *   <li>Component and page lifecycle management</li>
 * </ul>
 *
 *
 * <p>
 * <strong>Component Factory Pattern:</strong> This class provides numerous factory methods prefixed with
 * {@code $} (for single components) or {@code $$} (for component collections) that enable intuitive
 * component creation with automatic initialization.
 * 
 *
 * <p>
 * <strong>Selector Building:</strong> Protected methods provide access to selector builders and properties
 * for constructing complex selectors with fluent, readable syntax. Selector related method names start with
 * the underscore {@code _} character, making it easy to find.
 * 
 *
 * @see ScComponent
 * @see ScAbstractPage
 */
public abstract class ScAbstractComponent {
    private static final Logger LOG = ScLogHandler.logger(ScAbstractComponent.class);
    private static final ScSelectorPackageAccessor SELECTOR_ACCESSOR = ScSelectorPackageAccessor.instance();

    protected enum ScWaitCondition {
        ToExist, ToBeDisplayed, ToBeEnabled, ToBeDisabled, ToBeHidden, ToNotExist, ToStopAnimating;
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
     * The default wait timeout is as defined in {@link SelenticConfig}. It can be changed only for this component
     * by overriding this method and providing another value.
     *
     *
     * @return the wait timeout in milliseconds
     */
    protected long waitTimeout() {
        return SelenticConfig.config().waitTimeoutMilliseconds();
    }

    /**
     * Waits for the given component to meet the condition.
     *
     * <p>
     * This method blocks until the given component meets the given condition.
     *
     *
     * @throws ScComponentWaitException if the element does not meet the condition within the timeout period
     */
    protected final void waitForComponent(@NonNull ScComponent component, @NonNull ScWaitCondition waitType) {
        switch (waitType) {
            case ToExist -> {
                ScWait.waitUntil(waitTimeout(), component::exists, ex -> new ScComponentWaitException("Component does not exist.", ex));
            }
            case ToBeDisplayed -> {
                ScWait.waitUntil(waitTimeout(), component::isDisplayed, ex -> new ScComponentWaitException("Component is not displayed.", ex));
            }
            case ToBeEnabled -> {
                if (component instanceof ScClickableComponent clickableComponent) {
                    ScWait.waitUntil(waitTimeout(), clickableComponent::isEnabled, ex -> new ScComponentWaitException("Component is not enabled.", ex));
                }
            }
            case ToBeDisabled -> {
                if (component instanceof ScClickableComponent clickableComponent) {
                    ScWait.waitUntil(() -> !clickableComponent.isEnabled(), ex -> new ScComponentWaitException("Component is not disabled.", ex));
                }
            }
            case ToBeHidden -> {
                ScWait.waitUntil(waitTimeout(), () -> !component.isDisplayed(), ex -> new ScComponentWaitException("Component is still displayed.", ex));
            }
            case ToNotExist -> {
                ScWait.waitUntil(waitTimeout(), () -> !component.exists(), ex -> new ScComponentWaitException("Component still exists.", ex));
            }
            case ToStopAnimating -> {
                ScWait.waitUntil(waitTimeout(), component::isDisplayed, ex -> new ScComponentWaitException("Component is not displayed.", ex));
                ScWait.waitUntil(waitTimeout(), () -> {
                    final Boolean returned = (Boolean) Selentic.executeScript(
                            """
                                        let e = arguments[0];
                                        return !e.getAnimations().some(a => a.playState === 'running' || a.playState === 'pending');
                                    """,
                            this
                    );
                    return returned != null && returned;
                }, ex -> new ScComponentWaitException("Component is still animating.", ex)
                );
            }
        }
    }

    /**
     * Creates a negation of the provided selector property.
     *
     * <p>
     * This method inverts the logic of a selector property, useful for expressing negative
     * conditions in XPath or CSS selector construction.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _not(_cssClasses("disabled"))  // Matches elements without 'disabled' class
     * }</pre>
     *
     *
     * @param selectorProperty the selector property to negate
     * @return a {@link ScSelectorNotProperty} that represents the negation
     */
    protected static ScSelectorNotProperty _not(@NonNull ScSelectorProperty selectorProperty) {
        return SELECTOR_ACCESSOR._not(selectorProperty);
    }

    /**
     * Creates a selector condition for matching HTML attributes.
     *
     * <p>
     * This method provides access to attribute-based selector conditions, allowing
     * you to build selectors based on any HTML attribute.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _attr("data-testid").is("submit-button")
     * _attr("aria-label").contains("search")
     * }</pre>
     *
     *
     * @param attribute the name of the HTML attribute
     * @return a {@link ScSelectorAttributeCondition} for building attribute-based selectors
     */
    protected static ScSelectorAttributeCondition _attr(@NonNull String attribute) {
        return SELECTOR_ACCESSOR._attr(attribute);
    }

    /**
     * Creates a selector property for matching CSS classes.
     *
     * <p>
     * This method builds a selector property that matches elements containing the specified
     * CSS classes. Multiple classes can be provided to match elements with all specified classes.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _cssClasses("button", "primary")  // Matches elements with both 'button' and 'primary' classes
     * _cssClasses("active")             // Matches elements with 'active' class
     * }</pre>
     *
     *
     * @param cssClasses one or more CSS class names to match
     * @return a {@link ScSelectorCssClassesProperty} for building CSS class-based selectors
     */
    protected static ScSelectorCssClassesProperty _cssClasses(@NonNull String... cssClasses) {
        return SELECTOR_ACCESSOR._cssClasses(cssClasses);
    }

    /**
     * Creates a selector property for matching HTML tag names.
     *
     * <p>
     * This method builds a selector property that matches elements with the specified tag name.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _tag("button")      // Matches button elements
     * _tag("div")         // Matches div elements
     * _tag("input")       // Matches input elements
     * }</pre>
     *
     *
     * @param tag the HTML tag name to match
     * @return a {@link ScSelectorTagProperty} for building tag-based selectors
     */
    protected static ScSelectorTagProperty _tag(@NonNull String tag) {
        return SELECTOR_ACCESSOR._tag(tag);
    }

    /**
     * Creates a selector condition for matching HTML id attributes.
     *
     * <p>
     * This method provides access to id-based selector conditions without specifying a value,
     * useful for checking if an element has an id attribute.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _id().isPresent()   // Matches elements that have an id attribute
     * }</pre>
     *
     *
     * @return a {@link ScSelectorAttributeCondition} for building id-based selectors
     */
    protected static ScSelectorAttributeCondition _id() {
        return SELECTOR_ACCESSOR._id();
    }

    /**
     * Creates a selector property for matching a specific HTML id attribute value. This method is only valid for
     * CSS selector.
     *
     * <p>
     * This method builds a selector property that matches elements with the specified id value.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _id("submit-button")    // Matches element with id="submit-button"
     * _id("user-input")       // Matches element with id="user-input"
     * }</pre>
     *
     *
     * @param id the id attribute value to match
     * @return a {@link ScSelectorIdProperty} for building id-based selectors
     */
    protected static ScSelectorIdProperty _id(@NonNull String id) {
        return SELECTOR_ACCESSOR._id(id);
    }

    /**
     * Creates a selector condition for matching HTML name attributes.
     *
     * <p>
     * This method provides access to name-based selector conditions, useful for matching
     * form input elements by their name attribute.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _name().is("email")     // Matches elements with name="email"
     * }</pre>
     *
     *
     * @return a {@link ScSelectorAttributeCondition} for building name-based selectors
     */
    protected static ScSelectorAttributeCondition _name() {
        return SELECTOR_ACCESSOR._name();
    }

    /**
     * Creates a selector condition for matching HTML type attributes.
     *
     * <p>
     * This method provides access to type-based selector conditions, primarily useful for
     * matching input elements of specific types.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _type().is("text")      // Matches input elements with type="text"
     * _type().is("checkbox")  // Matches input elements with type="checkbox"
     * }</pre>
     * 
     *
     * @return a {@link ScSelectorAttributeCondition} for building type-based selectors
     */
    protected static ScSelectorAttributeCondition _type() {
        return SELECTOR_ACCESSOR._type();
    }

    /**
     * Creates a selector condition for matching element text content. This method is only valid for XPath.
     *
     * <p>
     * This method provides access to text-based selector conditions, allowing you to match
     * elements based on their text content.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _text().is("Click Me")          // Matches elements with exact text
     * _text().contains("Click")       // Matches elements containing the text
     * _text().startsWith("Submit")    // Matches elements starting with the text
     * }</pre>
     * 
     *
     * @return a {@link ScSelectorTextCondition} for building text-based selectors
     */
    protected static ScSelectorTextCondition _text() {
        return SELECTOR_ACCESSOR._text();
    }

    /**
     * Creates a selector property for matching elements starting from a specific index. This method is only valid for XPath.
     *
     * <p>
     * This method builds a selector property that matches a range of elements starting from
     * the specified index (0-based).
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _indexFrom(0)   // Matches elements starting from the first element (index 0)
     * _indexFrom(5)   // Matches elements starting from the sixth element
     * }</pre>
     * 
     *
     * @param startIndex the starting index (0-based)
     * @return a {@link ScSelectorIndexProperty} for building index-based range selectors
     */
    protected static ScSelectorIndexProperty _indexFrom(int startIndex) {
        return SELECTOR_ACCESSOR._indexFrom(startIndex);
    }

    /**
     * Creates a selector property for matching elements up to a specific index. This method is only valid for XPath.
     *
     * <p>
     * This method builds a selector property that matches a range of elements up to
     * the specified index (inclusive, 0-based).
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _indexTo(0)     // Matches only the first element
     * _indexTo(4)     // Matches elements with indices 0 through 4
     * }</pre>
     * 
     *
     * @param endIndex the ending index (0-based, inclusive)
     * @return a {@link ScSelectorIndexProperty} for building index-based range selectors
     */
    protected static ScSelectorIndexProperty _indexTo(int endIndex) {
        return SELECTOR_ACCESSOR._indexTo(endIndex);
    }

    /**
     * Creates a selector property for matching an element at a specific index. This method is only valid for XPath.
     *
     * <p>
     * This method builds a selector property that matches exactly one element at the
     * specified index (0-based).
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _indexAt(0)     // Matches the first element
     * _indexAt(5)     // Matches the sixth element
     * _indexAt(10)    // Matches the eleventh element
     * }</pre>
     * 
     *
     * @param index the index of the element to match (0-based)
     * @return a {@link ScSelectorIndexProperty} for building exact index-based selectors
     */
    protected static ScSelectorIndexProperty _indexAt(int index) {
        return SELECTOR_ACCESSOR._indexAt(index);
    }

    /**
     * Creates a selector property for matching the first element. This method is only valid for XPath.
     *
     * <p>
     * This method is a convenience method equivalent to {@code _indexAt(0)}.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _first()    // Matches the first matching element
     * }</pre>
     *
     *
     * @return a {@link ScSelectorIndexProperty} for selecting the first element
     */
    protected static ScSelectorIndexProperty _first() {
        return SELECTOR_ACCESSOR._first();
    }

    /**
     * Creates a selector property for matching the last element. This method is only valid for XPath.
     *
     * <p>
     * This method builds a selector property that matches the last element in a set of
     * matching elements.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _last()     // Matches the last matching element
     * }</pre>
     * 
     *
     * @return a {@link ScSelectorIndexProperty} for selecting the last element
     */
    protected static ScSelectorIndexProperty _last() {
        return SELECTOR_ACCESSOR._last();
    }

    /**
     * Creates a selector property for matching an element by its nth-of-type position. This method is only valid for CSS selector.
     *
     * <p>
     * This method builds a selector property that matches elements based on their position
     * among siblings of the same type (tag name). This is useful for selecting specific
     * elements within a group of the same tag type.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _nthOfType(0)   // Matches the first element of its type
     * _nthOfType(2)   // Matches the third element of its type
     * }</pre>
     * 
     *
     * @param index the nth-of-type position (0-based)
     * @return a {@link ScSelectorNthOfTypeProperty} for building nth-of-type selectors
     * @see ScSelectorNthOfTypeProperty
     */
    protected static ScSelectorNthOfTypeProperty _nthOfType(int index) {
        return SELECTOR_ACCESSOR._nthOfType(index);
    }

    /**
     * Creates a selector property for matching an element by its nth-last-of-type position. This method is only valid for CSS selector.
     *
     * <p>
     * This method builds a selector property that matches elements based on their position
     * among siblings of the same type, counting from the last element backwards. This is useful
     * for selecting specific elements within a group of the same tag type from the end.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _nthLastOfType(0)   // Matches the last element of its type
     * _nthLastOfType(1)   // Matches the second-to-last element of its type
     * }</pre>
     * 
     *
     * @param index the nth-last-of-type position counting from the end (0-based)
     * @return a {@link ScSelectorNthLastOfTypeProperty} for building nth-last-of-type selectors
     * @see ScSelectorNthLastOfTypeProperty
     */
    protected static ScSelectorNthLastOfTypeProperty _nthLastOfType(int index) {
        return SELECTOR_ACCESSOR._nthLastOfType(index);
    }

    /**
     * Creates a selector property for matching the first element of its type. This method is only valid for CSS selector.
     *
     * <p>
     * This method builds a selector property that matches the first element among siblings
     * of the same type (tag name). This is equivalent to {@code _nthOfType(0)}.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _firstOfType()   // Matches the first div, first p, etc.
     * }</pre>
     * 
     *
     * @return a {@link ScSelectorFirstOfTypeProperty} for building first-of-type selectors
     * @see ScSelectorFirstOfTypeProperty
     */
    protected static ScSelectorFirstOfTypeProperty _firstOfType() {
        return SELECTOR_ACCESSOR._firstOfType();
    }

    /**
     * Creates a selector property for matching the last element of its type. This method is only valid for CSS selector.
     *
     * <p>
     * This method builds a selector property that matches the last element among siblings
     * of the same type (tag name). This is equivalent to {@code _nthLastOfType(0)}.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _lastOfType()   // Matches the last div, last p, etc.
     * }</pre>
     * 
     *
     * @return a {@link ScSelectorLastOfTypeProperty} for building last-of-type selectors
     * @see ScSelectorLastOfTypeProperty
     */
    protected static ScSelectorLastOfTypeProperty _lastOfType() {
        return SELECTOR_ACCESSOR._lastOfType();
    }

    /**
     * Creates a selector property for matching an element by its nth-child position. This method is only valid for CSS selector.
     *
     * <p>
     * This method builds a selector property that matches elements based on their position
     * among all siblings (regardless of tag type). This is useful for selecting specific
     * elements by their order in the parent element.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _nthChild(0)   // Matches the first child element
     * _nthChild(2)   // Matches the third child element
     * }</pre>
     * 
     *
     * @param index the nth-child position (0-based)
     * @return a {@link ScSelectorNthChildProperty} for building nth-child selectors
     * @see ScSelectorNthChildProperty
     */
    protected static ScSelectorNthChildProperty _nthChild(int index) {
        return SELECTOR_ACCESSOR._nthChild(index);
    }

    /**
     * Creates a selector property for matching an element by its nth-last-child position. This method is only valid for CSS selector.
     *
     * <p>
     * This method builds a selector property that matches elements based on their position
     * among all siblings, counting from the last element backwards (regardless of tag type).
     * This is useful for selecting specific elements by their reverse order in the parent element.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _nthLastChild(0)   // Matches the last child element
     * _nthLastChild(1)   // Matches the second-to-last child element
     * }</pre>
     * 
     *
     * @param index the nth-last-child position counting from the end (0-based)
     * @return a {@link ScSelectorNthLastChildProperty} for building nth-last-child selectors
     * @see ScSelectorNthLastChildProperty
     */
    protected static ScSelectorNthLastChildProperty _nthLastChild(int index) {
        return SELECTOR_ACCESSOR._nthLastChild(index);
    }

    /**
     * Creates a selector property for matching the first child element. This method is only valid for CSS selector.
     *
     * <p>
     * This method builds a selector property that matches the first element among all siblings
     * (regardless of tag type). This is equivalent to {@code _nthChild(0)}.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _firstChild()   // Matches the first child in a parent element
     * }</pre>
     * 
     *
     * @return a {@link ScSelectorFirstChildProperty} for building first-child selectors
     * @see ScSelectorFirstChildProperty
     */
    protected static ScSelectorFirstChildProperty _firstChild() {
        return SELECTOR_ACCESSOR._firstChild();
    }

    /**
     * Creates a selector property for matching the last child element. This method is only valid for CSS selector.
     *
     * <p>
     * This method builds a selector property that matches the last element among all siblings
     * (regardless of tag type). This is equivalent to {@code _nthLastChild(0)}.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _lastChild()   // Matches the last child in a parent element
     * }</pre>
     * 
     *
     * @return a {@link ScSelectorLastChildProperty} for building last-child selectors
     * @see ScSelectorLastChildProperty
     */
    protected static ScSelectorLastChildProperty _lastChild() {
        return SELECTOR_ACCESSOR._lastChild();
    }

    /**
     * Creates a generic component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, ScGenericComponent.class)}.
     * Use this when you need a basic component without specific functionality.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector HEADER_TEXT = _cssSelector.descendant(_cssClasses("header"));
     * public final ScGenericComponent headerText = $genericComponent(HEADER_TEXT);
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the component
     * @return a {@link ScGenericComponent} wrapping the located element
     */
    protected ScGenericComponent $genericComponent(@NonNull ScSelector selector) {
        return $component(selector, ScGenericComponent.class);
    }

    /**
     * Creates a textbox component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, ScTextbox.class)}.
     * Use this for input elements where text entry is needed.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector USERNAME_TEXTBOX = _cssSelector.descendant(_id("username"));
     * public final ScTextbox usernameTextbox = $textbox(USERNAME_TEXTBOX);
     *
     * // In test code
     * page.usernameTextbox.enterText("john_doe");
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the textbox
     * @return a {@link ScTextbox} component for text input interaction
     */
    protected ScTextbox $textbox(@NonNull ScSelector selector) {
        return $component(selector, ScTextbox.class);
    }

    /**
     * Creates a checkbox component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, ScCheckbox.class)}.
     * Use this for checkbox input elements.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector REMEMBER_ME_CHECKBOX = _cssSelector.descendant(_id("rememberMe"));
     * public final ScCheckbox rememberMeCheckbox = $checkbox(REMEMBER_ME_CHECKBOX);
     *
     * // In test code
     * page.rememberMeCheckbox.select();
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the checkbox
     * @return a {@link ScCheckbox} component for checkbox interaction
     */
    protected ScCheckbox $checkbox(@NonNull ScSelector selector) {
        return $component(selector, ScCheckbox.class);
    }

    /**
     * Creates a dropdown component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, ScDropdown.class)}.
     * Use this for HTML select elements with single selection.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector COUNTRY_DROPDOWN = _cssSelector.descendant(_id("country"));
     * public final ScDropdown countryDropdown = $dropdown(COUNTRY_DROPDOWN);
     *
     * // In test code
     * page.countryDropdown.select("United States");
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the dropdown
     * @return a {@link ScDropdown} component for dropdown selection
     */
    protected ScDropdown $dropdown(@NonNull ScSelector selector) {
        return $component(selector, ScDropdown.class);
    }

    /**
     * Creates a multi-select component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, ScMultiSelect.class)}.
     * Use this for HTML select elements with multiple selection enabled.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector INTERESTS_MULTISELECT = _cssSelector.descendant(_id("interests"));
     * public final ScMultiSelect interestsMultiSelect = $multiSelect(INTERESTS_MULTISELECT);
     *
     * // In test code
     * page.interestsMultiSelect.select("Sports", "Music", "Reading");
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the multi-select
     * @return a {@link ScMultiSelect} component for multi-select interaction
     */
    protected ScMultiSelect $multiSelect(@NonNull ScSelector selector) {
        return $component(selector, ScMultiSelect.class);
    }

    /**
     * Creates a link component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, ScLink.class)}.
     * Use this for anchor (&lt;a&gt;) elements.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector LOGOUT_LINK = _cssSelector.descendant(_cssClasses("Logout"));
     * public final ScLink logoutLink = $link(LOGOUT_LINK);
     *
     * // In test code
     * page.logoutLink.click();
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the link
     * @return a {@link ScLink} component for link interaction
     */
    protected ScLink $link(@NonNull ScSelector selector) {
        return $component(selector, ScLink.class);
    }

    /**
     * Creates a button component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, ScButton.class)}.
     * Use this for button (&lt;button&gt;) elements or input button (&lt;input type="button|submit|reset"&gt;) elements.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector LOGOUT_BUTTON = _cssSelector.descendant(_id("Logout"));
     * public final ScButton logoutButton = $button(LOGOUT_BUTTON);
     *
     * // In test code
     * page.logoutLink.click();
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the link
     * @return a {@link ScButton} component for button interaction
     */
    protected ScButton $button(@NonNull ScSelector selector) {
        return $component(selector, ScButton.class);
    }

    /**
     * Creates an image component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, ScImage.class)}.
     * Use this for image (&lt;img&gt;) elements.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector LOGO_IMAGE = _cssSelector.descendant(_className("logo"));
     * public final ScImage logoImage = $image(LOGO_IMAGE);
     *
     * // In test code
     * String imageSrc = page.logoImage.source();
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the image
     * @return a {@link ScImage} component for image interaction
     */
    protected ScImage $image(@NonNull ScSelector selector) {
        return $component(selector, ScImage.class);
    }

    /**
     * Creates a radio button group component with the specified selector.
     *
     * <p>
     * This method creates a collection of radio buttons that are treated as a group.
     * It's equivalent to {@code $$components(selector, ScRadioButton.class, ScRadioButtonGroup.class)}.
     * Use this for groups of radio buttons that share the same name attribute.
     * 
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector GENDER_RADIO_BUTTONS = _cssSelector.descendant(_attr("name").is("gender"));
     * public final ScRadioButtonGroup genderRadioButtons = $radioButtons(GENDER_RADIO_BUTTONS);
     *
     * // In test code
     * page.genderRadioButtons.select("male");
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the radio buttons
     * @return a {@link ScRadioButtonGroup} for managing radio button groups
     */
    protected ScRadioButtonGroup<ScRadioButton> $radioButtons(@NonNull ScSelector selector) {
        return $$components(selector, ScRadioButton.class, ScRadioButtonGroup.class);
    }

    /**
     * Hook method for waiting for component or page display.
     *
     * <p>
     * This method is called by the framework to wait for a component or page to be displayed.
     * Subclasses can override this method to implement custom waiting logic specific to their needs.
     * 
     *
     * <p>
     * By default, this method does nothing. Subclasses should override it to define what needs to be
     * waited for before the component/page is considered ready for interaction.
     * 
     *
     * @see ScComponent#waitForDisplayed()
     * @see ScAbstractPage#waitForComponent(ScComponent)
     */
//    protected void waitForDisplayed() {}

    /**
     * Creates a component instance of the specified type with the given selector.
     *
     * <p>
     * This method is the primary factory method for creating component instances. It:
     * <ul>
     *   <li>Instantiates the component class via reflection</li>
     *   <li>Sets the selector on the component</li>
     *   <li>Establishes the parent-child relationship (caller component and owner page)</li>
     *   <li>Handles any instantiation errors by throwing {@link ScComponentCreationException}</li>
     * </ul>
     * 
     *
     * <p>
     * The component is not displayed or validated at this point; it is only instantiated and configured.
     * The actual element location and validation occur lazily when the component is first accessed.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector LOGIN_BUTTON = _cssSelector.descendant(_id("login"));
     * public final ScButton loginButton = $component(LOGIN_BUTTON, ScButton.class);
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the component element
     * @param componentType the class of the component to create; must extend from {@link ScComponent}
     * @param <T> the component type
     * @return a new instance of the specified component type, configured with the selector
     * @throws ScComponentCreationException if the component cannot be instantiated
     *
     * @see #$component(ScSelector, Class, ScAbstractComponent)
     */
    protected <T extends ScComponent> T $component(@NonNull ScSelector selector, @NonNull Class<T> componentType) {
        return $component(selector, componentType, null);
    }

    /**
     * Creates a component instance of the specified type with the given selector and containing object.
     *
     * <p>
     * This method creates a component that may be an inner class of a containing object. It:
     * <ul>
     *   <li>Instantiates the component class via reflection, passing the containing object if provided</li>
     *   <li>Sets the selector on the component</li>
     *   <li>Handles any instantiation errors by throwing {@link ScComponentCreationException}</li>
     * </ul>
     * 
     *
     * <p>
     * This method is used when instantiating a component class that is an inner class. The containing object must be provided to allow proper
     * instantiation.
     * 
     *
     * @param selector the {@link ScSelector} for locating the component element
     * @param componentType the class of the component to create; must extend from {@link ScComponent}
     * @param containingObject the object that contains this component as an inner class
     * @param <T> the component type
     * @return a new instance of the specified component type, configured with the selector
     * @throws ScComponentCreationException if the component cannot be instantiated
     *
     * @see #$component(ScSelector, Class)
     */
    protected <T extends ScComponent> T $component(@NonNull ScSelector selector, @NonNull Class<T> componentType, @Nullable ScAbstractComponent containingObject) {
        try {
            T $component;

            $component = containingObject == null ? componentType.getDeclaredConstructor().newInstance() : componentType.getDeclaredConstructor(containingObject.getClass()).newInstance(containingObject);
            $component.setSelector(selector);
            $component.setCallerComponent(this);
            return $component;
        } catch (Exception ex) {
            throw new ScComponentCreationException(ex);
        }
    }

    /**
     * Creates a collection of components of the specified type with the given selector.
     *
     * <p>
     * This method creates a {@link ScComponentCollection} that manages multiple component instances
     * matching the same selector. It:
     * <ul>
     *   <li>Creates a new collection instance</li>
     *   <li>Configures the selector and component type</li>
     * </ul>
     * 
     *
     * <p>
     * Components in the collection are lazily instantiated as they are accessed. The collection
     * provides convenient methods for iterating, filtering, and accessing individual components.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector TABLE_ROWS = _cssSelector.descendant(_className("table-row"));
     * public final ScComponentCollection<ScTableRow> rows = $$components(TABLE_ROWS, ScTableRow.class);
     *
     * // In test code
     * for (final ScTableRow row : page.rows) {
     *     System.out.println(row.text());
     * }
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the component elements
     * @param componentType the class of the components to create; must extend from {@link ScComponent}
     * @param <T> the component type
     * @return a {@link ScComponentCollection} managing components of the specified type
     *
     * @see #$$components(ScSelector, Class, ScAbstractComponent)
     * @see #$$components(ScSelector, Class, Class)
     */
    protected <T extends ScComponent> ScComponentCollection<T> $$components(@NonNull ScSelector selector, @NonNull Class<T> componentType) {
        ScComponentCollection<T> $$components = new ScComponentCollection<>();

        $$components.setSelector(selector);
        $$components.setComponentType(componentType);
        $$components.setCallerComponent(this);

        return $$components;
    }

    /**
     * Creates a collection of inner class components with the given selector and containing object.
     *
     * <p>
     * This method creates a {@link ScComponentCollection} for components that are inner classes
     * of a containing object. It:
     * <ul>
     *   <li>Creates a new collection instance</li>
     *   <li>Configures the selector, component type, and containing object</li>
     * </ul>
     * 
     *
     * <p>
     * Use this when your collection items are inner classes that require a reference to their
     * containing object for instantiation.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector ITEMS = _cssSelector.className("item");
     * public final ScComponentCollection<CartItem> items = $$components(ITEMS, CartItem.class, this);
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the component elements
     * @param componentType the class of the components to create; must extend from {@link ScComponent}
     * @param containingObject the object that contains these components if they're inner classes
     * @param <T> the component type
     * @return a {@link ScComponentCollection} managing inner class components
     *
     * @see #$$components(ScSelector, Class)
     * @see #$$components(ScSelector, Class, Class)
     */
    protected <T extends ScComponent> ScComponentCollection<T> $$components(@NonNull ScSelector selector, @NonNull Class<T> componentType, ScAbstractComponent containingObject) {
        ScComponentCollection<T> $$components = new ScComponentCollection<>();

        $$components.setSelector(selector);
        $$components.setComponentType(componentType);
        $$components.setContainingObject(containingObject);
        $$components.setCallerComponent(this);

        return $$components;
    }

    /**
     * Creates a custom collection type for components with the given selector.
     *
     * <p>
     * This method allows you to specify a custom {@link ScComponentCollection} subclass to manage
     * components. It:
     * <ul>
     *   <li>Instantiates the custom collection class via reflection</li>
     *   <li>Configures the selector and component type</li>
     *   <li>Handles instantiation errors by throwing {@link ScComponentCreationException}</li>
     * </ul>
     * 
     *
     * <p>
     * Use this when you need custom collection behavior, such as filtering, searching, or
     * specialized access patterns specific to your test domain.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * public class MenuItemCollection extends ScComponentCollection<MenuItem> {
     *  ...
     * }
     *
     * private static final ScCssSelector MENU_ITEMS = _cssSelector.descendant(_className("menu-item"));
     * public final MenuItemCollection menu = $$components(MENU_ITEMS, MenuItem.class, MenuItemCollection.class);
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the component elements
     * @param componentType the class of the components to create; must extend from {@link ScComponent}
     * @param componentCollectionType the custom collection class; must extend from {@link ScComponentCollection}
     * @param <T> the component type
     * @param <R> the custom collection type
     * @return a collection instance of the specified type, configured with the selector and component type
     * @throws ScComponentCreationException if the collection cannot be instantiated
     *
     * @see #$$components(ScSelector, Class)
     * @see #$$components(ScSelector, Class, ScAbstractComponent)
     */
    protected <T extends ScComponent, R extends ScComponentCollection<T>> R $$components(@NonNull ScSelector selector, @NonNull Class<T> componentType, @NonNull Class<R> componentCollectionType) {
        try {
            R $$components;

            $$components = componentCollectionType.getDeclaredConstructor().newInstance();
            $$components.setSelector(selector);
            $$components.setComponentType(componentType);
            $$components.setCallerComponent(this);

            return $$components;
        } catch (Exception ex) {
            throw new ScComponentCreationException(ex);
        }
    }

    /**
     * Handles frame content with automatic frame switching and context management.
     *
     * <p>
     * This method provides a clean, safe way to interact with HTML frame content. It:
     * <ul>
     *   <li>Waits for the frame to be displayed</li>
     *   <li>Automatically switches the WebDriver context to the frame</li>
     *   <li>Waits for the frame content to be fully loaded</li>
     *   <li>Executes the provided action within the frame context</li>
     *   <li>Automatically switches back to the parent frame (even if an exception occurs)</li>
     * </ul>
     * 
     *
     * <p>
     * The try-finally pattern ensures that the WebDriver context is always restored to the
     * parent frame, even if an error occurs during frame content interaction.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * private static final ScCssSelector IFRAME = _cssSelector.id("embedded-content");
     *
     * @Override
     * public final void inEmbeddedFrameContentFrame(ScFrameAction<EmbeddedFrameContent> predicate) {
     *     $frame(IFRAME, EmbeddedFrameContent.class, predicate);
     * }
     * }</pre>
     * 
     *
     * @param frameSelector the {@link ScSelector} for locating the frame element
     * @param frameContentType the class representing the frame content; must extend from {@link ScFrameContent}
     * @param predicate the {@link ScFrameAction} to execute within the frame context
     * @param <T> the frame content type
     * @throws ScComponentCreationException if the frame content cannot be instantiated
     * @throws ScElementNotFoundException if the frame cannot be found or displayed
     *
     * @see #$frame(ScSelector, Class, ScAbstractComponent, ScFrameAction)
     */
    protected <T extends ScFrameContent> void $frame(@NonNull ScSelector frameSelector, @NonNull Class<T> frameContentType, @NonNull ScFrameAction<T> predicate) {
        $frame(frameSelector, frameContentType, null, predicate);
    }

    /**
     * Handles frame content with automatic frame switching and context management for inner class frames.
     *
     * <p>
     * This method provides a clean, safe way to interact with frame content that may be defined as
     * an inner class. It:
     * <ul>
     *   <li>Creates the frame content instance (with containing object support if provided)</li>
     *   <li>Waits for the frame to be displayed</li>
     *   <li>Automatically switches the WebDriver context to the frame</li>
     *   <li>Waits for the frame content to be fully loaded</li>
     *   <li>Executes the provided action within the frame context</li>
     *   <li>Automatically switches back to the parent frame (via finally block)</li>
     * </ul>
     * 
     *
     * <p>
     * The try-finally pattern ensures that the WebDriver context is always restored to the
     * parent frame, preventing orphaned frame context issues.
     * 
     *
     * @param frameSelector the {@link ScSelector} for locating the frame element
     * @param frameContentType the class representing the frame content; must extend from {@link ScFrameContent}
     * @param containingObject the object that contains the frame content if it's an inner class; null otherwise
     * @param predicate the {@link ScFrameAction} to execute within the frame context
     * @param <T> the frame content type
     * @throws ScComponentCreationException if the frame content cannot be instantiated
     * @throws ScElementNotFoundException if the frame cannot be found or displayed
     *
     * @see #$frame(ScSelector, Class, ScFrameAction)
     */
    protected <T extends ScFrameContent> void $frame(@NonNull ScSelector frameSelector, @NonNull Class<T> frameContentType, @Nullable ScAbstractComponent containingObject, @NonNull ScFrameAction<T> predicate) {
        final WebDriver webDriver = Selentic.driver();
        final ScFrame $frame = this.$component(frameSelector, ScFrame.class);
        T $frameContent;

        try {
            if (containingObject == null) {
                $frameContent = frameContentType.getDeclaredConstructor().newInstance();
            } else {
                $frameContent = frameContentType.getDeclaredConstructor(containingObject.getClass()).newInstance(containingObject);
            }
        } catch (Exception ex) {
            throw new ScComponentCreationException(ex);
        }

        try {
            waitForComponent($frame, ScWaitCondition.ToBeDisplayed);
            webDriver.switchTo().frame($frame.scrolledElement());

            $frameContent.waitForPage();
            predicate.inFrame($frameContent);
        } finally {
            webDriver.switchTo().parentFrame();
        }
    }

    /**
     * Handles dialog component interaction with automatic display and visibility verification.
     *
     * <p>
     * This method provides a clean, safe way to interact with dialog components. It:
     * <ul>
     *   <li>Creates the dialog component instance</li>
     *   <li>Waits for the dialog to be displayed</li>
     *   <li>Logs the dialog opening event at debug level</li>
     *   <li>Executes the provided action within the dialog context</li>
     *   <li>Logs the dialog closing event at debug level</li>
     *   <li>Waits for the dialog to be hidden</li>
     * </ul>
     * 
     *
     * <p>
     * This method is useful for testing modal dialogs, alerts, and other popup components
     * that appear and disappear as part of user workflows.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * private static final ScXPath SANITYTEST_DIALOG = _xpath.descendant("div", _id().is("sanitytest-dialog"));
     *
     * public void inSanityTestDialog(ScDialogAction<ScSanityTestDialog> predicate) {
     *     $dialog(SANITYTEST_DIALOG, ScSanityTestDialog.class, predicate);
     * }
     *
     * // In test code
     * page.inSanityTestDialog(dialog -> {
     *     dialog.okButton.click();
     * });
     * }</pre>
     * 
     *
     * @param selector the {@link ScSelector} for locating the dialog element
     * @param componentType the class of the dialog; must extend from {@link ScDialog}
     * @param predicate the {@link ScDialogAction} to execute within the dialog context
     * @param <T> the dialog component type
     * @throws ScElementNotFoundException if the dialog cannot be found or displayed
     *
     * @see #$dialog(ScSelector, Class, ScAbstractComponent, ScDialogAction)
     */
    protected <T extends ScDialog> void $dialog(@NonNull ScSelector selector, @NonNull Class<T> componentType, @NonNull ScDialogAction<T> predicate) {
        $dialog(selector, componentType, null, predicate);
    }

    /**
     * Handles dialog component interaction with automatic display and visibility verification for inner class dialogs.
     *
     * <p>
     * This method provides a clean, safe way to interact with dialog components that may be defined as
     * inner classes. It:
     * <ul>
     *   <li>Creates the dialog component instance (with containing object support if provided)</li>
     *   <li>Waits for the dialog to be displayed</li>
     *   <li>Executes the provided action within the dialog context</li>
     *   <li>Waits for the dialog to be hidden before returning</li>
     * </ul>
     * 
     *
     * <p>
     * The logging and wait operations ensure that your test code doesn't proceed until the
     * dialog has fully appeared and then fully disappeared, maintaining synchronization with
     * the application's UI state.
     * 
     *
     * @param selector the {@link ScSelector} for locating the dialog element
     * @param componentType the class of the dialog; must extend from {@link ScDialog}
     * @param containingObject the object that contains the dialog if it's an inner class; null otherwise
     * @param predicate the {@link ScDialogAction} to execute within the dialog context
     * @param <T> the dialog component type
     * @throws ScElementNotFoundException if the dialog cannot be found or displayed
     *
     * @see #$dialog(ScSelector, Class, ScDialogAction)
     */
    protected <T extends ScDialog> void $dialog(@NonNull ScSelector selector, @NonNull Class<T> componentType, @Nullable ScAbstractComponent containingObject, @NonNull ScDialogAction<T> predicate) {
        final T $dialog = containingObject == null ? this.$component(selector, componentType) : this.$component(selector, componentType, containingObject);

        $dialog.waitForDisplayedDialog();
        LOG.debug("Open dialog: {}", $dialog.getClass().getSimpleName());
        predicate.in($dialog);
        $dialog.waitForHiddenDialog();
        LOG.debug("Close dialog: {}", $dialog.getClass().getSimpleName());
    }
}