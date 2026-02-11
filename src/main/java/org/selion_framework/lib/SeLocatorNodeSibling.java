package org.selion_framework.lib;

public final class SeLocatorNodeSibling extends SeLocatorNode {
    SeLocatorNodeSibling(String tag, SeLocatorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    SeLocatorNodeSibling(SeLocatorNode priorLocatorNode, String tag, SeLocatorProperty... locatorProperties) {
        super(priorLocatorNode, tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/following-sibling::";
    }
}
