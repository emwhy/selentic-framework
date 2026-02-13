package org.selion_framework.lib;

public final class SnXPathChild extends SnXPath {
    SnXPathChild(String tag, SnXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    SnXPathChild(SnXPath priorSelectorNode, String tag, SnXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/child::";
    }
}
