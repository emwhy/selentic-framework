package org.selion_framework.lib;

import org.openqa.selenium.By;
import org.selion_framework.lib.util.SnLogHandler;
import org.slf4j.Logger;

import java.util.Arrays;

/**
 * An abstract class that represents an XPath selector.
 *
 * <p>SnXPath extends {@link SnSelector} and provides XPath-specific navigation and selection
 * methods. The class builds complex XPath expressions by chaining method calls, allowing
 * intuitive construction of element selectors with support for various XPath relationships.
 * </p>
 *
 * @see SnSelector
 * @see SnXPathChild
 * @see SnXPathDescendant
 * @see SnXPathParent
 * @see SnXPathSibling
 */
public sealed abstract class SnXPath extends SnSelector permits SnXPathChild, SnXPathDescendant, SnXPathFollowing, SnXPathPage, SnXPathParent, SnXPathPreceding, SnXPathPrecedingSibling, SnXPathRaw, SnXPathSibling {
    private static final Logger LOG = SnLogHandler.logger(SnXPath.class);
    private final String tag;
    private final SnXpathPropertyType[] selectorProperties;

    /**
     * Constructs an {@code SnXPath} instance with a specified tag and optional selector properties.
     *
     * <p>This constructor is typically called by subclasses to initialize an XPath selector
     * without a prior selector node (root-level selector).
     * </p>
     *
     * @param tag the HTML tag name for this XPath node (e.g., "div", "input", "button")
     * @param selectorProperties variable number of {@link SnXpathPropertyType} objects that define
     *                           attributes and conditions for the selector (e.g., class, id, text content)
     */
    SnXPath(String tag, SnXpathPropertyType... selectorProperties) {
        super();
        this.tag = tag;
        this.selectorProperties = selectorProperties;
    }

    /**
     * Constructs an {@code SnXPath} instance with a prior selector node and specified tag and properties.
     *
     * <p>This constructor is used by subclasses to chain XPath selectors by linking to a
     * previous selector node, enabling composition of complex XPath expressions.
     * </p>
     *
     * @param priorSelectorNode the previous {@link SnXPath} node in the selector chain; allows
     *                         building composite XPath expressions
     * @param tag the HTML tag name for this XPath node (e.g., "div", "input", "button")
     * @param selectorProperties variable number of {@link SnXpathPropertyType} objects that define
     *                           attributes and conditions for the selector
     */
    SnXPath(SnXPath priorSelectorNode, String tag, SnXpathPropertyType... selectorProperties) {
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
        return (priorSelectorNode().map(Object::toString).orElse("")) + nodeText() + tag + String.join("", Arrays.stream(selectorProperties).map(p -> p.build(SnSelectorPropertyType.Types.XPath)).toList());
    }

    /**
     * Returns the hash code for this XPath selector based on its string representation.
     *
     * <p>Two {@code SnXPath} objects with identical XPath expressions will have the same hash code.
     * </p>
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
     * </p>
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
     * </p>
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
     * <p>This method constructs an {@link SnXPathDescendant} selector that represents all descendants
     * of the current element matching the specified criteria. This is equivalent to the XPath
     * "//" relationship.
     * </p>
     *
     * @param tag the HTML tag name of the descendant element
     * @param selectorProperties optional properties to filter descendant elements by attributes or content
     * @return a new {@code SnXPath} object representing the descendant selector
     * @see SnXPathDescendant
     */
    public SnXPath descendant(String tag, SnXpathPropertyType... selectorProperties) {
        return new SnXPathDescendant(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a direct child relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link SnXPathChild} selector that represents only direct children
     * of the current element matching the specified criteria. This is equivalent to the XPath "/"
     * relationship.
     * </p>
     *
     * @param tag the HTML tag name of the child element
     * @param selectorProperties optional properties to filter child elements by attributes or content
     * @return a new {@code SnXPath} object representing the child selector
     * @see SnXPathChild
     */
    public SnXPath child(String tag, SnXpathPropertyType... selectorProperties) {
        return new SnXPathChild(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a sibling relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link SnXPathSibling} selector that represents sibling elements
     * (elements sharing the same parent) matching the specified criteria.
     * </p>
     *
     * @param tag the HTML tag name of the sibling element
     * @param selectorProperties optional properties to filter sibling elements by attributes or content
     * @return a new {@code SnXPath} object representing the sibling selector
     * @see SnXPathSibling
     */
    public SnXPath sibling(String tag, SnXpathPropertyType... selectorProperties) {
        return new SnXPathSibling(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a preceding sibling relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link SnXPathPrecedingSibling} selector that represents preceding
     * sibling elements (siblings appearing earlier in the document) matching the specified criteria.
     * </p>
     *
     * @param tag the HTML tag name of the preceding sibling element
     * @param selectorProperties optional properties to filter preceding sibling elements by attributes or content
     * @return a new {@code SnXPath} object representing the preceding sibling selector
     * @see SnXPathPrecedingSibling
     */
    public SnXPath precedingSibling(String tag, SnXpathPropertyType... selectorProperties) {
        return new SnXPathPrecedingSibling(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a following relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link SnXPathFollowing} selector that represents elements
     * following the current element in document order that match the specified criteria.
     * This is equivalent to the XPath "following::" axis.
     * </p>
     *
     * @param tag the HTML tag name of the following element
     * @param selectorProperties optional properties to filter following elements by attributes or content
     * @return a new {@code SnXPath} object representing the following selector
     * @see SnXPathFollowing
     */
    public SnXPath following(String tag, SnXpathPropertyType... selectorProperties) {
        return new SnXPathFollowing(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a preceding relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link SnXPathPreceding} selector that represents elements
     * preceding the current element in document order that match the specified criteria.
     * This is equivalent to the XPath "preceding::" axis.
     * </p>
     *
     * @param tag the HTML tag name of the preceding element
     * @param selectorProperties optional properties to filter preceding elements by attributes or content
     * @return a new {@code SnXPath} object representing the preceding selector
     * @see SnXPathPreceding
     */
    public SnXPath preceding(String tag, SnXpathPropertyType... selectorProperties) {
        return new SnXPathPreceding(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code SnXPath} selector representing the parent relationship to the current element.
     *
     * <p>This method constructs an {@link SnXPathParent} selector that represents the direct parent
     * element of the current element. This is equivalent to the XPath ".." relationship.
     * </p>
     *
     * @return a new {@code SnXPath} object representing the parent selector
     * @see SnXPathParent
     */
    public SnXPath parent() {
        return new SnXPathParent(this);
    }

    /**
     * Creates a new {@code SnXPath} selector representing a page-level (root) element with the specified
     * tag and optional properties.
     *
     * <p>This method constructs an {@link SnXPathPage} selector that represents a root-level element
     * in the page DOM, starting a new selector chain not dependent on the current element.
     * This is useful for initiating independent XPath expressions.
     * </p>
     *
     * @param tag the HTML tag name of the page-level element
     * @param selectorProperties optional properties to filter page-level elements by attributes or content
     * @return a new {@code SnXPath} object representing the page-level selector
     * @see SnXPathPage
     */
    public SnXPath page(String tag, SnXpathPropertyType... selectorProperties) {
        return new SnXPathPage(tag, selectorProperties);
    }
}