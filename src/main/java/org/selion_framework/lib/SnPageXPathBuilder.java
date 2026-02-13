package org.selion_framework.lib;

public final class SnPageXPathBuilder extends SnSelectorBuilder {
    @Override
    public SnXPath raw(String selectorText) {
        return new SnXPathRaw(selectorText);
    }


    public SnXPath descendant(String tag, SnXpathPropertyType... selectorProperties) {
        this.setSelector(new SnXPathPage(tag, selectorProperties));
        return (SnXPath) this.selector();
    }
}
