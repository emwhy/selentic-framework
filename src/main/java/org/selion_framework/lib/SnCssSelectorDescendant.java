package org.selion_framework.lib;

/**
 * {@code SnCssSelectorDescendant} represents a CSS selector using the descendant combinator (space).
 *
 * <p>
 * This class selects all descendant elements at any depth, as opposed to the child combinator
 * ({@code >}) which matches only direct children.
 * </p>
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
 * SnCssSelector spans = _cssSelector.descendant(_id().is("container"))
 *                                   .descendant(_tag("span"));
 *
 * // Select all links anywhere inside navigation
 * SnCssSelector links = _cssSelector.page(_tag("nav"))
 *                                   .descendant(_attr("href").isNotEmpty());
 * }</pre>
 *
 * @see SnCssSelector
 * @see SnCssSelectorChild
 */
public final class SnCssSelectorDescendant extends SnCssSelector {

    /**
     * Constructs an {@code SnCssSelectorDescendant} without a prior selector (root-level selector).
     *
     * <p>
     * Used internally by the framework for creating root descendant selectors.
     * </p>
     *
     * @param selectorProperties optional properties to filter descendant elements
     */
    SnCssSelectorDescendant(SnCssSelectorPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    /**
     * Constructs an {@code SnCssSelectorDescendant} with a prior selector node and optional properties.
     *
     * <p>
     * This constructor is package-private and called internally by the framework. Use the fluent API
     * method {@link SnCssSelector#descendant(SnCssSelectorPropertyType...)} instead.
     * </p>
     *
     * @param priorSelectorNode the parent element selector in the chain
     * @param selectorProperties optional properties to filter descendant elements
     */
    SnCssSelectorDescendant(SnCssSelector priorSelectorNode, SnCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    /**
     * Returns the CSS descendant combinator operator: a space ({@code " "}).
     *
     * <p>
     * This symbol is placed between parent and descendant selectors in the CSS selector string.
     * Called internally during selector string construction.
     * </p>
     *
     * @return the CSS descendant combinator: a single space
     */
    @Override
    protected String nodeText() {
        return " ";
    }
}