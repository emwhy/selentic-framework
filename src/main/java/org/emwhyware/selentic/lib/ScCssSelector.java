package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.Arrays;

/**
 * An abstract class that represents a CSS selector.
 *
 * <p>SnCssSelector extends {@link ScSelector} and provides CSS selector-specific navigation and
 * selection methods. The class builds complex CSS selector expressions by chaining method calls,
 * allowing intuitive construction of element selectors with support for various CSS combinators
 * (descendant, child, sibling, and next sibling relationships).
 * 
 *
 * @see ScSelector
 * @see ScCssSelectorChild
 * @see ScCssSelectorDescendant
 * @see ScCssSelectorSibling
 * @see ScCssSelectorNextSibling
 * @see ScCssSelectorPage
 */
public sealed abstract class ScCssSelector extends ScSelector permits ScCssSelectorChild, ScCssSelectorDescendant, ScCssSelectorSibling, ScCssSelectorNextSibling, ScCssSelectorPage, ScCssSelectorRaw {
    private static final Logger LOG = ScLogHandler.logger(ScCssSelector.class);
    private final ScCssSelectorPropertyType[] selectorProperties;

    /**
     * Constructs an {@code SnCssSelector} instance with optional selector properties.
     *
     * <p>This constructor is typically called by subclasses to initialize a CSS selector
     * without a prior selector node (root-level selector).
     * 
     *
     * @param selectorProperties variable number of {@link ScCssSelectorPropertyType} objects that define
     *                           attributes and conditions for the selector (e.g., class, id, attribute values)
     */
    ScCssSelector(ScCssSelectorPropertyType... selectorProperties) {
        super();
        this.selectorProperties = selectorProperties;
    }

    /**
     * Constructs an {@code SnCssSelector} instance with a prior selector node and optional selector properties.
     *
     * <p>This constructor is used by subclasses to chain CSS selectors by linking to a
     * previous selector node, enabling composition of complex CSS selector expressions.
     * 
     *
     * @param priorSelectorNode the previous {@link ScCssSelector} node in the selector chain; allows
     *                         building composite CSS selector expressions
     * @param selectorProperties variable number of {@link ScCssSelectorPropertyType} objects that define
     *                           attributes and conditions for the selector
     */
    ScCssSelector(ScCssSelector priorSelectorNode, ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode);
        this.selectorProperties = selectorProperties;
    }

    /**
     * Returns the string representation of this CSS selector.
     *
     * @return a string representation of the complete CSS selector expression
     */
    @Override
    public String toString() {
        return (priorSelectorNode().map(Object::toString).orElse("")) + nodeText() + String.join("", Arrays.stream(selectorProperties).map(p -> p.build(ScSelectorPropertyType.Types.CssSelector)).toList());
    }

    /**
     * Returns the hash code for this CSS selector based on its string representation.
     *
     * <p>Two {@code SnCssSelector} objects with identical CSS selector expressions will have the same hash code.
     *
     * @return the hash code of the string representation of this CSS selector
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Builds a Selenium {@link By} locator using the CSS selector expression represented by this selector.
     *
     * <p>This method converts the CSS selector to a Selenium-compatible locator for element identification.
     * The resulting CSS selector expression is logged at DEBUG level for troubleshooting purposes.
     * 
     *
     * @return a {@link By} object using CSS selector locator strategy
     * @see By#cssSelector(String)
     */
    @Override
    protected By build() {
        final String s = toString();

        LOG.debug("CSS Selector: {}", s);
        return By.cssSelector(s);
    }

    /**
     * Creates a new {@code SnCssSelector} representing a descendant relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorDescendant} selector that represents all descendants
     * of the current element matching the specified criteria. This is equivalent to the CSS
     * descendant combinator (space character) relationship.
     * 
     *
     * @param selectorProperties optional properties to filter descendant elements by attributes or other selectors
     * @return a new {@code SnCssSelector} object representing the descendant selector
     * @see ScCssSelectorDescendant
     */
    public ScCssSelector descendant(ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorDescendant(this, selectorProperties);
    }

    /**
     * Creates a new {@code SnCssSelector} representing a direct child relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorChild} selector that represents only direct children
     * of the current element matching the specified criteria. This is equivalent to the CSS
     * child combinator (&#62;) relationship.
     * 
     *
     * @param selectorProperties optional properties to filter child elements by attributes or other selectors
     * @return a new {@code SnCssSelector} object representing the child selector
     * @see ScCssSelectorChild
     */
    public ScCssSelector child(ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorChild(this, selectorProperties);
    }

    /**
     * Creates a new {@code SnCssSelector} representing a sibling relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorSibling} selector that represents general sibling elements
     * (any sibling element in the DOM regardless of position) matching the specified criteria.
     * This is equivalent to the CSS general sibling combinator (~) relationship.
     * 
     *
     * @param selectorProperties optional properties to filter sibling elements by attributes or other selectors
     * @return a new {@code SnCssSelector} object representing the sibling selector
     * @see ScCssSelectorSibling
     */
    public ScCssSelector sibling(ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorSibling(this, selectorProperties);
    }

    /**
     * Creates a new {@code SnCssSelector} representing a next sibling relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorNextSibling} selector that represents the immediately
     * following sibling element matching the specified criteria. This is equivalent to the CSS
     * adjacent sibling combinator (+) relationship.
     * 
     *
     * @param selectorProperties optional properties to filter the next sibling element by attributes or other selectors
     * @return a new {@code SnCssSelector} object representing the next sibling selector
     * @see ScCssSelectorNextSibling
     */
    public ScCssSelector nextSibling(ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorNextSibling(this, selectorProperties);
    }

    /**
     * Creates a new {@code SnCssSelector} representing a page-level (root) element with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorPage} selector that represents a root-level element
     * in the page DOM, starting a new selector chain not dependent on the current element.
     * This is useful for initiating independent CSS selector expressions.
     * 
     *
     * @param selectorProperties optional properties to filter page-level elements by attributes or other selectors
     * @return a new {@code SnCssSelector} object representing the page-level selector
     * @see ScCssSelectorPage
     */
    public ScCssSelector page(ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorPage(selectorProperties);
    }
}