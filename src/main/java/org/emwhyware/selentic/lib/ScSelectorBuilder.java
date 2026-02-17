package org.emwhyware.selentic.lib;

public abstract sealed class ScSelectorBuilder permits ScComponentXPathBuilder, ScPageXPathBuilder, ScComponentCssSelectorBuilder, ScPageCssSelectorBuilder {
    private ScSelector selector;

    protected ScSelector selector() {
        return this.selector;
    }

    public abstract ScSelector raw(String selectorText);

    protected void setSelector(ScSelector selector) {
        this.selector = selector;
    }
}
