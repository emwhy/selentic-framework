package org.selion_framework.lib;

public final class SnComponentSelectorBuilder extends SnSelectorBuilder {
    public SnXPathSelector descendant(String tag, SnSelectorProperty... selectorProperties) {
        this.setSelector(new SnXPathSelectorDescendant(tag, selectorProperties));
        return this.selector();
    }

    public SnXPathSelector child(String tag, SnSelectorProperty... selectorProperties) {
        this.setSelector(new SnXPathSelectorChild(tag, selectorProperties));
        return this.selector();
    }

    public SnXPathSelector sibling(String tag, SnSelectorProperty... selectorProperties) {
        this.setSelector(new SnXPathSelectorSibling(tag, selectorProperties));
        return this.selector();
    }

    public SnXPathSelector precedingSibling(String tag, SnSelectorProperty... selectorProperties) {
        this.setSelector(new SnXPathSelectorPrecedingSibling(tag, selectorProperties));
        return this.selector();
    }

    public SnXPathSelector page(String tag, SnSelectorProperty... selectorProperties) {
        this.setSelector(new SnXPathSelectorPage(tag, selectorProperties));
        return this.selector();
    }

    public SnXPathSelector parent() {
        this.setSelector(new SnXPathSelectorParent());
        return this.selector();
    }
}
