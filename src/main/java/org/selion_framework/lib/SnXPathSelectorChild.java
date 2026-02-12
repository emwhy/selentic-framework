package org.selion_framework.lib;

public final class SnXPathSelectorChild extends SnXPathSelector {
    SnXPathSelectorChild(String tag, SnLocatorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    SnXPathSelectorChild(SnXPathSelector priorLocatorNode, String tag, SnLocatorProperty... locatorProperties) {
        super(priorLocatorNode, tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/child::";
    }
}
