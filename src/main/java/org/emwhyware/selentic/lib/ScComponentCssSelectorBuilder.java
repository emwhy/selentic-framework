package org.emwhyware.selentic.lib;

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
    public ScCssSelector raw(String selectorText) {
        return new ScCssSelectorRaw(selectorText);
    }

    /**
     * Sets and returns a CSS selector for a descendant of this component.
     *
     * @param selectorProperties One or more properties used to define the descendant relationship.
     * @return The updated {@link ScCssSelector} instance.
     */
    public ScCssSelector descendant(ScCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new ScCssSelectorDescendant(selectorProperties));
        return (ScCssSelector) this.selector();
    }

    /**
     * Sets and returns a CSS selector, looking at the entire web page.
     *
     * @param selectorProperties One or more properties used to define the page selector.
     * @return The updated {@link ScCssSelector} instance.
     */
    public ScCssSelector page(ScCssSelectorPropertyType... selectorProperties) {
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
    public ScCssSelector descendant(String tag, ScCssSelectorPropertyType... selectorProperties) {
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
    public ScCssSelector page(String tag, ScCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new ScCssSelectorPage(tag, selectorProperties));
        return (ScCssSelector) this.selector();
    }
}
