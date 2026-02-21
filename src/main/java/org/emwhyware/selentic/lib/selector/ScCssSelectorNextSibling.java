package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code ScCssSelectorNextSibling} represents a CSS selector using the adjacent sibling combinator ({@code +}).
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
 * ScCssSelector nextP = _cssSelector.descendant(_tag("h1"))
 *                                   .nextSibling(_tag("p"));
 *
 * // Select the button immediately after an input
 * ScCssSelector nextBtn = _cssSelector.descendant(_tag("input"), _type().is("text"))
 *                                     .nextSibling(_tag("button"));
 * }</pre>
 *
 * @see ScCssSelector
 * @see ScCssSelectorSibling
 */
public final class ScCssSelectorNextSibling extends ScCssSelector {

    /**
     * Constructs an {@code ScCssSelectorNextSibling} with a prior selector node and optional properties.
     *
     * <p>
     * This constructor is package-private and called internally by the framework. Use the fluent API
     * method {@link ScCssSelector#nextSibling(ScCssSelectorPropertyType...)} instead.
     * 
     *
     * @param priorSelectorNode the preceding element selector in the chain
     * @param selectorProperties optional properties to filter the next sibling element
     */
    ScCssSelectorNextSibling(@NonNull ScCssSelector priorSelectorNode, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    /**
     * Constructs an {@code ScCssSelectorNextSibling} with a prior selector node and optional properties.
     *
     * <p>
     * This constructor is package-private and called internally by the framework. Use the fluent API
     * method {@link ScCssSelector#nextSibling(ScCssSelectorPropertyType...)} instead.
     *
     *
     * @param tag The HTML/XML tag to target.
     * @param priorSelectorNode the preceding element selector in the chain
     * @param selectorProperties optional properties to filter the next sibling element
     */
    ScCssSelectorNextSibling(@NonNull ScCssSelector priorSelectorNode, @NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
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