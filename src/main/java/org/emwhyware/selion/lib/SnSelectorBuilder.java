package org.emwhyware.selion.lib;

public abstract sealed class SnSelectorBuilder permits SnComponentXPathBuilder, SnPageXPathBuilder, SnComponentCssSelectorBuilder, SnPageCssSelectorBuilder {
    private SnSelector selector;

    protected SnSelector selector() {
        return this.selector;
    }

    public abstract SnSelector raw(String selectorText);

    protected void setSelector(SnSelector selector) {
        this.selector = selector;
    }
}
