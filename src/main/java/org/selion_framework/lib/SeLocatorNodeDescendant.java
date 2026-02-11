package org.selion_framework.lib;

public final class SeLocatorNodeDescendant extends SeLocatorNode {
    SeLocatorNodeDescendant(String tag, SeLocatorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    SeLocatorNodeDescendant(SeLocatorNode priorLocatorNode, String tag, SeLocatorProperty... locatorProperties) {
        super(priorLocatorNode, tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
