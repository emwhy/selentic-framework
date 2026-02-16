package org.emwhyware.selion.lib;

public final class SnXPathSibling extends SnXPath {
    SnXPathSibling(String tag, SnXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    SnXPathSibling(SnXPath priorSelectorNode, String tag, SnXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/following-sibling::";
    }
}
