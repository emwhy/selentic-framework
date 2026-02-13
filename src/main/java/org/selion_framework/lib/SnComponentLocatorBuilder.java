package org.selion_framework.lib;

public final class SnComponentLocatorBuilder extends SnSelectorBuilder {
    public SnXPathSelector descendant(String tag, SnSelectorProperty... locatorProperties) {
        this.setLocator(new SnXPathSelectorDescendant(tag, locatorProperties));
        return this.selector();
    }

    public SnXPathSelector child(String tag, SnSelectorProperty... locatorProperties) {
        this.setLocator(new SnXPathSelectorChild(tag, locatorProperties));
        return this.selector();
    }

    public SnXPathSelector sibling(String tag, SnSelectorProperty... locatorProperties) {
        this.setLocator(new SnXPathSelectorSibling(tag, locatorProperties));
        return this.selector();
    }

    public SnXPathSelector precedingSibling(String tag, SnSelectorProperty... locatorProperties) {
        this.setLocator(new SnXPathSelectorPrecedingSibling(tag, locatorProperties));
        return this.selector();
    }

    public SnXPathSelector page(String tag, SnSelectorProperty... locatorProperties) {
        this.setLocator(new SnXPathSelectorPage(tag, locatorProperties));
        return this.selector();
    }

    public SnXPathSelector parent() {
        this.setLocator(new SnXPathSelectorParent());
        return this.selector();
    }
}
