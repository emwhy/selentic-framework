package org.selion_framework.lib;

public final class SnXPathSelectorPrecedingSibling extends SnXPathSelector {
    SnXPathSelectorPrecedingSibling(String tag, SnSelectorProperty... selectorProperties) {
        super(tag, selectorProperties);
    }

    SnXPathSelectorPrecedingSibling(SnXPathSelector priorSelectorNode, String tag, SnSelectorProperty... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding-sibling::";
    }
}
