package org.emwhyware.selion.lib;

public final class SnXPathFollowing extends SnXPath {
    SnXPathFollowing(String tag, SnXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    SnXPathFollowing(SnXPath priorSelectorNode, String tag, SnXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/following::";
    }
}
