package org.selion_framework.lib;

public final class SnXPathSelectorDescendant extends SnXPathSelector {
    SnXPathSelectorDescendant(String tag, SnLocatorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    SnXPathSelectorDescendant(SnXPathSelector priorLocatorNode, String tag, SnLocatorProperty... locatorProperties) {
        super(priorLocatorNode, tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
