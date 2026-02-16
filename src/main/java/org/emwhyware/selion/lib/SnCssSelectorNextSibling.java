package org.emwhyware.selion.lib;

/**
 * {@code SnCssSelectorNextSibling} represents a CSS selector using the adjacent sibling combinator ({@code +}).
 *
 * <p>
 * This class selects the immediately following sibling element that matches the selector.
 * It differs from the general sibling combinator ({@code ~}) which matches any following sibling.
 * 
 *
 * <h2>Sibling Combinators</h2>
 * <ul>
 *   <li>{@code h1 + p} - Selects only the first {@code <p>} immediately after {@code <h1>}</li>
 *   <li>{@code h1 ~ p} - Selects all {@code <p>} siblings following {@code <h1>}</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * // Select the paragraph immediately after a heading
 * SnCssSelector nextP = _cssSelector.descendant(_tag("h1"))
 *                                   .nextSibling(_tag("p"));
 *
 * // Select the button immediately after an input
 * SnCssSelector nextBtn = _cssSelector.descendant(_tag("input"), _type().is("text"))
 *                                     .nextSibling(_tag("button"));
 * }</pre>
 *
 * @see SnCssSelector
 * @see SnCssSelectorSibling
 */
public final class SnCssSelectorNextSibling extends SnCssSelector {

    /**
     * Constructs an {@code SnCssSelectorNextSibling} with a prior selector node and optional properties.
     *
     * <p>
     * This constructor is package-private and called internally by the framework. Use the fluent API
     * method {@link SnCssSelector#nextSibling(SnCssSelectorPropertyType...)} instead.
     * 
     *
     * @param priorSelectorNode the preceding element selector in the chain
     * @param selectorProperties optional properties to filter the next sibling element
     */
    SnCssSelectorNextSibling(SnCssSelector priorSelectorNode, SnCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    /**
     * Returns the CSS adjacent sibling combinator operator: {@code " + "}.
     *
     * <p>
     * This symbol is placed between the element and its immediately following sibling.
     * Called internally during selector string construction.
     * 
     *
     * @return the CSS adjacent sibling combinator: {@code " + "}
     */
    @Override
    protected String nodeText() {
        return " + ";
    }
}