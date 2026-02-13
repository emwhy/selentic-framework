package org.selion_framework.lib;

public final class SnXPathSelectorDescendant extends SnXPathSelector {
    SnXPathSelectorDescendant(String tag, SnSelectorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    SnXPathSelectorDescendant(SnXPathSelector priorLocatorNode, String tag, SnSelectorProperty... locatorProperties) {
        super(priorLocatorNode, tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
