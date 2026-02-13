package org.selion_framework.lib;

public sealed class SnSelectorBuilder permits SnComponentLocatorBuilder, SnPageLocatorBuilder, SnComponentCssSelectorBuilder {
    private SnXPathSelector selector;

    protected SnXPathSelector selector() {
        return this.selector;
    }

    public SnXPathSelector raw(String selectorText) {
        return null;
    }

    protected void setLocator(SnXPathSelector selector) {
        this.selector = selector;
    }
}
