package org.selion_framework.lib;

public final class SnXPathSelectorParent extends SnXPathSelector {
    SnXPathSelectorParent() {
        super("*");
    }

    SnXPathSelectorParent(SnXPathSelector priorLocatorNode) {
        super(priorLocatorNode, "*");
    }

    @Override
    protected String nodeText() {
        return "/parent::";
    }
}
