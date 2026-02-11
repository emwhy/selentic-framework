package org.selion_framework.lib;

public final class SeLocatorNodePage extends SeLocatorNode {
    SeLocatorNodePage(String tag, SeLocatorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
