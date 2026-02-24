package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.ScComponent;
import org.emwhyware.selentic.lib.ScPage;
import org.emwhyware.selentic.lib.exception.ScSelectorException;
import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.util.Arrays;

/**
 * An abstract class that represents an XPath selector.
 *
 * <p>ScXPath extends {@link ScSelector} and provides XPath-specific navigation and selection
 * methods. The class builds complex XPath expressions by chaining method calls, allowing
 * intuitive construction of element selectors with support for various XPath relationships.
 *
 * <p>
 * This class is used in {@link ScPage} and {@link ScComponent} to define XPath. It provides consistent way
 * to build selector.
 *
 * <pre>{@code
 *
 *     private static final ScXPath XPATH_ID_TEST_TEXT = _xpath.descendant(_id().is("outer-table-1"));
 *     private static final ScXPath XPATH_TAG_TEST_TEXTS = _xpath.descendant("h2");
 *     private static final ScXPath XPATH_CSS_CLASSES_TEST_TEXTS = _xpath.descendant(_cssClasses("status", "active"));
 *     private static final ScXPath XPATH_ATTR_IS_TEST_TEXTS = _xpath.descendant(_attr("scope").is("col"));
 *     private static final ScXPath XPATH_ATTR_START_WITH_TEST_TEXTS = _xpath.descendant(_attr("scope").startsWith("co"));
 *     private static final ScXPath XPATH_ATTR_ENDS_WITH_TEST_TEXTS = _xpath.descendant(_attr("scope").endsWith("ol"));
 *     private static final ScXPath XPATH_ATTR_CONTAINS_TEST_TEXTS = _xpath.descendant(_attr("scope").contains("ol"));
 *     private static final ScXPath XPATH_ATTR_WHOLE_WORD_TEST_TEXTS = _xpath.descendant(_attr("class").wholeWord("status"));
 *     private static final ScXPath XPATH_INDEX_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-1")).child("tbody").child("tr", _indexFrom(3), _indexTo(6)).child("td", _indexOf(0));
 *
 *     private static final ScXPath XPATH_TEXT_TEST_IS_TEXTS = _xpath.descendant(_text().is("Active")).precedingSibling("td");
 *     private static final ScXPath XPATH_TEXT_TEST_STARTS_WITH_TEXTS = _xpath.descendant(_text().startsWith("Act")).precedingSibling("td");
 *     private static final ScXPath XPATH_TEXT_TEST_ENDS_WITH_TEXTS = _xpath.descendant(_text().endsWith("dby")).precedingSibling("td");
 *     private static final ScXPath XPATH_TEXT_TEST_CONTAINS_TEXTS = _xpath.descendant(_text().contains("arn")).precedingSibling("td");
 *     private static final ScXPath XPATH_TEXT_TEST_WHOLE_WORD_TEXTS = _xpath.descendant(_text().wholeWord("Delta"));
 *
 *     private static final ScXPath XPATH_SIBLING_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-1")).child("tbody").child("tr", _indexOf(5)).sibling("tr").child("td", _indexOf(0));
 *     private static final ScXPath XPATH_PRECEDING_SIBLING_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-1")).child("tbody").child("tr", _indexOf(5)).precedingSibling("tr").child("td", _indexOf(0));
 *     private static final ScXPath XPATH_FOLLOWING_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-1")).child("tbody").child("tr", _indexOf(5)).following("tr").child("td", _indexOf(0));
 *     private static final ScXPath XPATH_PRECEDING_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-2")).child("tbody").child("tr", _indexOf(0)).preceding("tr").child("td", _indexOf(0));
 *     private static final ScXPath XPATH_DESCENDANT_TEST_TEXTS = _xpath.descendant("table", _id().is("outer-table-2")).descendant("td");
 *     private static final ScXPath XPATH_CHILD_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-2")).child("tbody").child("tr").child("td");
 *     private static final ScXPath XPATH_NOT_TEST_TEXTS = _xpath.descendant("body").child(_not(_id().isPresent()));
 *     private static final ScXPath XPATH_RAW_TEST_TEXTS = _xpath.raw("//body/h2");
 *
 * }</pre>
 *
 * @see ScCssSelector
 * @see ScPage
 * @see ScComponent
 * @see ScXPathChild
 * @see ScXPathDescendant
 * @see ScXPathParent
 * @see ScXPathSibling
 */
public sealed abstract class ScXPath extends ScSelector permits ScXPathChild, ScXPathDescendant, ScXPathFollowing, ScXPathPage, ScXPathParent, ScXPathPreceding, ScXPathPrecedingSibling, ScXPathRaw, ScXPathSibling, ScXPathLimitedBy {
    private static final Logger LOG = ScLogHandler.logger(ScXPath.class);
    private final String tag;
    private final ScXpathPropertyType[] selectorProperties;

    /**
     * Constructs an {@code ScXPath} instance with a specified tag and optional selector properties.
     *
     * <p>This constructor is typically called by subclasses to initialize an XPath selector
     * without a prior selector node (root-level selector).
     * 
     *
     * @param tag the HTML tag name for this XPath node (e.g., "div", "input", "button")
     * @param selectorProperties variable number of {@link ScXpathPropertyType} objects that define
     *                           attributes and conditions for the selector (e.g., class, id, text content)
     */
    ScXPath(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        super();
        this.tag = tag.trim();
        this.selectorProperties = selectorProperties;
    }

    /**
     * Constructs an {@code ScXPath} instance with a prior selector node and specified tag and properties.
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
    ScXPath(@NonNull ScXPath priorSelectorNode, @NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode);
        this.tag = tag.trim();
        this.selectorProperties = selectorProperties;
    }

    /**
     * Constructs an {@code ScXPath} instance with a specified tag and optional selector properties.
     *
     * <p>This constructor is typically called by subclasses to initialize an XPath selector
     * without a prior selector node (root-level selector).
     *
     *
     * @param selectorProperties variable number of {@link ScXpathPropertyType} objects that define
     *                           attributes and conditions for the selector (e.g., class, id, text content)
     */
    ScXPath(@NonNull ScXpathPropertyType... selectorProperties) {
        super();
        this.tag = "*";
        this.selectorProperties = selectorProperties;
    }

    /**
     * Constructs an {@code ScXPath} instance with a prior selector node and specified tag and properties.
     *
     * <p>This constructor is used by subclasses to chain XPath selectors by linking to a
     * previous selector node, enabling composition of complex XPath expressions.
     *
     *
     * @param priorSelectorNode the previous {@link ScXPath} node in the selector chain; allows
     *                         building composite XPath expressions
     * @param selectorProperties variable number of {@link ScXpathPropertyType} objects that define
     *                           attributes and conditions for the selector
     */
    ScXPath(@NonNull ScXPath priorSelectorNode, @NonNull ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode);
        this.tag = "*";
        this.selectorProperties = selectorProperties;
    }

    /**
     * Validate the selector input.
     */
    private static void validateSelector(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        if (tag.isEmpty()) {
            throw new ScSelectorException("Tag text should not be empty string.");
        } else if (tag.equals("*") && selectorProperties.length == 0) {
            throw new ScSelectorException("No properties were provided. This would have matched with every tag on the page.");
        } else if (tag.contains(" ")) {
            throw new ScSelectorException("Tag text should not contain space characters.");
        }
    }

    /**
     * Returns the string representation of this XPath selector.
     *
     * @return a string representation of the complete XPath expression
     */
    @Override
    public String toString() {
        if (!isCached()) {
            // A parent node contains no properties. Skipping validation.
            if (!(this instanceof ScXPathParent) && !(this instanceof ScXPathLimitedBy)) {
                validateSelector(this.tag, this.selectorProperties);
            }
            setCache((priorSelectorNode().map(Object::toString).orElse("")) + nodeText() + tag + String.join("", Arrays.stream(selectorProperties).map(p -> p.build(ScSelectorPropertyType.Types.XPath)).toList()));
        }
        return super.toString();
    }

    /**
     * Returns the hash code for this XPath selector based on its string representation.
     *
     * <p>Two {@code ScXPath} objects with identical XPath expressions will have the same hash code.
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

        LOG.debug("XPath: \"{}\"", s);
        return By.xpath(s);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a descendant relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathDescendant} selector that represents all descendants
     * of the current element matching the specified criteria. This is equivalent to the XPath
     * "//" relationship.
     * 
     *
     * @param tag the HTML tag name of the descendant element
     * @param selectorProperties optional properties to filter descendant elements by attributes or content
     * @return a new {@code ScXPath} object representing the descendant selector
     * @see ScXPathDescendant
     */
    public ScXPathDescendant descendant(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathDescendant(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a direct child relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathChild} selector that represents only direct children
     * of the current element matching the specified criteria. This is equivalent to the XPath "/"
     * relationship.
     * 
     *
     * @param tag the HTML tag name of the child element
     * @param selectorProperties optional properties to filter child elements by attributes or content
     * @return a new {@code ScXPath} object representing the child selector
     * @see ScXPathChild
     */
    public ScXPathChild child(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathChild(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a sibling relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathSibling} selector that represents sibling elements
     * (elements sharing the same parent) matching the specified criteria.
     * 
     *
     * @param tag the HTML tag name of the sibling element
     * @param selectorProperties optional properties to filter sibling elements by attributes or content
     * @return a new {@code ScXPath} object representing the sibling selector
     * @see ScXPathSibling
     */
    public ScXPathSibling sibling(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathSibling(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a preceding sibling relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathPrecedingSibling} selector that represents preceding
     * sibling elements (siblings appearing earlier in the document) matching the specified criteria.
     * 
     *
     * @param tag the HTML tag name of the preceding sibling element
     * @param selectorProperties optional properties to filter preceding sibling elements by attributes or content
     * @return a new {@code ScXPath} object representing the preceding sibling selector
     * @see ScXPathPrecedingSibling
     */
    public ScXPathPrecedingSibling precedingSibling(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathPrecedingSibling(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a following relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathFollowing} selector that represents elements
     * following the current element in document order that match the specified criteria.
     * This is equivalent to the XPath "following::" axis.
     * 
     *
     * @param tag the HTML tag name of the following element
     * @param selectorProperties optional properties to filter following elements by attributes or content
     * @return a new {@code ScXPath} object representing the following selector
     * @see ScXPathFollowing
     */
    public ScXPathFollowing following(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathFollowing(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a preceding relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathPreceding} selector that represents elements
     * preceding the current element in document order that match the specified criteria.
     * This is equivalent to the XPath "preceding::" axis.
     * 
     *
     * @param tag the HTML tag name of the preceding element
     * @param selectorProperties optional properties to filter preceding elements by attributes or content
     * @return a new {@code ScXPath} object representing the preceding selector
     * @see ScXPathPreceding
     */
    public ScXPathPreceding preceding(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathPreceding(this, tag, selectorProperties);
    }


    /**
     * Creates a new {@code ScXPath} selector representing a descendant relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathDescendant} selector that represents all descendants
     * of the current element matching the specified criteria. This is equivalent to the XPath
     * "//" relationship.
     *
     *
     * @param selectorProperties optional properties to filter descendant elements by attributes or content
     * @return a new {@code ScXPath} object representing the descendant selector
     * @see ScXPathDescendant
     */
    public ScXPathDescendant descendant(@NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathDescendant(this, selectorProperties);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a direct child relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathChild} selector that represents only direct children
     * of the current element matching the specified criteria. This is equivalent to the XPath "/"
     * relationship.
     *
     *
     * @param selectorProperties optional properties to filter child elements by attributes or content
     * @return a new {@code ScXPath} object representing the child selector
     * @see ScXPathChild
     */
    public ScXPathChild child(@NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathChild(this, selectorProperties);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a sibling relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathSibling} selector that represents sibling elements
     * (elements sharing the same parent) matching the specified criteria.
     *
     *
     * @param selectorProperties optional properties to filter sibling elements by attributes or content
     * @return a new {@code ScXPath} object representing the sibling selector
     * @see ScXPathSibling
     */
    public ScXPathSibling sibling(@NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathSibling(this, selectorProperties);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a preceding sibling relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathPrecedingSibling} selector that represents preceding
     * sibling elements (siblings appearing earlier in the document) matching the specified criteria.
     *
     *
     * @param selectorProperties optional properties to filter preceding sibling elements by attributes or content
     * @return a new {@code ScXPath} object representing the preceding sibling selector
     * @see ScXPathPrecedingSibling
     */
    public ScXPathPrecedingSibling precedingSibling(@NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathPrecedingSibling(this, selectorProperties);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a following relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathFollowing} selector that represents elements
     * following the current element in document order that match the specified criteria.
     * This is equivalent to the XPath "following::" axis.
     *
     *
     * @param selectorProperties optional properties to filter following elements by attributes or content
     * @return a new {@code ScXPath} object representing the following selector
     * @see ScXPathFollowing
     */
    public ScXPathFollowing following(@NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathFollowing(this, selectorProperties);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a preceding relationship to an element
     * with the specified tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathPreceding} selector that represents elements
     * preceding the current element in document order that match the specified criteria.
     * This is equivalent to the XPath "preceding::" axis.
     *
     *
     * @param selectorProperties optional properties to filter preceding elements by attributes or content
     * @return a new {@code ScXPath} object representing the preceding selector
     * @see ScXPathPreceding
     */
    public ScXPathPreceding preceding(@NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathPreceding(this, selectorProperties);
    }

    /**
     * Creates a new {@code ScXPath} selector representing the parent relationship to the current element.
     *
     * <p>This method constructs an {@link ScXPathParent} selector that represents the direct parent
     * element of the current element. This is equivalent to the XPath ".." relationship.
     * 
     *
     * @return a new {@code ScXPath} object representing the parent selector
     * @see ScXPathParent
     */
    public ScXPathParent parent() {
        return new ScXPathParent(this);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a page-level (root) element with the specified
     * tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathPage} selector that represents a root-level element
     * in the page DOM, starting a new selector chain not dependent on the current element.
     * This is useful for initiating independent XPath expressions.
     * 
     *
     * @param tag the HTML tag name of the page-level element
     * @param selectorProperties optional properties to filter page-level elements by attributes or content
     * @return a new {@code ScXPath} object representing the page-level selector
     * @see ScXPathPage
     */
    public ScXPathPage page(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathPage(tag, selectorProperties);
    }

    /**
     * Creates a new {@code ScXPath} selector representing a page-level (root) element with the specified
     * tag and optional properties.
     *
     * <p>This method constructs an {@link ScXPathPage} selector that represents a root-level element
     * in the page DOM, starting a new selector chain not dependent on the current element.
     * This is useful for initiating independent XPath expressions.
     *
     *
     * @param selectorProperties optional properties to filter page-level elements by attributes or content
     * @return a new {@code ScXPath} object representing the page-level selector
     * @see ScXPathPage
     */
    public ScXPathPage page(@NonNull ScXpathPropertyType... selectorProperties) {
        return new ScXPathPage(selectorProperties);
    }
}