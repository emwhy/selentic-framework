package org.selion_framework.lib;

/**
 * A builder class for constructing XPath selectors.
 * <p>
 * This class provides a fluent API to build complex XPath expressions by defining
 * relationships.
 * </p>
 * @see SnXPath
 */
public final class SnComponentXPathBuilder extends SnSelectorBuilder {

    /**
     * Creates a XPath selector from a raw XPath text.
     *
     * @param selectorText The literal XPath string.
     * @return A {@link SnXPath} representing the raw text provided.
     */
    @Override
    public SnXPath raw(String selectorText) {
        return new SnXPathRaw(selectorText);
    }

    /**
     * Creates a descendant XPath selector for the specified tag and properties.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link SnXPath} instance.
     */
    public SnXPath descendant(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathDescendant(tag, selectorProperties));
        return (SnXPath) this.selector();
    }

    /**
     * Creates a direct child XPath selector for the specified tag and properties.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link SnXPath} instance.
     */
    public SnXPath child(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathChild(tag, selectorProperties));
        return (SnXPath) this.selector();
    }

    /**
     * Creates a sibling XPath selector for the specified tag and properties.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link SnXPath} instance.
     */
    public SnXPath sibling(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathSibling(tag, selectorProperties));
        return (SnXPath) this.selector();
    }

    /**
     * Creates a preceding sibling XPath selector.
     * <i>Note: Implementation uses {@link SnXPathFollowing} internally.</i>
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link SnXPath} instance.
     */
    public SnXPath precedingSibling(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathFollowing(tag, selectorProperties));
        return (SnXPath) this.selector();
    }

    /**
     * Creates a following XPath selector.
     * <i>Note: Implementation uses {@link SnXPathSibling} internally.</i>
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link SnXPath} instance.
     */
    public SnXPath following(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathSibling(tag, selectorProperties));
        return (SnXPath) this.selector();
    }

    /**
     * Creates a preceding XPath selector for the specified tag and properties.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link SnXPath} instance.
     */
    public SnXPath preceding(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathPreceding(tag, selectorProperties));
        return (SnXPath) this.selector();
    }

    /**
     * Creates a page-level XPath selector starting from the root.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link SnXPath} instance.
     */
    public SnXPath page(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathPage(tag, selectorProperties));
        return (SnXPath) this.selector();
    }

    /**
     * Creates an XPath selector targeting the immediate parent of the current node.
     *
     * @return The updated {@link SnXPath} instance.
     */
    public SnXPath parent() {
        this.setSelector(new SnXPathParent());
        return (SnXPath) this.selector();
    }
}
