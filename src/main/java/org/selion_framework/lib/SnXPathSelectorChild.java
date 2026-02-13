package org.selion_framework.lib;

public final class SnXPathSelectorChild extends SnXPathSelector {
    SnXPathSelectorChild(String tag, SnSelectorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    SnXPathSelectorChild(SnXPathSelector priorLocatorNode, String tag, SnSelectorProperty... locatorProperties) {
        super(priorLocatorNode, tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/child::";
    }
}
