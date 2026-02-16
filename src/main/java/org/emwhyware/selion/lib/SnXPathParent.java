package org.emwhyware.selion.lib;

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
