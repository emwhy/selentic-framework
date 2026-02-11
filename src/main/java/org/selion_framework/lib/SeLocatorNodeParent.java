package org.selion_framework.lib;

public final class SeLocatorNodeParent extends SeLocatorNode {
    SeLocatorNodeParent() {
        super("*");
    }

    SeLocatorNodeParent(SeLocatorNode priorLocatorNode) {
        super(priorLocatorNode, "*");
    }

    @Override
    protected String nodeText() {
        return "/parent::";
    }
}
