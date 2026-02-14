package org.selion_framework.lib;

/**
 * A builder class  for constructing CSS selectors specifically from a page.
 * <p>
 * This class provides methods to initialize CSS selectors either from raw strings or
 * by defining hierarchical descendant relationships.
 * </p>
 * @see SnCssSelector
 */
public final class SnPageCssSelectorBuilder extends SnSelectorBuilder {

    /**
     * Creates a CSS selector from the provided raw CSS selector text.
     *
     * @param selectorText The literal CSS selector text.
     * @return A {@link SnCssSelector} instance representing the raw selector.
     */
    @Override
    public SnCssSelector raw(String selectorText) {
        return new SnCssSelectorRaw(selectorText);
    }

    /**
     * Constructs a descendant CSS selector using the specified property types.
     *
     * @param selectorProperties A variable number of {@link SnCssSelectorPropertyType}
     *                           objects to define the selector criteria.
     * @return The updated {@link SnCssSelector} instance.
     */
    public SnCssSelector descendant(SnCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new SnCssSelectorDescendant(selectorProperties));
        return (SnCssSelector) this.selector();
    }
}
