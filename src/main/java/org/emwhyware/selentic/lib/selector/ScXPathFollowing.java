package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScXPathFollowing extends ScXPath {
    ScXPathFollowing(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathFollowing(@NonNull ScXPath priorSelectorNode, @NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    ScXPathFollowing(@NonNull ScXpathPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    ScXPathFollowing(@NonNull ScXPath priorSelectorNode, @NonNull ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/following::";
    }

    /**
     * This method defines a boundary component to a list of components.
     *
     * <p>The method generates XPath, <b>[following:: ...]</b>. The XPath naming is not very clear what it does,
     * so the method implementation makes it more legible and clear.
     * <p>
     * The parameter {@link ScSelector} must be of the page level. It would fail if a relative path is passed.
     *
     *
     * @param xpath {@link ScXPath} class that contains XPath to the lower boundary of the list.
     * @return a new {@code ScXPath} object representing the preceding selector
     * @see ScXPathBoundary
     * @see ScXPath
     */
    public ScXPathBoundary boundary(@NonNull ScXPath xpath) {
        return new ScXPathBoundary(this, xpath);
    }

}
