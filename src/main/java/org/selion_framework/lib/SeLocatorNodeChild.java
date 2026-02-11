package org.selion_framework.lib;

public final class SeLocatorNodeChild extends SeLocatorNode {
    SeLocatorNodeChild(String tag, SeLocatorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    SeLocatorNodeChild(SeLocatorNode priorLocatorNode, String tag, SeLocatorProperty... locatorProperties) {
        super(priorLocatorNode, tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/child::";
    }
}
