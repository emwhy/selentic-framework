package org.selion_framework.lib;

public final class SnXPathSelectorSibling extends SnXPathSelector {
    SnXPathSelectorSibling(String tag, SnLocatorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    SnXPathSelectorSibling(SnXPathSelector priorLocatorNode, String tag, SnLocatorProperty... locatorProperties) {
        super(priorLocatorNode, tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/following-sibling::";
    }
}
