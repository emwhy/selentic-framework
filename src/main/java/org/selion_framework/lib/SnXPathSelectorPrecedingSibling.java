package org.selion_framework.lib;

public final class SnXPathSelectorPrecedingSibling extends SnXPathSelector {
    SnXPathSelectorPrecedingSibling(String tag, SnSelectorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    SnXPathSelectorPrecedingSibling(SnXPathSelector priorLocatorNode, String tag, SnSelectorProperty... locatorProperties) {
        super(priorLocatorNode, tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding-sibling::";
    }
}
