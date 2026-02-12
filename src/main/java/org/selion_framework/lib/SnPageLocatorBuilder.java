package org.selion_framework.lib;

public final class SnPageLocatorBuilder extends SnLocatorBuilder {
    public SnXPathSelector descendant(String tag, SnLocatorProperty... locatorProperties) {
        this.setLocator(new SnXPathSelectorPage(tag, locatorProperties));
        return this.locator();
    }
}
