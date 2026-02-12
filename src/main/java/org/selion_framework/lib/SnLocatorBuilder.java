package org.selion_framework.lib;

public sealed class SnLocatorBuilder permits SnComponentLocatorBuilder, SnPageLocatorBuilder, SnComponentCssSelectorBuilder {
    private SnXPathSelector locatorNode;

    protected SnXPathSelector locator() {
        return this.locatorNode;
    }

    public SnXPathSelector raw(String selectorText) {
        return null;
    }

    protected void setLocator(SnXPathSelector locatorNode) {
        this.locatorNode = locatorNode;
    }
}
