package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A builder class for constructing CSS selector.
 * <p>
 * This class extends {@link ScSelectorBuilder} to provide fluent methods for creating
 * various types of CSS selectors.
 * 
 * @see ScCssSelector
 */
public final class ScComponentCssSelectorBuilder extends ScSelectorBuilder {

    /**
     * Creates a raw CSS selector from a string.
     *
     * @param selectorText The literal CSS selector string to be wrapped.
     * @return A {@link ScCssSelector} representing the raw text provided.
     */
    @Override
    public @org.jspecify.annotations.NonNull ScCssSelector raw(@NonNull String selectorText) {
        return new ScCssSelectorRaw(selectorText);
    }

    /**
     * Sets and returns a CSS selector for a descendant of this component.
     *
     * @param selectorProperties One or more properties used to define the descendant relationship.
     * @return The updated {@link ScCssSelector} instance.
     */
    public ScCssSelector descendant(@NonNull ScCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new ScCssSelectorDescendant(selectorProperties));
        return (ScCssSelector) this.selector();
    }

    /**
     * Sets and returns a CSS selector, looking at the entire web page.
     *
     * @param selectorProperties One or more properties used to define the page selector.
     * @return The updated {@link ScCssSelector} instance.
     */
    public ScCssSelector page(@NonNull ScCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new ScCssSelectorPage(selectorProperties));
        return (ScCssSelector) this.selector();
    }


    /**
     * Sets and returns a CSS selector for a descendant of this component.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties One or more properties used to define the descendant relationship.
     * @return The updated {@link ScCssSelector} instance.
     */
    public ScCssSelector descendant(@NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new ScCssSelectorDescendant(tag, selectorProperties));
        return (ScCssSelector) this.selector();
    }

    /**
     * Sets and returns a CSS selector, looking at the entire web page.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties One or more properties used to define the page selector.
     * @return The updated {@link ScCssSelector} instance.
     */
    public ScCssSelector page(@NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new ScCssSelectorPage(tag, selectorProperties));
        return (ScCssSelector) this.selector();
    }
}
