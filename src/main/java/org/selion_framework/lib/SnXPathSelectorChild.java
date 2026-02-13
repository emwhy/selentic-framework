package org.selion_framework.lib;

public final class SnXPathSelectorChild extends SnXPathSelector {
    SnXPathSelectorChild(String tag, SnSelectorProperty... selectorProperties) {
        super(tag, selectorProperties);
    }

    SnXPathSelectorChild(SnXPathSelector priorSelectorNode, String tag, SnSelectorProperty... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/child::";
    }
}
