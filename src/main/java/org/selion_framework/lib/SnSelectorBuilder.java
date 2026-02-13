package org.selion_framework.lib;

public sealed class SnSelectorBuilder permits SnComponentXPathBuilder, SnPageXPathBuilder, SnComponentCssSelectorBuilder, SnPageCssSelectorBuilder {
    private SnSelector selector;

    protected SnSelector selector() {
        return this.selector;
    }

    public SnSelector raw(String selectorText) {
        return null;
    }

    protected void setSelector(SnSelector selector) {
        this.selector = selector;
    }
}
