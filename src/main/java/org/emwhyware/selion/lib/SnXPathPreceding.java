package org.emwhyware.selion.lib;

public final class SnXPathPreceding extends SnXPath {
    SnXPathPreceding(String tag, SnXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    SnXPathPreceding(SnXPath priorSelectorNode, String tag, SnXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding::";
    }
}
