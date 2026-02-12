package org.selion_framework.lib;

public final class SnXPathSelectorPrecedingSibling extends SnXPathSelector {
    SnXPathSelectorPrecedingSibling(String tag, SnLocatorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    SnXPathSelectorPrecedingSibling(SnXPathSelector priorLocatorNode, String tag, SnLocatorProperty... locatorProperties) {
        super(priorLocatorNode, tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding-sibling::";
    }
}
