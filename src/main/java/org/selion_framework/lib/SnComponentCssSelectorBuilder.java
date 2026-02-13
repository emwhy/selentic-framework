package org.selion_framework.lib;

public final class SnComponentCssSelectorBuilder extends SnSelectorBuilder {
    @Override
    public SnCssSelector raw(String selectorText) {
        return new SnCssSelectorRaw(selectorText);
    }

    public SnCssSelector descendant(SnCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new SnCssSelectorDescendant(selectorProperties));
        return (SnCssSelector) this.selector();
    }

    public SnCssSelector child(SnCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new SnCssSelectorChild(selectorProperties));
        return (SnCssSelector) this.selector();
    }

    public SnCssSelector sibling(SnCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new SnCssSelectorSibling(selectorProperties));
        return (SnCssSelector) this.selector();
    }

    public SnCssSelector nextSibling(SnCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new SnCssSelectorNextSibling(selectorProperties));
        return (SnCssSelector) this.selector();
    }

    public SnCssSelector page(SnCssSelectorPropertyType... selectorProperties) {
        this.setSelector(new SnCssSelectorPage(selectorProperties));
        return (SnCssSelector) this.selector();
    }
}
