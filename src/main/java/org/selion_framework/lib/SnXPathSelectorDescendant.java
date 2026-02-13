package org.selion_framework.lib;

public final class SnXPathSelectorDescendant extends SnXPathSelector {
    SnXPathSelectorDescendant(String tag, SnSelectorProperty... selectorProperties) {
        super(tag, selectorProperties);
    }

    SnXPathSelectorDescendant(SnXPathSelector priorSelectorNode, String tag, SnSelectorProperty... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
