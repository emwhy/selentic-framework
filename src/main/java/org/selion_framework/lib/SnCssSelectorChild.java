package org.selion_framework.lib;

/**
 * {@code SnCssSelectorChild} represents a CSS selector using the child combinator ({@code >}).
 *
 * <p>
 * This class selects elements that are direct children of a parent element, as opposed to
 * the descendant combinator (space) which matches any descendant regardless of depth.
 * </p>
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
 * SnCssSelector button = _cssSelector.descendant(_id("container"))
 *                                    .child(_cssClasses("submit"));
 *
 * // Select list items directly inside unordered list
 * SnCssSelector items = _cssSelector.descendant(_tag("ul"), _id("menu"))
 *                                   .child("li");
 * }</pre>
 *
 * @see SnCssSelector
 * @see SnCssSelectorDescendant
 */
public final class SnCssSelectorChild extends SnCssSelector {

    /**
     * Constructs an {@code SnCssSelectorChild} with a prior selector node and optional properties.
     *
     * <p>
     * This constructor is package-private and called internally by the framework. Use the fluent API
     * method {@link SnCssSelector#child(SnCssSelectorPropertyType...)} instead.
     * </p>
     *
     * @param priorSelectorNode the parent element selector in the chain
     * @param selectorProperties optional properties to filter child elements
     */
    SnCssSelectorChild(SnCssSelector priorSelectorNode, SnCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    /**
     * Returns the CSS child combinator operator: {@code " > "}.
     *
     * <p>
     * This symbol is placed between parent and child selectors in the CSS selector string.
     * Called internally during selector string construction.
     * </p>
     *
     * @return the CSS child combinator: {@code " > "}
     */
    @Override
    protected String nodeText() {
        return " > ";
    }
}