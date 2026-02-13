package org.selion_framework.lib;

public final class SnPageSelectorBuilder extends SnSelectorBuilder {
    public SnXPathSelector descendant(String tag, SnSelectorProperty... selectorProperties) {
        this.setSelector(new SnXPathSelectorPage(tag, selectorProperties));
        return this.selector();
    }
}
