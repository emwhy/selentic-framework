package org.selion_framework.lib;

public final class SnPageLocatorBuilder extends SnSelectorBuilder {
    public SnXPathSelector descendant(String tag, SnSelectorProperty... locatorProperties) {
        this.setLocator(new SnXPathSelectorPage(tag, locatorProperties));
        return this.selector();
    }
}
