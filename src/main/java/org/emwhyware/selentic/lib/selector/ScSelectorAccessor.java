package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.ScSelectorPackageAccessor;
import org.openqa.selenium.By;

/**
 * Gives access to some selector related classes in this package to outside this package. This helps to organize the selector
 * related classes better by allowing them to be neatly in their own package.
 * <p>
 * It only exposes necessary methods, allowing to keep some methods and constructors hidden to help keeping the integrity
 * of the code that utilizes this framework. By extending this class, classes from another packages can gain access to
 * included methods and ability to instantiate protected classes.
 *
 * @see ScSelectorPackageAccessor
 */
public class ScSelectorAccessor {

    /**
     * Protected constructor.
     */
    protected ScSelectorAccessor() {}

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
    protected ScSelectorNotProperty _not(@NonNull ScSelectorProperty selectorProperty) {
        return new ScSelectorNotProperty(selectorProperty);
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
    protected ScSelectorAttributeCondition _attr(@NonNull String attribute) {
        return new ScSelectorAttributeCondition("@", attribute);
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
    protected ScSelectorCssClassesProperty _cssClasses(@NonNull String... cssClasses) {
        return new ScSelectorCssClassesProperty(cssClasses);
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
    protected ScSelectorTagProperty _tag(@NonNull String tag) {
        return new ScSelectorTagProperty(tag);
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
    protected ScSelectorAttributeCondition _id() {
        return _attr("id");
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
    protected ScSelectorIdProperty _id(@NonNull String id) {
        return new ScSelectorIdProperty(id);
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
    protected ScSelectorAttributeCondition _name() {
        return _attr("name");
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
    protected ScSelectorAttributeCondition _type() {
        return _attr("type");
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
    protected ScSelectorTextCondition _text() {
        return new ScSelectorTextCondition();
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
    protected ScSelectorIndexProperty _indexFrom(int startIndex) {
        return new ScSelectorIndexProperty(ScSelectorIndexProperty.Conditions.From, startIndex);
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
    protected ScSelectorIndexProperty _indexTo(int endIndex) {
        return new ScSelectorIndexProperty(ScSelectorIndexProperty.Conditions.To, endIndex);
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
     * _indexOf(0)     // Matches the first element
     * _indexOf(5)     // Matches the sixth element
     * _indexOf(10)    // Matches the eleventh element
     * }</pre>
     *
     *
     * @param index the index of the element to match (0-based)
     * @return a {@link ScSelectorIndexProperty} for building exact index-based selectors
     */
    protected ScSelectorIndexProperty _indexOf(int index) {
        return new ScSelectorIndexProperty(ScSelectorIndexProperty.Conditions.At, index);
    }

    /**
     * Creates a selector property for matching the first element. This method is only valid for XPath.
     *
     * <p>
     * This method is a convenience method equivalent to {@code _indexOf(0)}.
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
    protected ScSelectorIndexProperty _first() {
        return _indexOf(0);
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
    protected ScSelectorIndexProperty _last() {
        return new ScSelectorIndexProperty(ScSelectorIndexProperty.Conditions.Last);
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
    protected ScSelectorNthOfTypeProperty _nthOfType(int index) {
        return new ScSelectorNthOfTypeProperty(index);
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
    protected ScSelectorNthLastOfTypeProperty _nthLastOfType(int index) {
        return new ScSelectorNthLastOfTypeProperty(index);
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
    protected ScSelectorFirstOfTypeProperty _firstOfType() {
        return new ScSelectorFirstOfTypeProperty();
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
    protected ScSelectorLastOfTypeProperty _lastOfType() {
        return new ScSelectorLastOfTypeProperty();
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
    protected ScSelectorNthChildProperty _nthChild(int index) {
        return new ScSelectorNthChildProperty(index);
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
    protected ScSelectorNthLastChildProperty _nthLastChild(int index) {
        return new ScSelectorNthLastChildProperty(index);
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
    protected ScSelectorFirstChildProperty _firstChild() {
        return new ScSelectorFirstChildProperty();
    }

    /**
     * This method defines a boundary component to a list of components.
     *
     * <p>The method generates XPath, <b>[following:: ...]</b>. The XPath naming is not very clear what it does,
     * so the method implementation makes it more legible and clear.
     * <p>
     * The parameter {@link ScSelector} must be of the page level. It would fail if a relative path is passed.
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * _xpath.descendant("div", _boundary(_xpath.descendant("div", _id().is("lower-boundary"))); // List of components, up to the given component.
     * }</pre>
     * @param xpath {@link ScXPath} class that contains XPath to the lower boundary of the list.
     * @return a new {@code ScSelectorBoundaryProperty} object
     * @see ScXPath
     */
    protected ScSelectorBoundaryProperty _boundary(ScXPath xpath) {
        return new ScSelectorBoundaryProperty(xpath);
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
    protected ScSelectorLastChildProperty _lastChild() {
        return new ScSelectorLastChildProperty();
    }

    /**
     * Check if a selector is absolute.
     * @param selector selector
     * @return true if the selector is absolute
     */
    protected boolean isSelectorAbsolute(@NonNull ScSelector selector) {
        return selector.isAbsolute();
    }

    /**
     * builds selector {@link By} object.
     * @param selector selector
     * @return {@link By} object
     */
    protected By buildSelector(@NonNull ScSelector selector) {
        return selector.build();
    }

    /**
     * builds selector {@link By} object.
     * @param selector selector
     * @param withPrefix Boolean value to describe if it should have prefix
     * @return {@link By} object
     */
    protected By buildSelector(@NonNull ScSelector selector, boolean withPrefix) {
        return ((ScXPath) selector).build(withPrefix);
    }
}
