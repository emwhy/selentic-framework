package org.emwhyware.selentic.lib;

/**
 * {@code ScCssSelectorDescendant} represents a CSS selector using the descendant combinator (space).
 *
 * <p>
 * This class selects all descendant elements at any depth, as opposed to the child combinator
 * ({@code >}) which matches only direct children.
 * 
 *
 * <h2>Descendant vs. Child</h2>
 * <ul>
 *   <li>{@code div span} - Selects all {@code <span>} descendants of {@code <div>}</li>
 *   <li>{@code div > span} - Selects only direct {@code <span>} children of {@code <div>}</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * // Select all spans anywhere inside a container
 * ScCssSelector spans = _cssSelector.descendant(_id().is("container"))
 *                                   .descendant(_tag("span"));
 *
 * // Select all links anywhere inside navigation
 * ScCssSelector links = _cssSelector.page(_tag("nav"))
 *                                   .descendant(_attr("href").isNotEmpty());
 * }</pre>
 *
 * @see ScCssSelector
 * @see ScCssSelectorChild
 */
public final class ScCssSelectorDescendant extends ScCssSelector {

    /**
     * Constructs an {@code ScCssSelectorDescendant} without a prior selector (root-level selector).
     *
     * <p>
     * Used internally by the framework for creating root descendant selectors.
     * 
     *
     * @param selectorProperties optional properties to filter descendant elements
     */
    ScCssSelectorDescendant(ScCssSelectorPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    /**
     * Constructs an {@code ScCssSelectorDescendant} with a prior selector node and optional properties.
     *
     * <p>
     * This constructor is package-private and called internally by the framework. Use the fluent API
     * method {@link ScCssSelector#descendant(ScCssSelectorPropertyType...)} instead.
     * 
     *
     * @param priorSelectorNode the parent element selector in the chain
     * @param selectorProperties optional properties to filter descendant elements
     */
    ScCssSelectorDescendant(ScCssSelector priorSelectorNode, ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }


    /**
     * Constructs an {@code ScCssSelectorDescendant} without a prior selector (root-level selector).
     *
     * <p>
     * Used internally by the framework for creating root descendant selectors.
     *
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties optional properties to filter descendant elements
     */
    ScCssSelectorDescendant(String tag, ScCssSelectorPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    /**
     * Constructs an {@code ScCssSelectorDescendant} with a prior selector node and optional properties.
     *
     * <p>
     * This constructor is package-private and called internally by the framework. Use the fluent API
     * method {@link ScCssSelector#descendant(ScCssSelectorPropertyType...)} instead.
     *
     *
     * @param priorSelectorNode the parent element selector in the chain
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties optional properties to filter descendant elements
     */
    ScCssSelectorDescendant(ScCssSelector priorSelectorNode, String tag, ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    /**
     * Returns the CSS descendant combinator operator: a space ({@code " "}).
     *
     * <p>
     * This symbol is placed between parent and descendant selectors in the CSS selector string.
     * Called internally during selector string construction.
     * 
     *
     * @return the CSS descendant combinator: a single space
     */
    @Override
    protected String nodeText() {
        return " ";
    }
}