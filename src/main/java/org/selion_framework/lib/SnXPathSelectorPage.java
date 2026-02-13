package org.selion_framework.lib;

public final class SnXPathSelectorPage extends SnXPathSelector {
    SnXPathSelectorPage(String tag, SnSelectorProperty... selectorProperties) {
        super(tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
