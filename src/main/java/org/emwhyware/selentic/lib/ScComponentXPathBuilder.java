package org.emwhyware.selentic.lib;

/**
 * A builder class for constructing XPath selectors.
 * <p>
 * This class provides a fluent API to build complex XPath expressions by defining
 * relationships.
 * 
 * @see ScXPath
 */
public final class ScComponentXPathBuilder extends ScSelectorBuilder {

    /**
     * Creates a XPath selector from a raw XPath text.
     *
     * @param selectorText The literal XPath string.
     * @return A {@link ScXPath} representing the raw text provided.
     */
    @Override
    public ScXPath raw(String selectorText) {
        return new ScXPathRaw(selectorText);
    }

    /**
     * Creates a descendant XPath selector for the specified tag and properties.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPath descendant(String tag, ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathDescendant(tag, selectorProperties));
        return (ScXPath) this.selector();
    }

    /**
     * Creates a direct child XPath selector for the specified tag and properties.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPath child(String tag, ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathChild(tag, selectorProperties));
        return (ScXPath) this.selector();
    }

    /**
     * Creates a sibling XPath selector for the specified tag and properties.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPath sibling(String tag, ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathSibling(tag, selectorProperties));
        return (ScXPath) this.selector();
    }

    /**
     * Creates a preceding sibling XPath selector.
     * <i>Note: Implementation uses {@link ScXPathFollowing} internally.</i>
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPath precedingSibling(String tag, ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathFollowing(tag, selectorProperties));
        return (ScXPath) this.selector();
    }

    /**
     * Creates a following XPath selector.
     * <i>Note: Implementation uses {@link ScXPathSibling} internally.</i>
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPath following(String tag, ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathSibling(tag, selectorProperties));
        return (ScXPath) this.selector();
    }

    /**
     * Creates a preceding XPath selector for the specified tag and properties.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPath preceding(String tag, ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathPreceding(tag, selectorProperties));
        return (ScXPath) this.selector();
    }

    /**
     * Creates a page-level XPath selector starting from the root.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPath page(String tag, ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathPage(tag, selectorProperties));
        return (ScXPath) this.selector();
    }

    /**
     * Creates an XPath selector targeting the immediate parent of the current node.
     *
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPath parent() {
        this.setSelector(new ScXPathParent());
        return (ScXPath) this.selector();
    }
}
