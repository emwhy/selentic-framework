package org.selion_framework.lib;

public final class SnComponentXPathBuilder extends SnSelectorBuilder {
    public SnXPath descendant(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathDescendant(tag, selectorProperties));
        return (SnXPath) this.selector();
    }

    public SnXPath child(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathChild(tag, selectorProperties));
        return (SnXPath) this.selector();
    }

    public SnXPath sibling(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathSibling(tag, selectorProperties));
        return (SnXPath) this.selector();
    }

    public SnXPath precedingSibling(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathPrecedingSibling(tag, selectorProperties));
        return (SnXPath) this.selector();
    }

    public SnXPath page(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathPage(tag, selectorProperties));
        return (SnXPath) this.selector();
    }

    public SnXPath parent() {
        this.setSelector(new SnXPathParent());
        return (SnXPath) this.selector();
    }
}
