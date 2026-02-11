package org.selion_framework.lib;

public final class SeLocatorNodePrecedingSibling extends SeLocatorNode {
    SeLocatorNodePrecedingSibling(String tag, SeLocatorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    SeLocatorNodePrecedingSibling(SeLocatorNode priorLocatorNode, String tag, SeLocatorProperty... locatorProperties) {
        super(priorLocatorNode, tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding-sibling::";
    }
}
