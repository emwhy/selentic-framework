package org.emwhyware.selentic.lib;

/**
 * A builder class  for constructing CSS selectors specifically from a page.
 * <p>
 * This class provides methods to initialize CSS selectors either from raw strings or
 * by defining hierarchical descendant relationships.
 * 
 * @see ScCssSelector
 */
public final class ScPageCssSelectorBuilder extends ScSelectorBuilder {

    /**
     * Creates a CSS selector from the provided raw CSS selector text.
     *
     * @param selectorText The literal CSS selector text.
     * @return A {@link ScCssSelector} instance representing the raw selector.
     */
    @Override
    public ScCssSelector raw(String selectorText) {
        return new ScCssSelectorRaw(selectorText);
    }

    /**
     * Constructs a descendant CSS selector using the specified property types.
     *
     * @param selectorProperties A variable number of {@link ScCssSelectorPropertyType}
     *                           objects to define the selector criteria.
     * @return The updated {@link ScCssSelector} instance.
     */
    public ScCssSelector descendant(ScCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new ScCssSelectorDescendant(selectorProperties));
        return (ScCssSelector) this.selector();
    }
}
