package org.selion_framework.lib;

/**
 * A builder class for constructing XPath selectors from a page.
 * <p>
 * This class provides methods to create XPath selectors starting from the root or
 * using literal strings, ensuring specific page-level targeting.
 * </p>
 * @see SnXPath
 */
public final class SnPageXPathBuilder extends SnSelectorBuilder {

    /**
     * Creates a XPath selector from a raw XPath text.
     *
     * @param selectorText The literal XPath string to be used.
     * @return A {@link SnXPath} representing the raw text provided.
     */
    @Override
    public SnXPath raw(String selectorText) {
        return new SnXPathRaw(selectorText);
    }

    /**
     * Creates a page-level descendant XPath selector for the specified tag and properties.
     * <p>
     * Note: This method internally instantiates {@link SnXPathPage} to ensure the
     * selector is rooted correctly for page-level scope.
     * </p>
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties or attributes used to filter the tag.
     * @return The updated {@link SnXPath} instance.
     */
    public SnXPath descendant(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathPage(tag, selectorProperties));
        return (SnXPath) this.selector();
    }
}
