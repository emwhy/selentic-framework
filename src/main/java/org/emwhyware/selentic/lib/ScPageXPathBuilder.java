package org.emwhyware.selentic.lib;

/**
 * A builder class for constructing XPath selectors from a page.
 * <p>
 * This class provides methods to create XPath selectors starting from the root or
 * using literal strings, ensuring specific page-level targeting.
 * 
 * @see ScXPath
 */
public final class ScPageXPathBuilder extends ScSelectorBuilder {

    /**
     * Creates a XPath selector from a raw XPath text.
     *
     * @param selectorText The literal XPath string to be used.
     * @return A {@link ScXPath} representing the raw text provided.
     */
    @Override
    public ScXPath raw(String selectorText) {
        return new ScXPathRaw(selectorText);
    }

    /**
     * Creates a page-level descendant XPath selector for the specified tag and properties.
     * <p>
     * Note: This method internally instantiates {@link ScXPathPage} to ensure the
     * selector is rooted correctly for page-level scope.
     * 
     *
     * @param tag The HTML/XML tag to target.
     * @param selectorProperties Optional properties or attributes used to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPath descendant(String tag, ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathPage(tag, selectorProperties));
        return (ScXPath) this.selector();
    }


    /**
     * Creates a page-level descendant XPath selector for the specified tag and properties.
     * <p>
     * Note: This method internally instantiates {@link ScXPathPage} to ensure the
     * selector is rooted correctly for page-level scope.
     *
     *
     * @param selectorProperties Optional properties or attributes used to filter the tag.
     * @return The updated {@link ScXPath} instance.
     */
    public ScXPath descendant(ScXpathPropertyType... selectorProperties) {
        this.setSelector(new ScXPathPage(selectorProperties));
        return (ScXPath) this.selector();
    }
}
