package org.emwhyware.selentic.lib;

public final class ScXPathFollowing extends ScXPath {
    ScXPathFollowing(String tag, ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathFollowing(ScXPath priorSelectorNode, String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/following::";
    }
}
