package org.selion_framework.lib;

/**
 * {@code SnCssSelectorPage} represents a root-level CSS selector that starts a new selector chain.
 *
 * <p>
 * This class creates page-level (document root) selectors that are not dependent on any prior element.
 * It is used to initiate independent CSS selector expressions from the beginning of the DOM.
 * 
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * // Select a button directly on the page
 * SnCssSelector button = _cssSelector.page(_id("submit"));
 *
 * // Select all paragraphs with a specific class
 * SnCssSelector paragraphs = _cssSelector.page(_tag("p"), _cssClasses("intro"));
 *
 * // Chain with descendant or child selectors
 * SnCssSelector input = _cssSelector.page(_id("login"))
 *                                   .child(_tag("input"), _type().is("text"));
 * }</pre>
 *
 * @see SnCssSelector
 */
public final class SnCssSelectorPage extends SnCssSelector {

    /**
     * Constructs an {@code SnCssSelectorPage} with optional properties.
     *
     * <p>
     * This constructor is package-private and called internally by the framework. Use the fluent API
     * method {@link SnCssSelector#page(SnCssSelectorPropertyType...)} instead.
     * 
     *
     * @param selectorProperties optional properties to filter page-level elements
     */
    SnCssSelectorPage(SnCssSelectorPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    /**
     * Returns an empty string as there is no combinator for root-level selectors.
     *
     * <p>
     * Root selectors start from the document without a preceding element.
     * Called internally during selector string construction.
     * 
     *
     * @return an empty string (no combinator)
     */
    @Override
    protected String nodeText() {
        return "";
    }
}