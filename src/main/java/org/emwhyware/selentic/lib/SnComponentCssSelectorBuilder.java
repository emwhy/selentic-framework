package org.emwhyware.selentic.lib;

/**
 * A builder class for constructing CSS selector.
 * <p>
 * This class extends {@link SnSelectorBuilder} to provide fluent methods for creating
 * various types of CSS selectors.
 * 
 * @see SnCssSelector
 */
public final class SnComponentCssSelectorBuilder extends SnSelectorBuilder {

    /**
     * Creates a raw CSS selector from a string.
     *
     * @param selectorText The literal CSS selector string to be wrapped.
     * @return A {@link SnCssSelector} representing the raw text provided.
     */
    @Override
    public SnCssSelector raw(String selectorText) {
        return new SnCssSelectorRaw(selectorText);
    }

    /**
     * Sets and returns a CSS selector for a descendant of this component.
     *
     * @param selectorProperties One or more properties used to define the descendant relationship.
     * @return The updated {@link SnCssSelector} instance.
     */
    public SnCssSelector descendant(SnCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new SnCssSelectorDescendant(selectorProperties));
        return (SnCssSelector) this.selector();
    }

    /**
     * Sets and returns a CSS selector, looking at the entire web page.
     *
     * @param selectorProperties One or more properties used to define the page selector.
     * @return The updated {@link SnCssSelector} instance.
     */
    public SnCssSelector page(SnCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new SnCssSelectorPage(selectorProperties));
        return (SnCssSelector) this.selector();
    }
}
