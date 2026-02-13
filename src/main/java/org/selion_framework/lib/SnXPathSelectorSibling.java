package org.selion_framework.lib;

public final class SnXPathSelectorSibling extends SnXPathSelector {
    SnXPathSelectorSibling(String tag, SnSelectorProperty... selectorProperties) {
        super(tag, selectorProperties);
    }

    SnXPathSelectorSibling(SnXPathSelector priorSelectorNode, String tag, SnSelectorProperty... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/following-sibling::";
    }
}
