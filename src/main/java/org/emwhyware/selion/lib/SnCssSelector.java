package org.emwhyware.selion.lib;

import org.emwhyware.selion.lib.util.SnLogHandler;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.Arrays;

/**
 * An abstract class that represents a CSS selector.
 *
 * <p>SnCssSelector extends {@link SnSelector} and provides CSS selector-specific navigation and
 * selection methods. The class builds complex CSS selector expressions by chaining method calls,
 * allowing intuitive construction of element selectors with support for various CSS combinators
 * (descendant, child, sibling, and next sibling relationships).
 * 
 *
 * @see SnSelector
 * @see SnCssSelectorChild
 * @see SnCssSelectorDescendant
 * @see SnCssSelectorSibling
 * @see SnCssSelectorNextSibling
 * @see SnCssSelectorPage
 */
public sealed abstract class SnCssSelector extends SnSelector permits SnCssSelectorChild, SnCssSelectorDescendant, SnCssSelectorSibling, SnCssSelectorNextSibling, SnCssSelectorPage, SnCssSelectorRaw {
    private static final Logger LOG = SnLogHandler.logger(SnCssSelector.class);
    private final SnCssSelectorPropertyType[] selectorProperties;

    /**
     * Constructs an {@code SnCssSelector} instance with optional selector properties.
     *
     * <p>This constructor is typically called by subclasses to initialize a CSS selector
     * without a prior selector node (root-level selector).
     * 
     *
     * @param selectorProperties variable number of {@link SnCssSelectorPropertyType} objects that define
     *                           attributes and conditions for the selector (e.g., class, id, attribute values)
     */
    SnCssSelector(SnCssSelectorPropertyType... selectorProperties) {
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
     * @param priorSelectorNode the previous {@link SnCssSelector} node in the selector chain; allows
     *                         building composite CSS selector expressions
     * @param selectorProperties variable number of {@link SnCssSelectorPropertyType} objects that define
     *                           attributes and conditions for the selector
     */
    SnCssSelector(SnCssSelector priorSelectorNode, SnCssSelectorPropertyType... selectorProperties) {
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
        return (priorSelectorNode().map(Object::toString).orElse("")) + nodeText() + String.join("", Arrays.stream(selectorProperties).map(p -> p.build(SnSelectorPropertyType.Types.CssSelector)).toList());
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
     * <p>This method constructs an {@link SnCssSelectorDescendant} selector that represents all descendants
     * of the current element matching the specified criteria. This is equivalent to the CSS
     * descendant combinator (space character) relationship.
     * 
     *
     * @param selectorProperties optional properties to filter descendant elements by attributes or other selectors
     * @return a new {@code SnCssSelector} object representing the descendant selector
     * @see SnCssSelectorDescendant
     */
    public SnCssSelector descendant(SnCssSelectorPropertyType... selectorProperties) {
        return new SnCssSelectorDescendant(this, selectorProperties);
    }

    /**
     * Creates a new {@code SnCssSelector} representing a direct child relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link SnCssSelectorChild} selector that represents only direct children
     * of the current element matching the specified criteria. This is equivalent to the CSS
     * child combinator (&#62;) relationship.
     * 
     *
     * @param selectorProperties optional properties to filter child elements by attributes or other selectors
     * @return a new {@code SnCssSelector} object representing the child selector
     * @see SnCssSelectorChild
     */
    public SnCssSelector child(SnCssSelectorPropertyType... selectorProperties) {
        return new SnCssSelectorChild(this, selectorProperties);
    }

    /**
     * Creates a new {@code SnCssSelector} representing a sibling relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link SnCssSelectorSibling} selector that represents general sibling elements
     * (any sibling element in the DOM regardless of position) matching the specified criteria.
     * This is equivalent to the CSS general sibling combinator (~) relationship.
     * 
     *
     * @param selectorProperties optional properties to filter sibling elements by attributes or other selectors
     * @return a new {@code SnCssSelector} object representing the sibling selector
     * @see SnCssSelectorSibling
     */
    public SnCssSelector sibling(SnCssSelectorPropertyType... selectorProperties) {
        return new SnCssSelectorSibling(this, selectorProperties);
    }

    /**
     * Creates a new {@code SnCssSelector} representing a next sibling relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link SnCssSelectorNextSibling} selector that represents the immediately
     * following sibling element matching the specified criteria. This is equivalent to the CSS
     * adjacent sibling combinator (+) relationship.
     * 
     *
     * @param selectorProperties optional properties to filter the next sibling element by attributes or other selectors
     * @return a new {@code SnCssSelector} object representing the next sibling selector
     * @see SnCssSelectorNextSibling
     */
    public SnCssSelector nextSibling(SnCssSelectorPropertyType... selectorProperties) {
        return new SnCssSelectorNextSibling(this, selectorProperties);
    }

    /**
     * Creates a new {@code SnCssSelector} representing a page-level (root) element with the specified properties.
     *
     * <p>This method constructs an {@link SnCssSelectorPage} selector that represents a root-level element
     * in the page DOM, starting a new selector chain not dependent on the current element.
     * This is useful for initiating independent CSS selector expressions.
     * 
     *
     * @param selectorProperties optional properties to filter page-level elements by attributes or other selectors
     * @return a new {@code SnCssSelector} object representing the page-level selector
     * @see SnCssSelectorPage
     */
    public SnCssSelector page(SnCssSelectorPropertyType... selectorProperties) {
        return new SnCssSelectorPage(selectorProperties);
    }
}