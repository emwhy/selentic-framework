package org.selion_framework.lib;

public final class SnXPathParent extends SnXPath {
    SnXPathParent() {
        super("*");
    }

    SnXPathParent(SnXPath priorSelectorNode) {
        super(priorSelectorNode, "*");
    }

    @Override
    protected String nodeText() {
        return "/parent::";
    }
}
