package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

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
    public @org.jspecify.annotations.NonNull ScCssSelector raw(@NonNull String selectorText) {
        return new ScCssSelectorRaw(selectorText);
    }

    /**
     * Constructs a descendant CSS selector using the specified property types.
     *
     * @param selectorProperties A variable number of {@link ScCssSelectorPropertyType}
     *                           objects to define the selector criteria.
     * @return The updated {@link ScCssSelector} instance.
     */
    public ScCssSelector descendant(@NonNull ScCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new ScCssSelectorDescendant(selectorProperties));
        return (ScCssSelector) this.selector();
    }

    /**
     * Constructs a descendant CSS selector using the specified property types.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties A variable number of {@link ScCssSelectorPropertyType}
     *                           objects to define the selector criteria.
     * @return The updated {@link ScCssSelector} instance.
     */
    public ScCssSelector descendant(@NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new ScCssSelectorDescendant(tag, selectorProperties));
        return (ScCssSelector) this.selector();
    }
}
