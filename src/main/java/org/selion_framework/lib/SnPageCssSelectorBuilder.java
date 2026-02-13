package org.selion_framework.lib;

public final class SnPageCssSelectorBuilder extends SnSelectorBuilder {
    public SnCssSelector descendant(SnCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new SnCssSelectorDescendant(selectorProperties));
        return (SnCssSelector) this.selector();
    }
}
