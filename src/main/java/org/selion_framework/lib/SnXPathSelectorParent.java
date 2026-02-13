package org.selion_framework.lib;

public final class SnXPathSelectorParent extends SnXPathSelector {
    SnXPathSelectorParent() {
        super("*");
    }

    SnXPathSelectorParent(SnXPathSelector priorSelectorNode) {
        super(priorSelectorNode, "*");
    }

    @Override
    protected String nodeText() {
        return "/parent::";
    }
}
