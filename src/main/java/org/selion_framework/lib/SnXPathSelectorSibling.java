package org.selion_framework.lib;

public final class SnXPathSelectorSibling extends SnXPathSelector {
    SnXPathSelectorSibling(String tag, SnSelectorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    SnXPathSelectorSibling(SnXPathSelector priorLocatorNode, String tag, SnSelectorProperty... locatorProperties) {
        super(priorLocatorNode, tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/following-sibling::";
    }
}
