package org.emwhyware.selentic.lib;

public final class ScXPathParent extends ScXPath {
    ScXPathParent() {
        super("*");
    }

    ScXPathParent(ScXPath priorSelectorNode) {
        super(priorSelectorNode, "*");
    }

    @Override
    protected String nodeText() {
        return "/parent::";
    }
}
