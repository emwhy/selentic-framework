package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code ScCssSelectorChild} represents a CSS selector using the child combinator ({@code >}).
 *
 * <p>
 * This class selects elements that are direct children of a parent element, as opposed to
 * the descendant combinator (space) which matches any descendant regardless of depth.
 * 
 *
 * <h2>Child vs. Descendant</h2>
 * <ul>
 *   <li>{@code div > span} - Selects only direct {@code <span>} children of {@code <div>}</li>
 *   <li>{@code div span} - Selects all {@code <span>} descendants of {@code <div>}</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * // Select button directly inside form
 * ScCssSelector button = _cssSelector.descendant(_id("container"))
 *                                    .child(_cssClasses("submit"));
 *
 * // Select list items directly inside unordered list
 * ScCssSelector items = _cssSelector.descendant(_tag("ul"), _id("menu"))
 *                                   .child("li");
 * }</pre>
 *
 * @see ScCssSelector
 * @see ScCssSelectorDescendant
 */
public final class ScCssSelectorChild extends ScCssSelector {

    /**
     * Constructs an {@code ScCssSelectorChild} with a prior selector node and optional properties.
     *
     * <p>
     * This constructor is package-private and called internally by the framework. Use the fluent API
     * method {@link ScCssSelector#child(ScCssSelectorPropertyType...)} instead.
     * 
     *
     * @param priorSelectorNode the parent element selector in the chain
     * @param selectorProperties optional properties to filter child elements
     */
    ScCssSelectorChild(@NonNull ScCssSelector priorSelectorNode, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }


    /**
     * Constructs an {@code ScCssSelectorChild} with a prior selector node and optional properties.
     *
     * <p>
     * This constructor is package-private and called internally by the framework. Use the fluent API
     * method {@link ScCssSelector#child(ScCssSelectorPropertyType...)} instead.
     *
     *
     * @param priorSelectorNode the parent element selector in the chain
     * @param selectorProperties optional properties to filter child elements
     */
    ScCssSelectorChild(@NonNull ScCssSelector priorSelectorNode, @NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    /**
     * Returns the CSS child combinator operator: {@code " > "}.
     *
     * <p>
     * This symbol is placed between parent and child selectors in the CSS selector string.
     * Called internally during selector string construction.
     * 
     *
     * @return the CSS child combinator: {@code " > "}
     */
    @Override
    protected String nodeText() {
        return " > ";
    }
}