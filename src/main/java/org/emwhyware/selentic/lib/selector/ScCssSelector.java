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
 * An abstract class that represents a CSS selector.
 * <p>
 * This class is used in {@link ScPage} and {@link ScComponent} to define CSS selectors. It provides consistent way
 * to build selector.
 *
 * <pre>{@code
 *
 *     private static final ScCssSelector CSS_SELECTOR_ID_TEST_TEXT = _cssSelector.descendant(_id("outer-table-1"));
 *     private static final ScCssSelector CSS_SELECTOR_TAG_TEST_TEXTS = _cssSelector.descendant("h2");
 *     private static final ScCssSelector CSS_SELECTOR_CSS_CLASSES_TEST_TEXTS = _cssSelector.descendant(_cssClasses("status", "active"));
 *     private static final ScCssSelector CSS_SELECTOR_ATTR_START_WITH_TEST_TEXTS = _cssSelector.descendant(_attr("scope").startsWith("co"));
 *     private static final ScCssSelector CSS_SELECTOR_ATTR_ENDS_WITH_TEST_TEXTS = _cssSelector.descendant(_attr("scope").endsWith("ol"));
 *     private static final ScCssSelector CSS_SELECTOR_ATTR_CONTAINS_TEST_TEXTS = _cssSelector.descendant(_attr("scope").contains("ol"));
 *     private static final ScCssSelector CSS_SELECTOR_ATTR_WHOLE_WORD_TEST_TEXTS = _cssSelector.descendant(_attr("class").wholeWord("status"));
 *     private static final ScCssSelector CSS_SELECTOR_NTH_OF_TYPE_TEST_TEXT = _cssSelector.descendant("body").child("h2", _nthOfType(1));
 *     private static final ScCssSelector CSS_SELECTOR_NTH_LAST_OF_TYPE_TEST_TEXT = _cssSelector.descendant("body").child("h2", _nthLastOfType(1));
 *     private static final ScCssSelector CSS_SELECTOR_LAST_OF_TYPE_TEST_TEXT = _cssSelector.descendant("body").child(_attr("id").isPresent(), _lastOfType());
 *     private static final ScCssSelector CSS_SELECTOR_NTH_CHILD_TEST_TEXT = _cssSelector.descendant("body").child(_nthChild(0));
 *     private static final ScCssSelector CSS_SELECTOR_NTH_LAST_CHILD_TEST_TEXT = _cssSelector.descendant("body").child(_nthLastChild(1));
 *     private static final ScCssSelector CSS_SELECTOR_LAST_CHILD_TEST_TEXT = _cssSelector.descendant("body").child(_lastChild());
 *     private static final ScCssSelector CSS_SELECTOR_LAST_OF_TYPE_NESTED_TEST_TEXT = CSS_SELECTOR_LAST_OF_TYPE_TEST_TEXT.child("tbody").descendant(_nthChild(1));
 *     private static final ScCssSelector CSS_SELECTOR_SIBLING_TEST_TEXTS = _cssSelector.descendant(_id("main-r2-c1")).sibling("td", _not(_attr("class").isPresent()));
 *     private static final ScCssSelector CSS_SELECTOR_NEXT_SIBLING_TEST_TEXT = _cssSelector.descendant(_id("main-r2-c1")).nextSibling("td");
 *     private static final ScCssSelector CSS_SELECTOR_DESCENDANT_TEST_TEXTS = _cssSelector.descendant("th");
 *     private static final ScCssSelector CSS_SELECTOR_CHILD_TEST_TEXTS = _cssSelector.descendant(_id("outer-table-2")).child("tbody").child("tr").child("td");
 *     private static final ScCssSelector CSS_SELECTOR_NOT_TEST_TEXTS = _cssSelector.descendant("body").child(_not(_tag("h2")));
 *     private static final ScCssSelector CSS_SELECTOR_RAW_TEST_TEXTS = _cssSelector.raw("body > :not(table)");
 *
 * }</pre>
 *
 * <p>
 * ScCssSelector extends {@link ScSelector} and provides CSS selector-specific navigation and
 * selection methods. The class builds complex CSS selector expressions by chaining method calls,
 * allowing intuitive construction of element selectors with support for various CSS combinators
 * (descendant, child, sibling, and next sibling relationships).
 *
 *
 * @see ScXPath
 * @see ScPage
 * @see ScComponent
 * @see ScCssSelectorChild
 * @see ScCssSelectorDescendant
 * @see ScCssSelectorSibling
 * @see ScCssSelectorNextSibling
 * @see ScCssSelectorPage
 */
public sealed abstract class ScCssSelector extends ScSelector permits ScCssSelectorChild, ScCssSelectorDescendant, ScCssSelectorSibling, ScCssSelectorNextSibling, ScCssSelectorPage, ScCssSelectorRaw {
    private static final Logger LOG = ScLogHandler.logger(ScCssSelector.class);
    private final String tag;
    private final ScCssSelectorPropertyType[] selectorProperties;

    /**
     * Constructs an {@code ScCssSelector} instance with optional selector properties.
     *
     * <p>This constructor is typically called by subclasses to initialize a CSS selector
     * without a prior selector node (root-level selector).
     * 
     *
     * @param selectorProperties variable number of {@link ScCssSelectorPropertyType} objects that define
     *                           attributes and conditions for the selector (e.g., class, id, attribute values)
     */
    ScCssSelector(@NonNull ScCssSelectorPropertyType... selectorProperties) {
        super();
        this.tag = "";
        this.selectorProperties = selectorProperties;
    }

    /**
     * Constructs an {@code ScCssSelector} instance with a prior selector node and optional selector properties.
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
    ScCssSelector(@NonNull ScCssSelector priorSelectorNode, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode);
        this.tag = "";
        this.selectorProperties = selectorProperties;
    }


    /**
     * Constructs an {@code ScCssSelector} instance with optional selector properties.
     *
     * <p>This constructor is typically called by subclasses to initialize a CSS selector
     * without a prior selector node (root-level selector).
     *
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties variable number of {@link ScCssSelectorPropertyType} objects that define
     *                           attributes and conditions for the selector (e.g., class, id, attribute values)
     */
    ScCssSelector(@NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        super();
        this.tag = tag.trim();
        this.selectorProperties = selectorProperties;
    }

    /**
     * Constructs an {@code ScCssSelector} instance with a prior selector node and optional selector properties.
     *
     * <p>This constructor is used by subclasses to chain CSS selectors by linking to a
     * previous selector node, enabling composition of complex CSS selector expressions.
     *
     *
     * @param priorSelectorNode the previous {@link ScCssSelector} node in the selector chain; allows
     *                         building composite CSS selector expressions
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties variable number of {@link ScCssSelectorPropertyType} objects that define
     *                           attributes and conditions for the selector
     */
    ScCssSelector(@NonNull ScCssSelector priorSelectorNode, @NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode);
        this.tag = tag.trim();
        this.selectorProperties = selectorProperties;
    }

    /**
     * Validate the selector input.
     */
    private static void validateSelector(@NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        if ((tag.equals("*") || tag.isEmpty()) && selectorProperties.length == 0) {
            throw new ScSelectorException("No properties were provided. This would have matched with every tag on the page.");
        } else if (tag.contains(" ")) {
            throw new ScSelectorException("Tag text should not contain space characters.");
        }
    }

    /**
     * Returns the string representation of this CSS selector.
     *
     * @return a string representation of the complete CSS selector expression
     */
    @Override
    public String toString() {
        if (!isCached()) {
            validateSelector(this.tag, this.selectorProperties);
            setCache((priorSelectorNode().map(Object::toString).orElse("")) + nodeText() + tag + String.join("", Arrays.stream(selectorProperties).map(p -> p.build(ScSelectorPropertyType.Types.CssSelector)).toList()));
        }
        return super.toString();
    }

    /**
     * Returns the hash code for this CSS selector based on its string representation.
     *
     * <p>Two {@code ScCssSelector} objects with identical CSS selector expressions will have the same hash code.
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
    public final By build() {
        final String s = toString();

        LOG.debug("CSS Selector: \"{}\"", s);
        return By.cssSelector(s);
    }

    /**
     * Creates a new {@code ScCssSelector} representing a descendant relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorDescendant} selector that represents all descendants
     * of the current element matching the specified criteria. This is equivalent to the CSS
     * descendant combinator (space character) relationship.
     * 
     *
     * @param selectorProperties optional properties to filter descendant elements by attributes or other selectors
     * @return a new {@code ScCssSelector} object representing the descendant selector
     * @see ScCssSelectorDescendant
     */
    public ScCssSelectorDescendant descendant(@NonNull ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorDescendant(this, selectorProperties);
    }

    /**
     * Creates a new {@code ScCssSelector} representing a direct child relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorChild} selector that represents only direct children
     * of the current element matching the specified criteria. This is equivalent to the CSS
     * child combinator (&#62;) relationship.
     * 
     *
     * @param selectorProperties optional properties to filter child elements by attributes or other selectors
     * @return a new {@code ScCssSelector} object representing the child selector
     * @see ScCssSelectorChild
     */
    public ScCssSelectorChild child(@NonNull ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorChild(this, selectorProperties);
    }

    /**
     * Creates a new {@code ScCssSelector} representing a sibling relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorSibling} selector that represents general sibling elements
     * (any sibling element in the DOM regardless of position) matching the specified criteria.
     * This is equivalent to the CSS general sibling combinator (~) relationship.
     * 
     *
     * @param selectorProperties optional properties to filter sibling elements by attributes or other selectors
     * @return a new {@code ScCssSelector} object representing the sibling selector
     * @see ScCssSelectorSibling
     */
    public ScCssSelectorSibling sibling(@NonNull ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorSibling(this, selectorProperties);
    }

    /**
     * Creates a new {@code ScCssSelector} representing a next sibling relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorNextSibling} selector that represents the immediately
     * following sibling element matching the specified criteria. This is equivalent to the CSS
     * adjacent sibling combinator (+) relationship.
     * 
     *
     * @param selectorProperties optional properties to filter the next sibling element by attributes or other selectors
     * @return a new {@code ScCssSelector} object representing the next sibling selector
     * @see ScCssSelectorNextSibling
     */
    public ScCssSelectorNextSibling nextSibling(@NonNull ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorNextSibling(this, selectorProperties);
    }

    /**
     * Creates a new {@code ScCssSelector} representing a page-level (root) element with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorPage} selector that represents a root-level element
     * in the page DOM, starting a new selector chain not dependent on the current element.
     * This is useful for initiating independent CSS selector expressions.
     * 
     *
     * @param selectorProperties optional properties to filter page-level elements by attributes or other selectors
     * @return a new {@code ScCssSelector} object representing the page-level selector
     * @see ScCssSelectorPage
     */
    public ScCssSelectorPage page(@NonNull ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorPage(selectorProperties);
    }


    /**
     * Creates a new {@code ScCssSelector} representing a descendant relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorDescendant} selector that represents all descendants
     * of the current element matching the specified criteria. This is equivalent to the CSS
     * descendant combinator (space character) relationship.
     *
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties optional properties to filter descendant elements by attributes or other selectors
     * @return a new {@code ScCssSelector} object representing the descendant selector
     * @see ScCssSelectorDescendant
     */
    public ScCssSelectorDescendant descendant(@NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorDescendant(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code ScCssSelector} representing a direct child relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorChild} selector that represents only direct children
     * of the current element matching the specified criteria. This is equivalent to the CSS
     * child combinator (&#62;) relationship.
     *
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties optional properties to filter child elements by attributes or other selectors
     * @return a new {@code ScCssSelector} object representing the child selector
     * @see ScCssSelectorChild
     */
    public ScCssSelectorChild child(@NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorChild(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code ScCssSelector} representing a sibling relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorSibling} selector that represents general sibling elements
     * (any sibling element in the DOM regardless of position) matching the specified criteria.
     * This is equivalent to the CSS general sibling combinator (~) relationship.
     *
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties optional properties to filter sibling elements by attributes or other selectors
     * @return a new {@code ScCssSelector} object representing the sibling selector
     * @see ScCssSelectorSibling
     */
    public ScCssSelectorSibling sibling(@NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorSibling(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code ScCssSelector} representing a next sibling relationship to an element
     * with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorNextSibling} selector that represents the immediately
     * following sibling element matching the specified criteria. This is equivalent to the CSS
     * adjacent sibling combinator (+) relationship.
     *
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties optional properties to filter the next sibling element by attributes or other selectors
     * @return a new {@code ScCssSelector} object representing the next sibling selector
     * @see ScCssSelectorNextSibling
     */
    public ScCssSelectorNextSibling nextSibling(@NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorNextSibling(this, tag, selectorProperties);
    }

    /**
     * Creates a new {@code ScCssSelector} representing a page-level (root) element with the specified properties.
     *
     * <p>This method constructs an {@link ScCssSelectorPage} selector that represents a root-level element
     * in the page DOM, starting a new selector chain not dependent on the current element.
     * This is useful for initiating independent CSS selector expressions.
     *
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties optional properties to filter page-level elements by attributes or other selectors
     * @return a new {@code ScCssSelector} object representing the page-level selector
     * @see ScCssSelectorPage
     */
    public ScCssSelectorPage page(@NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        return new ScCssSelectorPage(tag, selectorProperties);
    }
}
