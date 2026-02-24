package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

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
    public @org.jspecify.annotations.NonNull ScXPath raw(@NonNull String selectorText) {
        return new ScXPathRaw(selectorText);
    }

    /**
     * Creates a descendant XPath selector for the specified tag and properties.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathDescendant descendant(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathDescendant(tag, selectorProperties));
        return (ScXPathDescendant) this.selector();
    }

    /**
     * Creates a direct child XPath selector for the specified tag and properties.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathChild child(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathChild(tag, selectorProperties));
        return (ScXPathChild) this.selector();
    }

    /**
     * Creates a sibling XPath selector for the specified tag and properties.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathSibling sibling(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathSibling(tag, selectorProperties));
        return (ScXPathSibling) this.selector();
    }

    /**
     * Creates a preceding sibling XPath selector.
     * <i>Note: Implementation uses {@link ScXPathFollowing} internally.</i>
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathPrecedingSibling precedingSibling(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathPrecedingSibling(tag, selectorProperties));
        return (ScXPathPrecedingSibling) this.selector();
    }

    /**
     * Creates a following XPath selector.
     * <i>Note: Implementation uses {@link ScXPathSibling} internally.</i>
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathFollowing following(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathFollowing(tag, selectorProperties));
        return (ScXPathFollowing) this.selector();
    }

    /**
     * Creates a preceding XPath selector for the specified tag and properties.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathPreceding preceding(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathPreceding(tag, selectorProperties));
        return (ScXPathPreceding) this.selector();
    }

    /**
     * Creates a page-level XPath selector starting from the root.
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathPage page(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathPage(tag, selectorProperties));
        return (ScXPathPage) this.selector();
    }


    /**
     * Creates a descendant XPath selector for the specified tag and properties.
     *
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathDescendant descendant(@NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathDescendant(selectorProperties));
        return (ScXPathDescendant) this.selector();
    }

    /**
     * Creates a direct child XPath selector for the specified tag and properties.
     *
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathChild child(@NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathChild(selectorProperties));
        return (ScXPathChild) this.selector();
    }

    /**
     * Creates a sibling XPath selector for the specified tag and properties.
     *
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathSibling sibling(@NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathSibling(selectorProperties));
        return (ScXPathSibling) this.selector();
    }

    /**
     * Creates a preceding sibling XPath selector.
     * <i>Note: Implementation uses {@link ScXPathFollowing} internally.</i>
     *
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathFollowing precedingSibling(@NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathPrecedingSibling(selectorProperties));
        return (ScXPathFollowing) this.selector();
    }

    /**
     * Creates a following XPath selector.
     * <i>Note: Implementation uses {@link ScXPathSibling} internally.</i>
     *
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPath following(@NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathFollowing(selectorProperties));
        return (ScXPath) this.selector();
    }

    /**
     * Creates a preceding XPath selector for the specified tag and properties.
     *
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathPreceding preceding(@NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathPreceding(selectorProperties));
        return (ScXPathPreceding) this.selector();
    }

    /**
     * Creates a page-level XPath selector starting from the root.
     *
     * @param selectorProperties Optional properties/attributes to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathPage page(@NonNull ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathPage(selectorProperties));
        return (ScXPathPage) this.selector();
    }

    /**
     * Creates an XPath selector targeting the immediate parent of the current node.
     *
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPathParent parent() {
        this.setSelector(new ScXPathParent());
        return (ScXPathParent) this.selector();
    }
}
