package org.emwhyware.selentic.lib;

public final class ScXPathFollowing extends ScXPath {
    ScXPathFollowing(String tag, ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathFollowing(ScXPath priorSelectorNode, String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    ScXPathFollowing(ScXpathPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    ScXPathFollowing(ScXPath priorSelectorNode, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/following::";
    }
}
