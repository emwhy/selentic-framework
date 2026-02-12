package org.selion_framework.lib;

public final class SnComponentLocatorBuilder extends SnLocatorBuilder {
    public SnXPathSelector descendant(String tag, SnLocatorProperty... locatorProperties) {
        this.setLocator(new SnXPathSelectorDescendant(tag, locatorProperties));
        return this.locator();
    }

    public SnXPathSelector child(String tag, SnLocatorProperty... locatorProperties) {
        this.setLocator(new SnXPathSelectorChild(tag, locatorProperties));
        return this.locator();
    }

    public SnXPathSelector sibling(String tag, SnLocatorProperty... locatorProperties) {
        this.setLocator(new SnXPathSelectorSibling(tag, locatorProperties));
        return this.locator();
    }

    public SnXPathSelector precedingSibling(String tag, SnLocatorProperty... locatorProperties) {
        this.setLocator(new SnXPathSelectorPrecedingSibling(tag, locatorProperties));
        return this.locator();
    }

    public SnXPathSelector page(String tag, SnLocatorProperty... locatorProperties) {
        this.setLocator(new SnXPathSelectorPage(tag, locatorProperties));
        return this.locator();
    }

    public SnXPathSelector parent() {
        this.setLocator(new SnXPathSelectorParent());
        return this.locator();
    }
}
