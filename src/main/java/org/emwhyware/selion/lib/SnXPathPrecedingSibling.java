package org.emwhyware.selion.lib;

public final class SnXPathPrecedingSibling extends SnXPath {
    SnXPathPrecedingSibling(String tag, SnXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    SnXPathPrecedingSibling(SnXPath priorSelectorNode, String tag, SnXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding-sibling::";
    }
}
