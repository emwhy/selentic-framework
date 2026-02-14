package org.selion_framework.lib;

import org.openqa.selenium.WebDriver;
import org.selion_framework.lib.exception.SnComponentCreationException;
import org.selion_framework.lib.exception.SnElementNotFoundException;
import org.selion_framework.lib.util.SnLogHandler;
import org.slf4j.Logger;

import java.util.Optional;

/**
 * {@code SnAbstractComponent} is the abstract base class for all components and pages.
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
 * </p>
 *
 * <p>
 * <strong>Component Factory Pattern:</strong> This class provides numerous factory methods prefixed with
 * {@code $} (for single components) or {@code $$} (for component collections) that enable intuitive
 * component creation with automatic initialization.
 * </p>
 *
 * <p>
 * <strong>Selector Building:</strong> Protected methods provide access to selector builders and properties
 * for constructing complex selectors with fluent, readable syntax. Selector related method names start with
 * the underscore {@code _} character, making it easy to find.
 * </p>
 *
 * @see SnComponent
 * @see SnAbstractPage
 */
public abstract class SnAbstractComponent {
    private static final Logger LOG = SnLogHandler.logger(SnAbstractComponent.class);

    /**
     * Creates a negation of the provided selector property.
     *
     * <p>
     * This method inverts the logic of a selector property, useful for expressing negative
     * conditions in XPath or CSS selector construction.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _not(_cssClasses("disabled"))  // Matches elements without 'disabled' class
     * }</pre>
     * </p>
     *
     * @param selectorProperty the selector property to negate
     * @return a {@link SnSelectorNotProperty} that represents the negation
     */
    protected SnSelectorProperty _not(SnSelectorProperty selectorProperty) {
        return new SnSelectorNotProperty(selectorProperty);
    }

    /**
     * Creates a selector condition for matching HTML attributes.
     *
     * <p>
     * This method provides access to attribute-based selector conditions, allowing
     * you to build selectors based on any HTML attribute.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _attr("data-testid").is("submit-button")
     * _attr("aria-label").contains("search")
     * }</pre>
     * </p>
     *
     * @param attribute the name of the HTML attribute
     * @return a {@link SnSelectorAttributeCondition} for building attribute-based selectors
     */
    protected static SnSelectorAttributeCondition _attr(String attribute) {
        return new SnSelectorAttributeCondition("@", attribute);
    }

    /**
     * Creates a selector property for matching CSS classes.
     *
     * <p>
     * This method builds a selector property that matches elements containing the specified
     * CSS classes. Multiple classes can be provided to match elements with all specified classes.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _cssClasses("button", "primary")  // Matches elements with both 'button' and 'primary' classes
     * _cssClasses("active")             // Matches elements with 'active' class
     * }</pre>
     * </p>
     *
     * @param cssClasses one or more CSS class names to match
     * @return a {@link SnSelectorCssClassesProperty} for building CSS class-based selectors
     */
    protected static SnSelectorCssClassesProperty _cssClasses(String... cssClasses) {
        return new SnSelectorCssClassesProperty(cssClasses);
    }

    /**
     * Creates a selector property for matching HTML tag names.
     *
     * <p>
     * This method builds a selector property that matches elements with the specified tag name.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _tag("button")      // Matches button elements
     * _tag("div")         // Matches div elements
     * _tag("input")       // Matches input elements
     * }</pre>
     * </p>
     *
     * @param tag the HTML tag name to match
     * @return a {@link SnSelectorTagProperty} for building tag-based selectors
     */
    protected static SnSelectorTagProperty _tag(String tag) {
        return new SnSelectorTagProperty(tag);
    }

    /**
     * Creates a selector condition for matching HTML id attributes.
     *
     * <p>
     * This method provides access to id-based selector conditions without specifying a value,
     * useful for checking if an element has an id attribute.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _id().isPresent()   // Matches elements that have an id attribute
     * }</pre>
     * </p>
     *
     * @return a {@link SnSelectorAttributeCondition} for building id-based selectors
     */
    protected static SnSelectorAttributeCondition _id() {
        return _attr("id");
    }

    /**
     * Creates a selector property for matching a specific HTML id attribute value. This method is only valid for
     * CSS selector.
     *
     * <p>
     * This method builds a selector property that matches elements with the specified id value.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _id("submit-button")    // Matches element with id="submit-button"
     * _id("user-input")       // Matches element with id="user-input"
     * }</pre>
     * </p>
     *
     * @param id the id attribute value to match
     * @return a {@link SnSelectorIdProperty} for building id-based selectors
     */
    protected static SnSelectorIdProperty _id(String id) {
        return new SnSelectorIdProperty(id);
    }

    /**
     * Creates a selector condition for matching HTML name attributes.
     *
     * <p>
     * This method provides access to name-based selector conditions, useful for matching
     * form input elements by their name attribute.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _name().is("email")     // Matches elements with name="email"
     * }</pre>
     * </p>
     *
     * @return a {@link SnSelectorAttributeCondition} for building name-based selectors
     */
    protected static SnSelectorAttributeCondition _name() {
        return _attr("name");
    }

    /**
     * Creates a selector condition for matching HTML type attributes.
     *
     * <p>
     * This method provides access to type-based selector conditions, primarily useful for
     * matching input elements of specific types.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _type().is("text")      // Matches input elements with type="text"
     * _type().is("checkbox")  // Matches input elements with type="checkbox"
     * }</pre>
     * </p>
     *
     * @return a {@link SnSelectorAttributeCondition} for building type-based selectors
     */
    protected static SnSelectorAttributeCondition _type() {
        return _attr("type");
    }

    /**
     * Creates a selector condition for matching element text content. This method is only valid for XPath.
     *
     * <p>
     * This method provides access to text-based selector conditions, allowing you to match
     * elements based on their text content.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _text().is("Click Me")          // Matches elements with exact text
     * _text().contains("Click")       // Matches elements containing the text
     * _text().startsWith("Submit")    // Matches elements starting with the text
     * }</pre>
     * </p>
     *
     * @return a {@link SnSelectorTextCondition} for building text-based selectors
     */
    protected static SnSelectorTextCondition _text() {
        return new SnSelectorTextCondition();
    }

    /**
     * Creates a selector property for matching elements starting from a specific index. This method is only valid for XPath.
     *
     * <p>
     * This method builds a selector property that matches a range of elements starting from
     * the specified index (0-based).
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _indexFrom(0)   // Matches elements starting from the first element (index 0)
     * _indexFrom(5)   // Matches elements starting from the sixth element
     * }</pre>
     * </p>
     *
     * @param startIndex the starting index (0-based)
     * @return a {@link SnSelectorIndexProperty} for building index-based range selectors
     */
    protected static SnSelectorIndexProperty _indexFrom(int startIndex) {
        return new SnSelectorIndexProperty(SnSelectorIndexProperty.Conditions.From, startIndex);
    }

    /**
     * Creates a selector property for matching elements up to a specific index. This method is only valid for XPath.
     *
     * <p>
     * This method builds a selector property that matches a range of elements up to
     * the specified index (inclusive, 0-based).
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _indexTo(0)     // Matches only the first element
     * _indexTo(4)     // Matches elements with indices 0 through 4
     * }</pre>
     * </p>
     *
     * @param endIndex the ending index (0-based, inclusive)
     * @return a {@link SnSelectorIndexProperty} for building index-based range selectors
     */
    protected static SnSelectorIndexProperty _indexTo(int endIndex) {
        return new SnSelectorIndexProperty(SnSelectorIndexProperty.Conditions.To, endIndex);
    }

    /**
     * Creates a selector property for matching an element at a specific index. This method is only valid for XPath.
     *
     * <p>
     * This method builds a selector property that matches exactly one element at the
     * specified index (0-based).
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _indexAt(0)     // Matches the first element
     * _indexAt(5)     // Matches the sixth element
     * _indexAt(10)    // Matches the eleventh element
     * }</pre>
     * </p>
     *
     * @param index the index of the element to match (0-based)
     * @return a {@link SnSelectorIndexProperty} for building exact index-based selectors
     */
    protected static SnSelectorIndexProperty _indexAt(int index) {
        return new SnSelectorIndexProperty(SnSelectorIndexProperty.Conditions.At, index);
    }

    /**
     * Creates a selector property for matching the first element. This method is only valid for XPath.
     *
     * <p>
     * This method is a convenience method equivalent to {@code _indexAt(0)}.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _first()    // Matches the first matching element
     * }</pre>
     * </p>
     *
     * @return a {@link SnSelectorIndexProperty} for selecting the first element
     */
    protected static SnSelectorIndexProperty _first() {
        return _indexAt(0);
    }

    /**
     * Creates a selector property for matching the last element. This method is only valid for XPath.
     *
     * <p>
     * This method builds a selector property that matches the last element in a set of
     * matching elements.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _last()     // Matches the last matching element
     * }</pre>
     * </p>
     *
     * @return a {@link SnSelectorIndexProperty} for selecting the last element
     */
    protected static SnSelectorIndexProperty _last() {
        return new SnSelectorIndexProperty(SnSelectorIndexProperty.Conditions.Last);
    }

    /**
     * Creates a selector property for matching an element by its nth-of-type position. This method is only valid for CSS selector.
     *
     * <p>
     * This method builds a selector property that matches elements based on their position
     * among siblings of the same type (tag name). This is useful for selecting specific
     * elements within a group of the same tag type.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _nthOfType(0)   // Matches the first element of its type
     * _nthOfType(2)   // Matches the third element of its type
     * }</pre>
     * </p>
     *
     * @param index the nth-of-type position (0-based)
     * @return a {@link SnSelectorNthOfTypeProperty} for building nth-of-type selectors
     */
    protected static SnSelectorNthOfTypeProperty _nthOfType(int index) {
        return new SnSelectorNthOfTypeProperty(index);
    }

    /**
     * Creates a selector property for matching an element by its nth-child position. This method is only valid for CSS selector.
     *
     * <p>
     * This method builds a selector property that matches elements based on their position
     * among siblings.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _nthChild(0)   // Matches the first element of its type
     * _nthChild(2)   // Matches the third element of its type
     * }</pre>
     * </p>
     *
     * @param index the nth-child position (0-based)
     * @return a {@link SnSelectorNthOfTypeProperty} for building nth-of-type selectors
     */
    protected static SnSelectorNthChildProperty _nthChild(int index) {
        return new SnSelectorNthChildProperty(index);
    }

    /**
     * Creates a generic component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, SnGenericComponent.class)}.
     * Use this when you need a basic component without specific functionality.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector HEADER_TEXT = _cssSelector.descendant(_cssClasses("header"));
     * public final SnGenericComponent headerText = $genericComponent(HEADER_TEXT);
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the component
     * @return a {@link SnGenericComponent} wrapping the located element
     */
    protected SnGenericComponent $genericComponent(SnSelector selector) {
        return $component(selector, SnGenericComponent.class);
    }

    /**
     * Creates a textbox component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, SnTextbox.class)}.
     * Use this for input elements where text entry is needed.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector USERNAME_TEXTBOX = _cssSelector.descendant(_id("username"));
     * public final SnTextbox usernameTextbox = $textbox(USERNAME_TEXTBOX);
     *
     * // In test code
     * page.usernameTextbox.enterText("john_doe");
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the textbox
     * @return a {@link SnTextbox} component for text input interaction
     */
    protected SnTextbox $textbox(SnSelector selector) {
        return $component(selector, SnTextbox.class);
    }

    /**
     * Creates a checkbox component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, SnCheckbox.class)}.
     * Use this for checkbox input elements.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector REMEMBER_ME_CHECKBOX = _cssSelector.descendant(_id("rememberMe"));
     * public final SnCheckbox rememberMeCheckbox = $checkbox(REMEMBER_ME_CHECKBOX);
     *
     * // In test code
     * page.rememberMeCheckbox.select();
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the checkbox
     * @return a {@link SnCheckbox} component for checkbox interaction
     */
    protected SnCheckbox $checkbox(SnSelector selector) {
        return $component(selector, SnCheckbox.class);
    }

    /**
     * Creates a dropdown component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, SnDropdown.class)}.
     * Use this for HTML select elements with single selection.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector COUNTRY_DROPDOWN = _cssSelector.descendant(_id("country"));
     * public final SnDropdown countryDropdown = $dropdown(COUNTRY_DROPDOWN);
     *
     * // In test code
     * page.countryDropdown.select("United States");
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the dropdown
     * @return a {@link SnDropdown} component for dropdown selection
     */
    protected SnDropdown $dropdown(SnSelector selector) {
        return $component(selector, SnDropdown.class);
    }

    /**
     * Creates a multi-select component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, SnMultiSelect.class)}.
     * Use this for HTML select elements with multiple selection enabled.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector INTERESTS_MULTISELECT = _cssSelector.descendant(_id("interests"));
     * public final SnMultiSelect interestsMultiSelect = $multiSelect(INTERESTS_MULTISELECT);
     *
     * // In test code
     * page.interestsMultiSelect.select("Sports", "Music", "Reading");
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the multi-select
     * @return a {@link SnMultiSelect} component for multi-select interaction
     */
    protected SnMultiSelect $multiSelect(SnSelector selector) {
        return $component(selector, SnMultiSelect.class);
    }

    /**
     * Creates a link component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, SnLink.class)}.
     * Use this for anchor (&lt;a&gt;) elements.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector LOGOUT_LINK = _cssSelector.descendant(_cssClasses("Logout"));
     * public final SnLink logoutLink = $link(LOGOUT_LINK);
     *
     * // In test code
     * page.logoutLink.click();
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the link
     * @return a {@link SnLink} component for link interaction
     */
    protected SnLink $link(SnSelector selector) {
        return $component(selector, SnLink.class);
    }

    /**
     * Creates a button component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, SnButton.class)}.
     * Use this for button (&lt;button&gt;) elements or input button (&lt;input type="button|submit|reset"&gt;) elements.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector LOGOUT_BUTTON = _cssSelector.descendant(_id("Logout"));
     * public final SnButton logoutButton = $button(LOGOUT_BUTTON);
     *
     * // In test code
     * page.logoutLink.click();
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the link
     * @return a {@link SnButton} component for button interaction
     */
    protected SnButton $button(SnSelector selector) {
        return $component(selector, SnButton.class);
    }

    /**
     * Creates an image component with the specified selector.
     *
     * <p>
     * This is a convenience method equivalent to {@code $component(selector, SnImage.class)}.
     * Use this for image (&lt;img&gt;) elements.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector LOGO_IMAGE = _cssSelector.descendant(_className("logo"));
     * public final SnImage logoImage = $image(LOGO_IMAGE);
     *
     * // In test code
     * String imageSrc = page.logoImage.source();
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the image
     * @return a {@link SnImage} component for image interaction
     */
    protected SnImage $image(SnSelector selector) {
        return $component(selector, SnImage.class);
    }

    /**
     * Creates a radio button group component with the specified selector.
     *
     * <p>
     * This method creates a collection of radio buttons that are treated as a group.
     * It's equivalent to {@code $$components(selector, SnRadioButton.class, SnRadioButtonGroup.class)}.
     * Use this for groups of radio buttons that share the same name attribute.
     * </p>
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector GENDER_RADIO_BUTTONS = _cssSelector.descendant(_attr("name").is("gender"));
     * public final SnRadioButtonGroup genderRadioButtons = $radioButtons(GENDER_RADIO_BUTTONS);
     *
     * // In test code
     * page.genderRadioButtons.select("male");
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the radio buttons
     * @return a {@link SnRadioButtonGroup} for managing radio button groups
     */
    protected SnRadioButtonGroup $radioButtons(SnSelector selector) {
        return $$components(selector, SnRadioButton.class, SnRadioButtonGroup.class);
    }

    /**
     * Hook method for waiting for component or page display.
     *
     * <p>
     * This method is called by the framework to wait for a component or page to be displayed.
     * Subclasses can override this method to implement custom waiting logic specific to their needs.
     * </p>
     *
     * <p>
     * By default, this method does nothing. Subclasses should override it to define what needs to be
     * waited for before the component/page is considered ready for interaction.
     * </p>
     *
     * @see SnComponent#waitForDisplayed()
     * @see SnAbstractPage#waitForComponent(SnComponent)
     */
    protected void waitForDisplayed() {}

    /**
     * Creates a component instance of the specified type with the given selector.
     *
     * <p>
     * This method is the primary factory method for creating component instances. It:
     * <ul>
     *   <li>Instantiates the component class via reflection</li>
     *   <li>Sets the selector on the component</li>
     *   <li>Establishes the parent-child relationship (caller component and owner page)</li>
     *   <li>Handles any instantiation errors by throwing {@link SnComponentCreationException}</li>
     * </ul>
     * </p>
     *
     * <p>
     * The component is not displayed or validated at this point; it is only instantiated and configured.
     * The actual element location and validation occur lazily when the component is first accessed.
     * </p>
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector LOGIN_BUTTON = _cssSelector.descendant(_id("login"));
     * public final SnButton loginButton = $component(LOGIN_BUTTON, SnButton.class);
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the component element
     * @param componentType the class of the component to create; must extend from {@link SnComponent}
     * @param <T> the component type
     * @return a new instance of the specified component type, configured with the selector
     * @throws SnComponentCreationException if the component cannot be instantiated
     *
     * @see #$component(SnSelector, Class, SnAbstractComponent)
     */
    protected <T extends SnComponent> T $component(SnSelector selector, Class<T> componentType) {
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
     *   <li>Handles any instantiation errors by throwing {@link SnComponentCreationException}</li>
     * </ul>
     * </p>
     *
     * <p>
     * This method is used when instantiating a component class that is an inner class. The containing object must be provided to allow proper
     * instantiation.
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the component element
     * @param componentType the class of the component to create; must extend from {@link SnComponent}
     * @param containingObject the object that contains this component as an inner class
     * @param <T> the component type
     * @return a new instance of the specified component type, configured with the selector
     * @throws SnComponentCreationException if the component cannot be instantiated
     *
     * @see #$component(SnSelector, Class)
     */
    protected <T extends SnComponent> T $component(SnSelector selector, Class<T> componentType, SnAbstractComponent containingObject) {
        try {
            T $component;

            $component = containingObject == null ? componentType.getDeclaredConstructor().newInstance() : componentType.getDeclaredConstructor(containingObject.getClass()).newInstance(containingObject);
            $component.setSelector(selector);
            if (this instanceof SnComponent $this) {
                $component.setCallerComponent(Optional.of($this));
                $component.setOwnerPage($this.ownerPage());
            } else {
                $component.setOwnerPage((SnAbstractPage) this);
            }
            return $component;
        } catch (Exception ex) {
            throw new SnComponentCreationException(ex);
        }
    }

    /**
     * Creates a collection of components of the specified type with the given selector.
     *
     * <p>
     * This method creates a {@link SnComponentCollection} that manages multiple component instances
     * matching the same selector. It:
     * <ul>
     *   <li>Creates a new collection instance</li>
     *   <li>Configures the selector and component type</li>
     * </ul>
     * </p>
     *
     * <p>
     * Components in the collection are lazily instantiated as they are accessed. The collection
     * provides convenient methods for iterating, filtering, and accessing individual components.
     * </p>
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector TABLE_ROWS = _cssSelector.descendant(_className("table-row"));
     * public final SnComponentCollection<SnTableRow> rows = $$components(TABLE_ROWS, SnTableRow.class);
     *
     * // In test code
     * for (SnTableRow row : page.rows) {
     *     System.out.println(row.text());
     * }
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the component elements
     * @param componentType the class of the components to create; must extend from {@link SnComponent}
     * @param <T> the component type
     * @return a {@link SnComponentCollection} managing components of the specified type
     *
     * @see #$$components(SnSelector, Class, SnAbstractComponent)
     * @see #$$components(SnSelector, Class, Class)
     */
    protected <T extends SnComponent> SnComponentCollection<T> $$components(SnSelector selector, Class<T> componentType) {
        SnComponentCollection<T> $$components = new SnComponentCollection<>();

        $$components.setSelector(selector);
        $$components.setComponentType(componentType);
        $$components.setOwnerPage(this);

        return $$components;
    }

    /**
     * Creates a collection of inner class components with the given selector and containing object.
     *
     * <p>
     * This method creates a {@link SnComponentCollection} for components that are inner classes
     * of a containing object. It:
     * <ul>
     *   <li>Creates a new collection instance</li>
     *   <li>Configures the selector, component type, and containing object</li>
     * </ul>
     * </p>
     *
     * <p>
     * Use this when your collection items are inner classes that require a reference to their
     * containing object for instantiation.
     * </p>
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector ITEMS = _cssSelector.className("item");
     * public final SnComponentCollection<CartItem> items = $$components(ITEMS, CartItem.class, this);
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the component elements
     * @param componentType the class of the components to create; must extend from {@link SnComponent}
     * @param containingObject the object that contains these components if they're inner classes
     * @param <T> the component type
     * @return a {@link SnComponentCollection} managing inner class components
     *
     * @see #$$components(SnSelector, Class)
     * @see #$$components(SnSelector, Class, Class)
     */
    protected <T extends SnComponent> SnComponentCollection<T> $$components(SnSelector selector, Class<T> componentType, SnAbstractComponent containingObject) {
        SnComponentCollection<T> $$components = new SnComponentCollection<>();

        $$components.setSelector(selector);
        $$components.setComponentType(componentType);
        $$components.setContainingObject(containingObject);
        $$components.setOwnerPage(this);

        return $$components;
    }

    /**
     * Creates a custom collection type for components with the given selector.
     *
     * <p>
     * This method allows you to specify a custom {@link SnComponentCollection} subclass to manage
     * components. It:
     * <ul>
     *   <li>Instantiates the custom collection class via reflection</li>
     *   <li>Configures the selector and component type</li>
     *   <li>Handles instantiation errors by throwing {@link SnComponentCreationException}</li>
     * </ul>
     * </p>
     *
     * <p>
     * Use this when you need custom collection behavior, such as filtering, searching, or
     * specialized access patterns specific to your test domain.
     * </p>
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * public class MenuItemCollection extends SnComponentCollection<MenuItem> {
     *  ...
     * }
     *
     * private static final SnCssSelector MENU_ITEMS = _cssSelector.descendant(_className("menu-item"));
     * public final MenuItemCollection menu = $$components(MENU_ITEMS, MenuItem.class, MenuItemCollection.class);
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the component elements
     * @param componentType the class of the components to create; must extend from {@link SnComponent}
     * @param componentCollectionType the custom collection class; must extend from {@link SnComponentCollection}
     * @param <T> the component type
     * @param <R> the custom collection type
     * @return a collection instance of the specified type, configured with the selector and component type
     * @throws SnComponentCreationException if the collection cannot be instantiated
     *
     * @see #$$components(SnSelector, Class)
     * @see #$$components(SnSelector, Class, SnAbstractComponent)
     */
    protected <T extends SnComponent, R extends SnComponentCollection<T>> R $$components(SnSelector selector, Class<T> componentType, Class<R> componentCollectionType) {
        try {
            R $$components;

            $$components = componentCollectionType.getDeclaredConstructor().newInstance();
            $$components.setSelector(selector);
            $$components.setComponentType(componentType);
            $$components.setOwnerPage(this);
            return $$components;
        } catch (Exception ex) {
            throw new SnComponentCreationException(ex);
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
     * </p>
     *
     * <p>
     * The try-finally pattern ensures that the WebDriver context is always restored to the
     * parent frame, even if an error occurs during frame content interaction.
     * </p>
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * private static final SnCssSelector IFRAME = _cssSelector.id("embedded-content");
     *
     * @Override
     * public final void inEmbeddedFrameContentFrame(SnFrameAction<EmbeddedFrameContent> predicate) {
     *     $frame(IFRAME, EmbeddedFrameContent.class, predicate);
     * }
     * }</pre>
     * </p>
     *
     * @param frameSelector the {@link SnSelector} for locating the frame element
     * @param frameContentType the class representing the frame content; must extend from {@link SnFrameContent}
     * @param predicate the {@link SnFrameAction} to execute within the frame context
     * @param <T> the frame content type
     * @throws SnComponentCreationException if the frame content cannot be instantiated
     * @throws SnElementNotFoundException if the frame cannot be found or displayed
     *
     * @see #$frame(SnSelector, Class, SnAbstractComponent, SnFrameAction)
     */
    protected <T extends SnFrameContent> void $frame(SnSelector frameSelector, Class<T> frameContentType, SnFrameAction<T> predicate) {
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
     * </p>
     *
     * <p>
     * The try-finally pattern ensures that the WebDriver context is always restored to the
     * parent frame, preventing orphaned frame context issues.
     * </p>
     *
     * @param frameSelector the {@link SnSelector} for locating the frame element
     * @param frameContentType the class representing the frame content; must extend from {@link SnFrameContent}
     * @param containingObject the object that contains the frame content if it's an inner class; null otherwise
     * @param predicate the {@link SnFrameAction} to execute within the frame context
     * @param <T> the frame content type
     * @throws SnComponentCreationException if the frame content cannot be instantiated
     * @throws SnElementNotFoundException if the frame cannot be found or displayed
     *
     * @see #$frame(SnSelector, Class, SnFrameAction)
     */
    protected <T extends SnFrameContent> void $frame(SnSelector frameSelector, Class<T> frameContentType, SnAbstractComponent containingObject, SnFrameAction<T> predicate) {
        final WebDriver webDriver = Selion.driver();
        final SnFrame $frame = this.$component(frameSelector, SnFrame.class);
        T $frameContent;

        try {
            if (containingObject == null) {
                $frameContent = frameContentType.getDeclaredConstructor().newInstance();
            } else {
                $frameContent = frameContentType.getDeclaredConstructor(containingObject.getClass()).newInstance(containingObject);
            }
        } catch (Exception ex) {
            throw new SnComponentCreationException(ex);
        }

        try {
            $frame.waitForDisplayed();
            webDriver.switchTo().frame($frame.scrolled());

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
     * </p>
     *
     * <p>
     * This method is useful for testing modal dialogs, alerts, and other popup components
     * that appear and disappear as part of user workflows.
     * </p>
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * private static final SnXPath SANITYTEST_DIALOG = _xpath.descendant("div", _id().is("sanitytest-dialog"));
     *
     * public void inSanityTestDialog(SnDialogAction<SnSanityTestDialog> predicate) {
     *     $dialog(SANITYTEST_DIALOG, SnSanityTestDialog.class, predicate);
     * }
     *
     * // In test code
     * page.inSanityTestDialog(dialog -> {
     *     dialog.okButton.click();
     * });
     * }</pre>
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the dialog element
     * @param componentType the class of the dialog; must extend from {@link SnDialog}
     * @param predicate the {@link SnDialogAction} to execute within the dialog context
     * @param <T> the dialog component type
     * @throws SnElementNotFoundException if the dialog cannot be found or displayed
     *
     * @see #$dialog(SnSelector, Class, SnAbstractComponent, SnDialogAction)
     */
    protected <T extends SnDialog> void $dialog(SnSelector selector, Class<T> componentType, SnDialogAction<T> predicate) {
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
     * </p>
     *
     * <p>
     * The logging and wait operations ensure that your test code doesn't proceed until the
     * dialog has fully appeared and then fully disappeared, maintaining synchronization with
     * the application's UI state.
     * </p>
     *
     * @param selector the {@link SnSelector} for locating the dialog element
     * @param componentType the class of the dialog; must extend from {@link SnDialog}
     * @param containingObject the object that contains the dialog if it's an inner class; null otherwise
     * @param predicate the {@link SnDialogAction} to execute within the dialog context
     * @param <T> the dialog component type
     * @throws SnElementNotFoundException if the dialog cannot be found or displayed
     *
     * @see #$dialog(SnSelector, Class, SnDialogAction)
     */
    protected <T extends SnDialog> void $dialog(SnSelector selector, Class<T> componentType, SnAbstractComponent containingObject, SnDialogAction<T> predicate) {
        final T $dialog = containingObject == null ? this.$component(selector, componentType) : this.$component(selector, componentType, containingObject);

        $dialog.waitForDisplayed();
        LOG.debug("Open dialog: {}", $dialog.getClass().getSimpleName());
        predicate.in($dialog);
        LOG.debug("Close dialog: {}", $dialog.getClass().getSimpleName());
        $dialog.waitForHidden();
    }
}