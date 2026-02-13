package org.selion_framework.lib;

public final class SnPageXPathBuilder extends SnSelectorBuilder {
    public SnXPath descendant(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathPage(tag, selectorProperties));
        return (SnXPath) this.selector();
    }
}
