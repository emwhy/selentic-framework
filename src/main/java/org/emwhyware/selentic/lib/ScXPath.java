package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.Arrays;

/**
 * An abstract class that represents an XPath selector.
 *
 * <p>SnXPath extends {@link ScSelector} and provides XPath-specific navigation and selection
 * methods. The class builds complex XPath expressions by chaining method calls, allowing
 * intuitive construction of element selectors with support for various XPath relationships.
 * 
 *
 * @see ScSelector
 * @see ScXPathChild
 * @see ScXPathDescendant
 * @see ScXPathParent
 * @see ScXPathSibling
 */
public sealed abstract class ScXPath extends ScSelector permits ScXPathChild, ScXPathDescendant, ScXPathFollowing, ScXPathPage, ScXPathParent, ScXPathPreceding, ScXPathPrecedingSibling, ScXPathRaw, ScXPathSibling {
    private static final Logger LOG = ScLogHandler.logger(ScXPath.class);
    private final String tag;
    private final ScXpathPropertyType[] selectorProperties;

    /**
     * Constructs an {@code SnXPath} instance with a specified tag and optional selector properties.
     *
     * <p>This constructor is typically called by subclasses to initialize an XPath selector
     * without a prior selector node (root-level selector).
     * 
     *
     * @param tag the HTML tag name for this XPath node (e.g., "div", "input", "button")
     * @param selectorProperties variable number of {@link ScXpathPropertyType} objects that define
     *                           attributes and conditions for the selector (e.g., class, id, text content)
     */
    ScXPath(String tag, ScXpathPropertyType... selectorProperties) {
        super();
        this.tag = tag;
        this.selectorProperties = selectorProperties;
    }

    /**
     * Constructs an {@code SnXPath} instance with a prior selector node and specified tag and properties.
     *
     * <p>This constructor is used by subclasses to chain XPath selectors by linking to a
     * previous selector node, enabling composition of complex XPath expressions.
     * 
     *
     * @param priorSelectorNode the previous {@link ScXPath} node in the selector chain; allows
     *                         building composite XPath expressions
     * @param tag the HTML tag name for this XPath node (e.g., "div", "input", "button")
     * @param selectorProperties variable number of {@link ScXpathPropertyType} objects that define
     *                           attributes and conditions for the selector
     */
    ScXPath(ScXPath priorSelectorNode, String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode);
        this.tag = tag;
        this.selectorProperties = selectorProperties;
    }

    /**
     * Returns the string representation of this XPath selector.
     *
     * @return a string representation of the complete XPath expression
     */
    @Override
    public String toString() {
        return (priorSelectorNode().map(Object::toString).orElse("")) + nodeText() + tag + String.join("", Arrays.stream(selectorProperties).map(p -> p.build(ScSelectorPropertyType.Types.XPath)).toList());
    }

    /**
     * Returns the hash code for this XPath selector based on its string representation.
     *
     * <p>Two {@code SnXPath} objects with identical XPath expressions will have the same hash code.
     * 
     *
     * @return the hash code of the string representation of this XPath selector
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Builds a Selenium {@link By} locator using the XPath expression represented by this selector.
     *
     * <p>This method calls {@link #build(boolean)} with {@code false}, creating a relative XPath
     * locator without a "current node" prefix.
     * 
     *
     * @return a {@link By} object using XPath locator strategy with no prefix
     * @see #build(boolean)
     */
    @Override
    protected By build() {
        return build(false);
    }

    /**
     * Builds a Selenium {@link By} locator with an optional prefix applied to the XPath expression.
     *
     * <p>When {@code withPrefix} is {@code true}, a "." prefix is added to the XPath expression,
     * indicating a relative XPath starting from the current node. When {@code false}, the XPath
     * is relative to the document root or the context node.
     * 
     *
     * @param withPrefix {@code true} to add a "current node" prefix (".") to the XPath expression;
     *                  {@code false} for an absolute or document-relative XPath
     * @return a {@link By} object configured with the complete prefixed XPath expression
     * @see By#xpath(String)
     */
    protected By build(boolean withPrefix) {
        final String s = (withPrefix ? "." : "") + toString();

        LOG.debug("XPath: {}", s);
        return By.xpath(s);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a descendant relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathDescendant} selector that represents all descendants
     * of the current element matching the specified criteria. This is equivalent to the XPath
     * "//" relationship.
     * 
     *
     * @param tag the HTML tag name of the descendant element
     * @param selectorProperties optional properties to filter descendant elements by attributes or content
     * @return a new {@code SnXPath} object representing the descendant selector
     * @see ScXPathDescendant
     */
    public ScXPath descendant(String tag, ScXpathPropertyType... selectorProperties) {
        return new ScXPathDescendant(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a direct child relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathChild} selector that represents only direct children
     * of the current element matching the specified criteria. This is equivalent to the XPath "/"
     * relationship.
     * 
     *
     * @param tag the HTML tag name of the child element
     * @param selectorProperties optional properties to filter child elements by attributes or content
     * @return a new {@code SnXPath} object representing the child selector
     * @see ScXPathChild
     */
    public ScXPath child(String tag, ScXpathPropertyType... selectorProperties) {
        return new ScXPathChild(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a sibling relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathSibling} selector that represents sibling elements
     * (elements sharing the same parent) matching the specified criteria.
     * 
     *
     * @param tag the HTML tag name of the sibling element
     * @param selectorProperties optional properties to filter sibling elements by attributes or content
     * @return a new {@code SnXPath} object representing the sibling selector
     * @see ScXPathSibling
     */
    public ScXPath sibling(String tag, ScXpathPropertyType... selectorProperties) {
        return new ScXPathSibling(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a preceding sibling relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathPrecedingSibling} selector that represents preceding
     * sibling elements (siblings appearing earlier in the document) matching the specified criteria.
     * 
     *
     * @param tag the HTML tag name of the preceding sibling element
     * @param selectorProperties optional properties to filter preceding sibling elements by attributes or content
     * @return a new {@code SnXPath} object representing the preceding sibling selector
     * @see ScXPathPrecedingSibling
     */
    public ScXPath precedingSibling(String tag, ScXpathPropertyType... selectorProperties) {
        return new ScXPathPrecedingSibling(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a following relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathFollowing} selector that represents elements
     * following the current element in document order that match the specified criteria.
     * This is equivalent to the XPath "following::" axis.
     * 
     *
     * @param tag the HTML tag name of the following element
     * @param selectorProperties optional properties to filter following elements by attributes or content
     * @return a new {@code SnXPath} object representing the following selector
     * @see ScXPathFollowing
     */
    public ScXPath following(String tag, ScXpathPropertyType... selectorProperties) {
        return new ScXPathFollowing(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a preceding relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathPreceding} selector that represents elements
     * preceding the current element in document order that match the specified criteria.
     * This is equivalent to the XPath "preceding::" axis.
     * 
     *
     * @param tag the HTML tag name of the preceding element
     * @param selectorProperties optional properties to filter preceding elements by attributes or content
     * @return a new {@code SnXPath} object representing the preceding selector
     * @see ScXPathPreceding
     */
    public ScXPath preceding(String tag, ScXpathPropertyType... selectorProperties) {
        return new ScXPathPreceding(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code SnXPath} selector representing the parent relationship to the current element.
     *
     * <p>This method constructs an {@link ScXPathParent} selector that represents the direct parent
     * element of the current element. This is equivalent to the XPath ".." relationship.
     * 
     *
     * @return a new {@code SnXPath} object representing the parent selector
     * @see ScXPathParent
     */
    public ScXPath parent() {
        return new ScXPathParent(this);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a page-level (root) element with the specified
     * tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathPage} selector that represents a root-level element
     * in the page DOM, starting a new selector chain not dependent on the current element.
     * This is useful for initiating independent XPath expressions.
     * 
     *
     * @param tag the HTML tag name of the page-level element
     * @param selectorProperties optional properties to filter page-level elements by attributes or content
     * @return a new {@code SnXPath} object representing the page-level selector
     * @see ScXPathPage
     */
    public ScXPath page(String tag, ScXpathPropertyType... selectorProperties) {
        return new ScXPathPage(tag, selectorProperties);
    }
}