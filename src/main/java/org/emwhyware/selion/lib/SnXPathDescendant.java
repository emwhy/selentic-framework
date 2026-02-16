package org.emwhyware.selion.lib;

public final class SnXPathDescendant extends SnXPath {
    SnXPathDescendant(String tag, SnXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    SnXPathDescendant(SnXPath priorSelectorNode, String tag, SnXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
