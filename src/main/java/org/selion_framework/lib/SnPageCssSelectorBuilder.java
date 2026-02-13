package org.selion_framework.lib;

public final class SnPageCssSelectorBuilder extends SnSelectorBuilder {
    @Override
    public SnCssSelector raw(String selectorText) {
        return new SnCssSelectorRaw(selectorText);
    }

    public SnCssSelector descendant(SnCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new SnCssSelectorDescendant(selectorProperties));
        return (SnCssSelector) this.selector();
    }
}
